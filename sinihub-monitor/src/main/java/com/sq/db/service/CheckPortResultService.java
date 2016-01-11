package com.sq.db.service;

import com.sq.db.domain.CheckItem;
import com.sq.db.domain.CheckPort;
import com.sq.db.domain.Constant;
import com.sq.db.domain.EmailStatus;
import com.sq.db.repository.CheckPortRepository;
import com.sq.db.repository.EmailStatusRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ywj on 2016/1/8.
 * 检查各端口网络是否连通过程类
 */
@Service("checkPortResultService")
public class CheckPortResultService extends BaseService<CheckPort,Long>{

    private static final Logger log = LoggerFactory.getLogger(CheckPortResultService.class);

    private final static String CONFIG_FILE_NAME = "/conf/Ip.properties";

    @BaseComponent
    @Autowired
    private CheckPortRepository checkPortRepository;

    @Autowired
    private PortResultService portResultService;

    @Autowired
    private EmailStatusRepository emailStatusRepository;

    @Autowired
    private EmailStatusService emailStatusService;

    //记录每次监测的结果，正常则为true反之为false
    private boolean isConnect;
    //统计出现问题的次数
    private static Map<Long,Integer> countConMap = new HashMap<Long,Integer>();

    /**
     * 获取属性文件
     * @return
     */
    public static Properties getProperty(){
        Properties prop = new Properties();
        try {
            //加载属性文件获取资源
            prop.load(CheckNetService.class.getResourceAsStream(CONFIG_FILE_NAME));
        } catch (IOException e) {
            log.error(Constant.LOAD_EMAIL_PROP_FAIL);
        }
        return prop;
    }
    //类加载的时候调用
    static {
        getProperty();
    }
    /**
     * 传入数据检查网络
     * @param checkPort
     */
    public void checkPortResult(CheckPort checkPort){
        Properties props = getProperty();
        try {
            //获取本地LocalHost
            InetAddress localInetAddr = InetAddress.getByName(props.getProperty("localAddress"));
            log.error("this is local IP address");
            log.error(checkPort.getProjectPoint().getHospId());
            //获取远程的Address
            InetAddress remoteInetAddr = InetAddress.getByName(checkPort.getProjectPoint().getHospId().toString());
            //判断网络是否连通得到结果
            isConnect = isReachable(localInetAddr,remoteInetAddr,checkPort.getPort(), Constant.TIME_OUT);
            //对监测结果判断进行操作
            if(isConnect == true){
                log.error(checkPort.getProjectPoint().getHospId()+":"+checkPort.getPort()+"is true");
                //查出对应数据库中邮箱的开关状态
                List<EmailStatus> emailStatusList = emailStatusRepository.findAll();
                for(EmailStatus e:emailStatusList) {
                    if (checkPort.getId().intValue() == e.getCheckItemId().intValue()) {
                        emailStatusService.delete(e.getId());
                        e.setCheckItemId(checkPort.getId());
                        //可以发送邮箱
                        e.setStatus(Constant.EMAIL_STATUS_ONE);
                        emailStatusRepository.save(e);
                    }
                }
            }else{
                Integer countCon = countConMap.get(checkPort.getId());
                if(countCon == null){
                    countCon = Constant.SECOND_TIME_OZEO;
                }
                countCon = (countCon+1);
                log.error(checkPort.getId().toString()+Constant.FAIL_TIME+countCon.toString());
                //产生错误则将结果保存到数据库中
                portResultService.receiveDataAndSave(checkPort,countCon);
                //超过10次则清除
                if(countCon >= Constant.SECOND_TIME_TEN){
                    countCon = Constant.SECOND_TIME_OZEO;
                }
                //将记录次数放入缓存
                countConMap.put(checkPort.getId(), countCon);
            }
        } catch (UnknownHostException e) {
            log.error(Constant.GET_LOCALHOSP_FAIL);
        }
    }

    /**
     * 判断是否能和项目点网络连通工具类
     * @param localInetAddr
     * @param remoteInetAddr
     * @param port
     * @param timeout
     * @return
     */
    static boolean isReachable(InetAddress localInetAddr, InetAddress remoteInetAddr, int port, int timeout) {
        //标记位记录结果
        boolean isReachable = false;
        Socket socket = null;
        try {
            socket = new Socket();
            //使用Socket来连接服务器时最简单的方式就是直接使用IP和端口，
            SocketAddress localSocketAddr = new InetSocketAddress(localInetAddr, Constant.DE_PORT);
            //bind关联地址和端口
            socket.bind(localSocketAddr);
			/*
			 * SocketAddress只是个抽象类，它除了有一个默认的构造方法外，
			 * 其它的方法都是abstract的，因此，我们必须使用SocketAddress的
			 * 子类来建立SocketAddress对象，也就是唯一的子类InetSocketAddress
			 */
            InetSocketAddress endpointSocketAddr = new InetSocketAddress(remoteInetAddr, port);
            //创建与指定外部端口的连接
            socket.connect(endpointSocketAddr, timeout);
            isReachable = true;
        } catch (IOException e) {
            log.error(remoteInetAddr.getHostAddress()+"端口："+port+Constant.FAIL_CONNECTION);
        } finally {
            if (socket != null) {
                try {
                    //关闭socket
                    socket.close();
                } catch (IOException e) {
                    log.error(Constant.CLOSE_SOCKET_EXCEPTION);
                }
            }
        }
        return isReachable;
    }
}

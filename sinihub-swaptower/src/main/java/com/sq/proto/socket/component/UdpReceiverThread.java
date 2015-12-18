package com.sq.proto.socket.component;

import com.sq.proto.common.component.RtDataCache;
import com.sq.proto.common.domain.OriginalData;
import com.sq.proto.common.repository.OriginalDataRepository;
import com.sq.proto.socket.domain.UdpProtocal;
import com.sq.util.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * UDP数据接收线程
 * User: shuiqing
 * Date: 15/12/18
 * Time: 上午11:20
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class UdpReceiverThread extends Thread{

    private static Logger log = LoggerFactory.getLogger(UdpReceiverThread.class);

    private static final int TIMEOUT = 0;  //设置接收数据的超时时间

    private static final int DATA_LENGTH = 1024*1000;

    /**
     * 由于Thread非spring启动时实例化，而是根据具体的逻辑动态实例化，所以需要通过此方式从spring的context中获取相应的bean.
     */
    private OriginalDataRepository originalDataRepository = SpringUtils.getBean(OriginalDataRepository.class);

    private RtDataCache rtDataCache = SpringUtils.getBean(RtDataCache.class);

    private UdpProtocal udpProtocal;

    boolean connFlag = true;     //是否接收到数据的标志位

    public UdpProtocal getUdpProtocal() {
        return udpProtocal;
    }

    public void setUdpProtocal(UdpProtocal udpProtocal) {
        this.udpProtocal = udpProtocal;
    }

    @Override
    public void run() {
        byte[] buf = new byte[DATA_LENGTH];
        try {
            //客户端在端口监听接收到的数据
            DatagramSocket ds = new DatagramSocket(Integer.parseInt(udpProtocal.getPort()));
            InetAddress loc = InetAddress.getLocalHost();

            //定义用来接收数据的DatagramPacket实例
            DatagramPacket dp_receive = new DatagramPacket(buf, DATA_LENGTH);
            //数据发向本地端口
            ds.setSoTimeout(TIMEOUT);     //设置接收数据时阻塞的最长时间

            int i = 1;
            while(connFlag){

                Thread.sleep(udpProtocal.getUdpUpdateRate()*1000l);  //udp通信频率

                //接收从服务端发送回来的数据
                ds.receive(dp_receive);

                log.debug("dp_receive:" + new String(dp_receive.getData(), 0, dp_receive.getLength()));
                updateOrignalDataCache(new String(dp_receive.getData(), 0, dp_receive.getLength()));   //更新实时数据的缓存

                if (i % udpProtocal.getSyncRate() == 0) {
                    saveReceiveUdpData(new String(dp_receive.getData(), 0, dp_receive.getLength()));
                }
                i++;

                //如果收到数据，则打印出来
                String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +
                        " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
                log.debug(str_receive);
                //由于dp_receive在接收了数据之后，其内部消息长度值会变为实际接收的消息的字节数，
                //所以这里要将dp_receive的内部消息长度重新置为1024
                dp_receive.setLength(DATA_LENGTH);
            }
            ds.close();
        } catch (UnknownHostException e) {
            log.error("IP地址错误.", e);
        }catch(InterruptedIOException e){
            log.error("获取监听端口的数据失败.",e);
        } catch (IOException e) {
            log.error("数据通讯异常.", e);
        } catch (InterruptedException e) {
            log.error("UDP接收数据线程中断.", e);
        }
    }

    /**
     * 更新实时的测点数据
     * @param receiveUdpData  接收到得实时测点数据字符串
     */
    public void updateOrignalDataCache(String receiveUdpData) {
        String[] pointEntityArray = receiveUdpData.split(";");
        for (String pointStr:pointEntityArray) {
            String[] paramArray = pointStr.split("%%");
            rtDataCache.originalItemMap.put(paramArray[0], paramArray[1]);
            log.debug("receive data-- itemCode:" + paramArray[0] + ",itemValue:" + paramArray[1]);
        }
    }

    /**
     * 接收udp传来的数据并保存至数据库
     * @param receiveMsg  接收到得实时数据
     */
    public void saveReceiveUdpData (String receiveMsg) {
        String[] pointEntityArray = receiveMsg.split(";");
        List<OriginalData> originalDataList = new LinkedList<OriginalData>();
        for (String pointStr:pointEntityArray) {
            String[] paramArray = pointStr.split("%%");
            OriginalData originalData = new OriginalData();
            originalData.setInstanceTime(Calendar.getInstance());
            originalData.setItemCode(paramArray[0]);
            originalData.setItemValue(paramArray[1]);
            originalDataList.add(originalData);
        }
        originalDataRepository.save(originalDataList);
    }
}

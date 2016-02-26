package com.sq.db.service;

import com.sq.db.component.DBUtil;
import com.sq.db.domain.CheckItem;
import com.sq.db.domain.Constant;
import com.sq.db.domain.EmailStatus;
import com.sq.db.domain.ProjectPoint;
import com.sq.db.repository.EmailStatusRepository;
import com.sq.db.repository.ProjectPointRepository;
import com.sq.entity.search.MatchType;
import com.sq.entity.search.Searchable;
import com.sq.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ywj on 2015/12/21.
 * 检测测点时间是否正常
 */
@Service("originalDateService")
public class OriginalDateService {

    private static final Logger log = LoggerFactory.getLogger(OriginalDateService.class);

    //接收查询的数据持有的时间
    private String time = null;

    private ResultSet rs = null;

    private CheckItem ch;

    //构造dbConnectionMapmap用于缓存链接
    public static Map<Long,Connection> dbConnectionMapmap = new HashMap<Long,Connection>();

    // 记录连接失败的次数
    private static Map<Long,Integer> failMap = new HashMap<Long,Integer>();

    @Autowired
    private CheckResultService checkResultService;

    @Autowired
    private ProjectPointRepository projectPointRepository;

    @Autowired
    private ProjectPointService projectPointService;

    @Autowired
    private CheckItemService checkItemService;

    @Autowired
    private EmailStatusRepository emailStatusRepository;

    @Autowired
    private EmailStatusService emailStatusService;

    // 用于阻隔连续发送链接超时的邮件
    private static Map<Long,Integer> exceptionFlagMap = new HashMap<Long,Integer>();

    /***
     * 核查测点是否异常
     * @param  checkItem
     * @throws Exception
     */
    public void checkOriginal(CheckItem checkItem)  {
        Integer exceptionFlag = exceptionFlagMap.get(checkItem.getId());
        try {
            if(exceptionFlag == null){
                exceptionFlagMap.put(checkItem.getId(),Constant.SECOND_TIME_OZEO);
                exceptionFlag = exceptionFlagMap.get(checkItem.getId());
            }
        } catch (Exception e) {
            log.error("第一次运行初始值为零");
            log.error(exceptionFlag.toString());
        }
        Map<Long,Boolean> map = CheckNetService.resultMap;
        Boolean flag = map.get(checkItem.getProjectPoint().getId());
        log.error(checkItem.getProjectPoint().getId().toString()+"net is"+flag);
        if(flag !=null) {
            if (flag == true) {
                try {
                    //先去map缓存中查找是否存在此链接
                    Connection conn = dbConnectionMapmap.get(checkItem.getProjectPoint().getId());
                    //判断缓存中是否存在链接资源
                    if (conn == null) {
                        conn = projectPointService.instanceConnection(checkItem.getProjectPoint().getId());
                        dbConnectionMapmap.put(checkItem.getId(), conn);
                    }
                    Statement stmt = conn.createStatement();
                    //查询该表中最新一条记录
                    String sql = "select * from t_originaldata t ORDER BY t.instanceTime desc limit 1";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        //获取数据库最近数据的时间
                        time = rs.getDate("instanceTime") + " " + rs.getTime("instanceTime").toString();
                    }
                    //计算时间差值
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d1 = df.parse(df.format(new Date()));
                    Date d2 = df.parse(time);
                    long diff = d1.getTime() - d2.getTime();
                    long days = diff / (1000);
                    log.error("-----时间差为-----");
                    log.error(String.valueOf(days));
                    //查出对应数据库中邮箱的开关状态
                    List<EmailStatus> emailStatusList = emailStatusRepository.findAll();
                    for(EmailStatus e:emailStatusList){
                        if(checkItem.getId().intValue() == e.getCheckItemId().intValue()){
                            System.out.println(e.getStatus());
                            //emailStatusService.delete(e.getId());
                            //e.setCheckItemId(checkItem.getId());
                            //判断时间是否在正常范围内
                            if (days > Constant.CHECK_TIME) {
                                //出现异常传入有异常的数据生成记录
                                checkResultService.receiveDataAndSave(checkItem, time);
                                //发送完邮件，将对应的邮箱状态改为1代表不接受邮件，防止重复发送邮件
                                e.setStatus(Constant.EMAIL_STATUS_TWO);
                                emailStatusRepository.save(e);
                            } else {
                                //传入正常的数据记录结果
                                checkResultService.receiveDataAndSaveWellData(checkItem);
                                //再次查询数据正常设置邮箱的状态为0 代表可以接收邮件
                                e.setStatus(Constant.EMAIL_STATUS_ONE);
                                emailStatusRepository.save(e);
                            }
                        }
                    }
                    exceptionFlagMap.put(checkItem.getId(),Constant.SECOND_TIME_OZEO);
                } catch (Exception e) {
                    Integer failTime = failMap.get(checkItem.getProjectPoint().getId());
                    //不存在代表是第一次出现异常，给予初始值0
                    if (failTime == null) {
                        failTime = Constant.SECOND_TIME_OZEO;
                    }
                    //每次出现连接异常将次数递增一个
                    failTime = (failTime + Constant.SYNCS_TATUS01);
                    failMap.put(checkItem.getProjectPoint().getId(), failTime);
                    if (failTime >= Constant.SECOND_TIME_FIVE) {
                        //checkResultService.sendAndSaveEmail("本地连接失败！", "非项目点的因素！",exceptionFlag);
                        //改变其他异常的邮箱状态1，防止重复发送
                        exceptionFlagMap.put(checkItem.getId(),Constant.SYNCS_TATUS01);
                        //超过5次则清除重新计数
                        failTime = Constant.SECOND_TIME_OZEO;
                        failMap.put(checkItem.getProjectPoint().getId(), failTime);
                    } else {
                        checkOriginal(checkItem);
                    }
                    log.error(Constant.CHECK_COMM_EXCEPTION);
                }
            }
        }
    }
}

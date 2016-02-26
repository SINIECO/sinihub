package com.sq.db.service;

import com.sq.db.domain.CheckItem;
import com.sq.db.domain.Constant;
import com.sq.db.domain.ProjectPoint;
import com.sq.db.repository.CheckItemRepository;
import com.sq.db.repository.ProjectPointRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ywj on 2015/12/29.
 * 监测地磅数据服务类
 */
@Service("checkTradeDataService")
public class CheckTradeDataService {

    private static final Logger log = LoggerFactory.getLogger(CheckTradeDataService.class);

    //接收最新数据的时间
    private String newesTime;

    //设置第一次查询比较的基准时间
    private String firstTime;
    private ResultSet rs = null;

    private CheckItem ch;

    //构造dbConnectionMapmap用于缓存链接
    private static Map<Long, Connection> dbConnectionSecondMapmap = new HashMap<Long, Connection>();

    private static Map<Long, Integer> failMapTimes = new HashMap<Long, Integer>();

    @Autowired
    private CheckResultService checkResultService;

    @Autowired
    private ProjectPointRepository projectPointRepository;

    @Autowired
    private ProjectPointService projectPointService;

    @Autowired
    private CheckTradeItemService checkTradeItemService;

    @Autowired
    private TradeResultService tradeResultService;

    // 用于阻隔连续发送链接超时的邮件
    private static Map<Long,Integer> exceptionFlagMap = new HashMap<Long,Integer>();


    /***
     * 核查测点是否异常
     * @param projectPoint
     */
    public void checkTrade(ProjectPoint projectPoint) {
        Map<Long, Boolean> map = CheckNetService.resultMap;
        Boolean flag = map.get(projectPoint.getId());
        log.error(projectPoint.getId().toString() + "net is" + flag);
        if (flag != null) {
            if (flag == true) {
                try {
                    //先去map缓存中查找是否存在此链接
                    //Connection conn = dbConnectionSecondMapmap.get(projectPoint.getId());
                    //判断缓存中是否存在链接资源
                    //if (conn == null) {
                    Connection conn = projectPointService.instanceConnectionForTrade(projectPoint.getId());
                        //dbConnectionSecondMapmap.put(projectPoint.getId(), conn);
                    //}
                    Integer flatStatus = failMapTimes.get(projectPoint.getId());
                    //第一次赋值为1代表可以发送邮件
                    if(flatStatus == null){
                        failMapTimes.put(projectPoint.getId(),Constant.EMAIL_STATUS_TWO);
                    }
                    Statement stmt = conn.createStatement();
                    String sql = null;
                    if(projectPoint.getId() == 8){
                        sql = "select t.secondWeightTime from t_trade t ORDER BY t.secondWeightTime desc limit 1 ";
                        rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            //获取数据库最新数据的时间
                            String [] s = rs.getString("secondWeightTime").split("\\.");
                            newesTime = s[0];
                        }
                    } else {
                        //查询该表中最新一条记录
                        sql = "select t.seconddatetime from t_trade t ORDER BY t.seconddatetime desc limit 1 ";
                        rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            //获取数据库最新数据的主键
                            newesTime = rs.getDate("seconddatetime") + " " + rs.getTime("seconddatetime").toString();
                        }
                    }
                    log.error(newesTime);
                    //计算时间差值
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d1 = df.parse(df.format(new Date()));
                    Date d2 = df.parse(newesTime);
                    long diff = d1.getTime() - d2.getTime();
                    long days = diff / (1000);
                    log.error("-----时间差为-----");
                    log.error(String.valueOf(days));
                    //超过12小时就出现问题
                    if (days > 64800) {
                            //存储数据到数据库中
                            Integer flatSecond = failMapTimes.get(projectPoint.getId());
                            if(flatSecond == Constant.EMAIL_STATUS_TWO){
                                tradeResultService.receiveDataAndSave(projectPoint,newesTime);
                                failMapTimes.put(projectPoint.getId(),Constant.EMAIL_STATUS_ONE);
                            }
                        } else {
                            failMapTimes.put(projectPoint.getId(),Constant.EMAIL_STATUS_TWO);
                            tradeResultService.receiveDataAndSaveWell(projectPoint);
                            log.error(Constant.GOOD_CHECK_TRADE);
                        }
                    } catch (Exception e) {
                        log.error(Constant.CHECK_TRADE_EXCEPTION ,e);
                    }
            }
        }
    }
}
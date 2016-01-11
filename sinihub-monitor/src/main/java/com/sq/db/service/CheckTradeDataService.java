package com.sq.db.service;

import com.sq.db.domain.CheckItem;
import com.sq.db.domain.Constant;
import com.sq.db.domain.ProjectPoint;
import com.sq.db.repository.CheckItemRepository;
import com.sq.db.repository.ProjectPointRepository;
import com.sq.inject.annotation.BaseComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ywj on 2015/12/29.
 * 监测地磅数据服务类
 */
@Service("checkTradeDataService")
public class CheckTradeDataService {

    private static final Logger log = LoggerFactory.getLogger(CheckTradeDataService.class);

    //接收最新数据的Id
    private Long newestId;

    //设置第一次查询比较的基准Id
    private Long firstId;
    private ResultSet rs = null;

    private CheckItem ch;

    //构造dbConnectionMapmap用于缓存链接
    private static Map<Long, Connection> dbConnectionSecondMapmap = new HashMap<Long, Connection>();

    // 记录连接失败的次数
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

    //用于存储每次的比较Id map存在key区分是哪个项目下的ID
    public static Map<Integer, String> testIdMap = new HashMap<Integer, String>();

    /***
     * 核查测点是否异常
     *
     * @param projectPoint
     * @throws Exception
     */
    public void checkTrade(ProjectPoint projectPoint) {
        Integer otherErrorFlag = exceptionFlagMap.get(projectPoint.getId());
        try {
            if(otherErrorFlag == null){
                exceptionFlagMap.put(projectPoint.getId(),Constant.SECOND_TIME_OZEO);
                otherErrorFlag = exceptionFlagMap.get(projectPoint.getId());
            }
        } catch (Exception e) {
            log.error("第一次运行初始值为零");
            log.error(otherErrorFlag.toString());
        }
        Map<Long, Boolean> map = CheckNetService.resultMap;
        Boolean flag = map.get(projectPoint.getId());
        log.error(projectPoint.getId().toString() + "net is" + flag);
        if (flag != null) {
            if (flag == true) {
                try {
                    //先去map缓存中查找是否存在此链接
                    Connection conn = dbConnectionSecondMapmap.get(projectPoint.getId());
                    //判断缓存中是否存在链接资源
                    if (conn == null) {
                        conn = projectPointService.instanceConnection(projectPoint.getId());
                        dbConnectionSecondMapmap.put(projectPoint.getId(), conn);
                    }
                    Statement stmt = conn.createStatement();
                    //查询该表中最新一条记录
                    String sql = "select t.id from t_trade t ORDER BY t.id desc limit 1";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        //获取数据库最新数据的主键
                        newestId = rs.getLong("id");
                    }
                    String secondId = testIdMap.get(projectPoint.getId().intValue());
                    //查到缓存中的id
                    if (secondId != null) {
                        //相等则产生异常
                        if (Integer.parseInt(secondId) == newestId) {
                            //存储数据到数据库中
                            tradeResultService.receiveDataAndSave(projectPoint);
                        } else {
                            log.error(Constant.GOOD_CHECK_TRADE);
                        }
                        //没有查出缓存中的Id 代表这是第一次运行，使用初始Id
                    }
                    testIdMap.put(projectPoint.getId().intValue(), newestId.toString());
                    exceptionFlagMap.put(projectPoint.getId(),Constant.SECOND_TIME_OZEO);
                } catch (Exception e) {
                    Integer failTime = failMapTimes.get(projectPoint.getId());
                    if (failTime == null) {
                        failTime = Constant.SECOND_TIME_OZEO;
                    }
                    failTime = (failTime + 1);
                    failMapTimes.put(projectPoint.getId(), failTime);
                    if (failTime >= Constant.SECOND_TIME_FIVE) {
                        checkResultService.sendAndSaveEmail("地磅本地连接失败！", "非项目点的因素！", otherErrorFlag);
                        exceptionFlagMap.put(projectPoint.getId(),Constant.SYNCS_TATUS01);
                        //超过5次则清除
                        failTime = Constant.SECOND_TIME_OZEO;
                        failMapTimes.put(projectPoint.getId(), failTime);
                    } else {
                        checkTrade(projectPoint);
                    }
                    log.error(Constant.CHECK_TRADE_EXCEPTION);
                }
            }
        }
    }
}
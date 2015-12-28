package com.sq.db.service;

import com.sq.db.domain.CheckItem;
import com.sq.db.domain.Constant;
import com.sq.db.domain.ProjectPoint;
import com.sq.db.repository.ProjectPointRepository;
import com.sq.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    private Map<Long,Connection> dbConnectionMapmap = new HashMap<Long,Connection>();

    @Autowired
    private CheckResultService checkResultService;

    @Autowired
    private ProjectPointRepository projectPointRepository;

    @Autowired
    private ProjectPointService projectPointService;

    /***
     * 核查测点是否异常
     * @param  checkItem
     * @throws Exception
     */
    public void checkOriginal(CheckItem checkItem) throws Exception {
        //先去map缓存中查找是否存在此链接
        Connection conn = dbConnectionMapmap.get(checkItem.getProjectPoint().getId());
        //判断缓存中是否存在链接资源
        if (conn == null) {
            conn = projectPointService.instanceConnection(checkItem.getProjectPoint().getId());
            dbConnectionMapmap.put(checkItem.getProjectPoint().getId(),conn);
        }
        Statement stmt = conn.createStatement();
        //查询该表中最新一条记录
        String sql = "select * from t_originaldata t ORDER BY t.instanceTime desc limit 1";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            //获取数据库最近数据的时间
            time = rs.getDate("instanceTime") + " " + rs.getTime("instanceTime").toString();
        }
        //计算时间差值
        long minutes = DateUtil.getMinutesBetTwoCal(
                DateUtil.stringToCalendar(time, DateUtil.DATE_FORMAT_YMDHMS),
                Calendar.getInstance());
        //判断时间是否在正常范围内
        if(minutes > Constant.CHECK_TIME){
            //出现异常传入有异常的数据生成记录
            checkResultService.receiveDataAndSave(checkItem,time);
        }else{
            //传入正常的数据记录结果
            checkResultService.receiveDataAndSaveWellData(checkItem);
        }
    }
}

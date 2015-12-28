package com.sq.protocal.jdbc.service;

import com.sq.inject.annotation.BaseComponent;
import com.sq.jdbc.DblinkConnecter;
import com.sq.jdbc.JdbcHelper;
import com.sq.loadometer.domain.Trade;
import com.sq.loadometer.repository.TradeDataRepository;
import com.sq.protocal.jdbc.domain.JdbcProtocal;
import com.sq.protocal.jdbc.repository.JdbcProtocalRepository;
import com.sq.service.BaseService;
import com.sq.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * JDBC通信服务类
 * User: shuiqing
 * Date: 15/12/21
 * Time: 上午11:04
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Service
public class JdbcProtocalService extends BaseService<JdbcProtocal,Long> {

    private static final Logger log = LoggerFactory.getLogger(JdbcProtocalService.class);

    @BaseComponent
    @Autowired
    private JdbcProtocalRepository jdbcProtocalRepository;

    @Autowired
    private TradeDataRepository tradeDataRepository;

    /**
     * JDBC数据同步
     * @param sysCode 子系统编码
     */
    public void syncTradeRealTimeData(String sysCode) {
        JdbcProtocal jdbcProtocal = jdbcProtocalRepository.findJdbcProtocalBySysCode(sysCode);
        if(null == jdbcProtocal) {
            log.error("syncTradeRealTimeData jdbcProtocal is null. sysCode ->>> " + sysCode);
            return;
        }
        Calendar syncCal = Calendar.getInstance();
        syncCal.add(Calendar.HOUR_OF_DAY, -1);
        String syncDate = DateUtil.formatCalendar(syncCal,DateUtil.DATE_FORMAT_DAFAULT);
        syncLoadometerTrade(syncDate,jdbcProtocal);
    }

    /**
     * 地磅流水数据同步
     */
    private void syncLoadometerTrade (String syncCal,JdbcProtocal jdbcProtocal) {
        removeCurrDayTradeData(syncCal);
        insertCurrDayTradeData(syncCal,jdbcProtocal);
    }

    /**
     * 清除当日的已同步的流水数据
     * @param removeTradeDay 删除日期
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void removeCurrDayTradeData (String removeTradeDay) {
        tradeDataRepository.deleteDataBySecondTime(removeTradeDay);
    }

    /**
     * 填充当日的流水数据
     * @param fillTradeData 填充日期
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private void insertCurrDayTradeData (String fillTradeData,JdbcProtocal jdbcProtocal) {
        List<Trade> tradeList = new ArrayList<Trade>();

        StringBuilder insertTradeBuilder = new StringBuilder();
        insertTradeBuilder
                .append(jdbcProtocal.getSelectSql())
                .append(" ")
                .append(fillTradeData);

        JdbcHelper.conn = DblinkConnecter.connSqlserver(jdbcProtocal.getJdbcDriverType(),
                jdbcProtocal.getConnecturl(),
                jdbcProtocal.getUsername(),
                jdbcProtocal.getPassword());

        try {
            List<HashMap<String,String>> resultList = JdbcHelper.query(insertTradeBuilder.toString());
            for (HashMap tradeMap:resultList) {
                Trade trade = new Trade(tradeMap);
                tradeList.add(trade);
            }
        } catch (SQLException e) {
            log.error("执行query error：" + insertTradeBuilder.toString());
        }
        tradeDataRepository.save(tradeList);
    }

}

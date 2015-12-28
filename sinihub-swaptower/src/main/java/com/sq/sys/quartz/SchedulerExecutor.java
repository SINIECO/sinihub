package com.sq.sys.quartz;

import com.sq.loadometer.service.TradeDataService;
import com.sq.protocal.common.service.MesuringPointService;
import com.sq.protocal.common.service.OriginalDataService;
import com.sq.quota.service.QuotaComputInsService;
import com.sq.util.DateUtil;
import com.sq.util.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 定时任务执行业务类.
 * User: shuiqing
 * Date: 2015/4/7
 * Time: 10:26
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Component
public class SchedulerExecutor {

    private static final Logger log = LoggerFactory.getLogger(SchedulerExecutor.class);

    @Autowired
    private MesuringPointService mesuringPointService;

    @Autowired
    private OriginalDataService originalDataService;

    @Autowired
    private QuotaComputInsService quotaComputInsService;

    @Autowired
    private TradeDataService tradeDataService;

    /**
     * 原始测点数据迁移任务
     */
    public void execDcsDataMigration () {
        log.error("----------- Opc原始数据迁移任务开始 -----------");
        Calendar curr = Calendar.getInstance();
        curr.add(Calendar.DAY_OF_MONTH, -1);
        originalDataService.opcDataMigration(DateUtil.formatCalendar(curr));
        log.error("----------- Opc原始数据迁移任务结束 -----------");
    }
}

package com.sq.quartz;

import com.sq.db.domain.CheckPort;
import com.sq.db.service.CheckItemService;
import com.sq.db.service.CheckNetItemService;
import com.sq.db.service.CheckPortService;
import com.sq.db.service.CheckTradeItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class SchedulerExecuteService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerExecuteService.class);

    @Autowired
    private CheckItemService checkItemService;

    @Autowired
    private CheckNetItemService checkNetItemService;

    @Autowired
    private CheckTradeItemService checkTradeItemService;

    @Autowired
    private CheckPortService checkPortService;
    /**
     * 检查每个项目点通讯状态是否正常
     * @method checkAllProjectPoint
     */
    public void checkAllProjectPoint() {
        log.error("----------- 检查所有项目点的通讯状态任务开始 -----------");
         checkItemService.checkAllCheckItem();
        log.error("----------- 检查所有项目点的通讯状态任务结束 -----------");
    }

    /**
     * by Ywj 2015-12-29
     * 检查每个项目点的网络连通是否良好
     * @method checkAllItemNet
     */
    public void checkAllItemNet(){
        log.error("------------检查所有项目点的网络连通状态开始------------");
         checkNetItemService.checkAllNetCheckItem();
        log.error("------------检查所有项目点的网络连通状态结束------------");
    }

    /**
     * by Ywj 2015-12-30
     * 监测各个项目的地磅数据是否更新
     * @method checkAllTrade
     */
    public void checkAllTrade(){
        log.error("------------检查所有项目的地磅数据更新状态开始------------");
         checkTradeItemService.checkAllProjectPointTrade();
        log.error("------------检查所有项目的地磅数据更新状态结束------------");
    }

    /**
     * by Ywj 2016-01-08
     * 检查各个项目点的端口状态是否正常
     */
    public void checkAllPortReady(){
        log.error("------------检查所有项目的端口状态开始------------");
         checkPortService.checkAllPort();
        log.error("------------检查所有项目的端口状态结束------------");
    }
}

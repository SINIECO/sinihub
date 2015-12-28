package com.sq.quartz;

import com.sq.db.service.CheckItemService;
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

    /**
     * 检查每个项目点的内容
     */
    public void checkAllProjectPoint() {
        log.error("----------- 检查所有项目点的通讯状态任务开始 -----------");
        checkItemService.checkAllCheckItem();
        log.error("----------- 检查所有项目点的通讯状态任务结束 -----------");
    }
}

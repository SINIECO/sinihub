package com.sq.db.service;

import com.sq.db.domain.*;
import com.sq.db.repository.CheckResultRepository;
import com.sq.db.repository.EmailStatusRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ywj on 2015/12/28.
 * 监测网络连通情况数据服务类
 */
@Service("netResultService")
public class NetResultService {

    private static final Logger log = LoggerFactory.getLogger(NetResultService.class);

    @BaseComponent
    @Autowired
    private CheckResultRepository checkResultRepository;

    @Autowired
    private CheckResultService checkResultService;

    @Autowired
    private EmailStatusRepository emailStatusRepository;

    @Autowired
    private EmailStatusService emailStatusService;

    public void receiveDataAndSave(CheckItem checkItem,Integer countCon){
        //实例化CheckResult对象接收数据用于存到mysql数据库中
        CheckResult checkResult = new CheckResult();
        checkResult.setCheckItemId(checkItem.getProjectPoint().getId());
        checkResult.setProblemType(Constant.PROBLEM_TYPE03);
        checkResult.setSyncStatus(Constant.SYNCS_TATUS02);
        checkResult.setCheckTime(Calendar.getInstance());
        checkResultRepository.save(checkResult);
        //格式化当前日期
        String dateStr= DateUtil.formatCalendar(Calendar.getInstance());
        //传递数据发送邮件
        if(countCon == Constant.SECOND_TIME_TEN) {
            checkResultService.receiveQueueAndSend(checkResult,checkItem,dateStr,null);
            //查出对应数据库中邮箱的开关状态
            List<EmailStatus> emailStatusList = emailStatusRepository.findAll();
            for(EmailStatus e:emailStatusList) {
                if (checkItem.getId().intValue() == e.getCheckItemId().intValue()) {
                    emailStatusService.delete(e.getId());
                    e.setCheckItemId(checkItem.getId());
                    //可以发送邮箱
                    e.setStatus(Constant.EMAIL_STATUS_TWO);
                    emailStatusRepository.save(e);
                }
            }
        }
    }
}

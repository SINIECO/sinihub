package com.sq.db.service;

import com.sq.db.domain.*;
import com.sq.db.repository.CheckPortRepository;
import com.sq.db.repository.CheckResultRepository;
import com.sq.db.repository.EmailStatusRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.service.BaseService;
import com.sq.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ywj on 2016/1/8.
 */
@Service("portResultService")
public class PortResultService extends BaseService<CheckPort,Long> {

    private static final Logger log = LoggerFactory.getLogger(PortResultService.class);

    @BaseComponent
    @Autowired
    private CheckPortRepository checkPortRepository;


    @Autowired
    private CheckResultRepository checkResultRepository;

    @Autowired
    private CheckResultService checkResultService;

    @Autowired
    private EmailStatusRepository emailStatusRepository;

    @Autowired
    private EmailStatusService emailStatusService;

    public void receiveDataAndSave(CheckPort checkPort,Integer countCon){
        //实例化CheckResult对象接收数据用于存到mysql数据库中
        CheckResult checkResult = new CheckResult();
        checkResult.setCheckItemId(checkPort.getId());
        checkResult.setProblemType(Constant.PROBLEM_TYPE05);
        checkResult.setSyncStatus(Constant.SYNCS_TATUS02);
        checkResult.setCheckTime(Calendar.getInstance());
        checkResultRepository.save(checkResult);
        //格式化当前日期
        String dateStr= DateUtil.formatCalendar(Calendar.getInstance());
        //传递数据发送邮件
        if(countCon == Constant.SECOND_TIME_TEN) {
            checkResultService.receiveQueueAndSend(checkResult,null,dateStr,checkPort);
            //查出对应数据库中邮箱的开关状态
            List<EmailStatus> emailStatusList = emailStatusRepository.findAll();
            for(EmailStatus e:emailStatusList) {
                if (checkPort.getId().intValue() == e.getCheckItemId().intValue()) {
                    emailStatusService.delete(e.getId());
                    e.setCheckItemId(checkPort.getId());
                    //可以发送邮箱
                    e.setStatus(Constant.EMAIL_STATUS_TWO);
                    emailStatusRepository.save(e);
                }
            }
        }
    }
}

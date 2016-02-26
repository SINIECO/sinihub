package com.sq.db.service;

import com.sq.db.component.EmailUtil;
import com.sq.db.domain.*;
import com.sq.db.repository.CheckResultRepository;
import com.sq.db.repository.EmailItemRepository;
import com.sq.db.repository.EmailStatusRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by ywj on 2015/12/24.
 *  监测测点结果处理类
 */
@Service("checkResultService")
public class CheckResultService extends BaseService<CheckResult,Long> {

    private static final Logger log = LoggerFactory.getLogger(CheckResultService.class);

    @BaseComponent
    @Autowired
    private CheckResultRepository checkResultRepository;

    @Autowired
    private EmailItemService emailItemService;

    @Autowired
    private EmailStatusRepository emailStatusRepository;
    /** by ywj
     * 用于通讯异常
     * 接收并保存异常数据信息
     * @param checkItem
     */
    public void receiveDataAndSave(CheckItem checkItem,String time){
        //实例化CheckResult对象接收数据用于存到数据库中
        CheckResult checkResult = new CheckResult();
        checkResult.setCheckItemId(checkItem.getId());
        checkResult.setProblemType(Constant.PROBLEM_TYPE02);
        checkResult.setSyncStatus(Constant.SYNCS_TATUS02);
        checkResult.setCheckTime(Calendar.getInstance());
        checkResultRepository.save(checkResult);
        receiveQueueAndSend(checkResult, checkItem, time, null);
    }
    /** by ywj
     * 接收并保存正常数据信息
     * @param checkItem
     */
    public void receiveDataAndSaveWellData(CheckItem checkItem){
        //实例化CheckResult对象接收数据用于存到数据库中
        CheckResult checkResult = new CheckResult();
        checkResult.setCheckItemId(checkItem.getId());
        checkResult.setProblemType(Constant.PROBLEM_TYPE01);
        checkResult.setSyncStatus(Constant.SYNCS_TATUS01);
        checkResult.setCheckTime(Calendar.getInstance());
        checkResultRepository.save(checkResult);
    }

    /**
     * 组装各邮件信息
     * @param checkResult
     * @param checkItem
     */
    public void receiveQueueAndSend(CheckResult checkResult,CheckItem checkItem,String time,CheckPort checkPort){
        //接收邮件标题
        String subject = null;
        //接收邮件正文
        String text = null;
        //根据异常类型进行区分以及邮件标题内容的组装
        switch (checkResult.getProblemType()){
            case Constant.PROBLEM_TYPE02:
                subject = checkItem.getProjectPoint().getPointName()
                        + Constant.SLASH + checkItem.getSysName()
                        + Constant.SLASH + Constant.PROBLEM_TYPE02_DIS
                        + Constant.SLASH + time;
                text = subject;
                break;
            case Constant.PROBLEM_TYPE03:
                subject = checkItem.getProjectPoint().getPointName()
                        + Constant.SLASH + checkItem.getProjectPoint().getHospId()
                        + Constant.SLASH + Constant.PROBLEM_TYPE03_DIS
                        + Constant.SLASH + time;
                text = "如若是南京光大的网络，是由于连接数据库产生，其他项目点网络异常可以去看看";
                break;
            case Constant.PROBLEM_TYPE04:
                subject = checkItem.getProjectPoint().getPointName()
                        + Constant.SLASH + Constant.PROBLEM_TYPE04_DIS
                        + Constant.SLASH + time;
                text = subject;
                break;
            case Constant.PROBLEM_TYPE05:
                subject = checkPort.getProjectPoint().getHospId()
                        + Constant.SLASH
                        + checkPort.getPort()
                        + Constant.SLASH + Constant.PROBLEM_TYPE05_DIS
                        + Constant.SLASH + time;
                text = subject;
                break;
        }
        List<EmailStatus> emailStatusList = emailStatusRepository.findAll();
        for(EmailStatus e:emailStatusList){
            if(checkItem != null) {
                if (checkItem.getId().intValue() == e.getCheckItemId().intValue()) {
                    sendAndSaveEmail(subject, text, e.getStatus());
                }
            }
            if(checkPort != null) {
                if (checkPort.getId().intValue() == e.getCheckItemId().intValue()) {
                    sendAndSaveEmail(subject, text, e.getStatus());
                }
            }
        }
    }

    /**
     * 用于发送邮件以及存储邮件信息
     * @param subject
     * @param text
     */
    public void sendAndSaveEmail(String subject,String text,Integer status){
        if(status == Constant.EMAIL_STATUS_ONE){
            //加载属性文件获取收件人的信息
            Properties props = new EmailUtil().getProperty();
            EmailItem emailItem = new EmailItem();
            emailItem.setReceiver(props.getProperty("receiver"));
            emailItem.setSubject(subject);
            emailItem.setText(text);
            emailItem.setSendTime(Calendar.getInstance());
            try {
                //传入信息发送邮件
                new EmailUtil().getEmailServeAndSend(subject,text.toString());
                emailItem.setSendStatus(Constant.SEND_STATU01);
            } catch (MessagingException e) {
                emailItem.setSendStatus(Constant.SEND_STATU02);
                log.error(Constant.LOG_ERROR01);
            }
            //构造EmailItem对象存储邮件发送记录信息
            emailItemService.saveEmailRecord(emailItem);
        }
    }
}

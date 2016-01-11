package com.sq.db.service;

import com.sq.db.domain.CheckItem;
import com.sq.db.domain.CheckResult;
import com.sq.db.domain.Constant;
import com.sq.db.domain.ProjectPoint;
import com.sq.db.repository.CheckResultRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ywj on 2015/12/30.
 * 监测地磅结果服务类
 */
@Service("tradeResultService")
public class TradeResultService {
    @BaseComponent
    @Autowired
    private CheckResultRepository checkResultRepository;

    @Autowired
    private CheckResultService checkResultService;

    /**
     * 存储地磅异常情况信息到数据库
     * @param projectPoint
     */
    public void receiveDataAndSave(ProjectPoint projectPoint){
        //实例化CheckResult对象接收数据用于存到mysql数据库中
        CheckResult checkResult = new CheckResult();
        checkResult.setCheckItemId(projectPoint.getId());
        checkResult.setProblemType(Constant.PROBLEM_TYPE04);
        checkResult.setSyncStatus(Constant.SYNCS_TATUS02);
        checkResult.setCheckTime(Calendar.getInstance());
        checkResultRepository.save(checkResult);
        //传递数据发送邮件
        String dateStr= DateUtil.formatCalendar(Calendar.getInstance());
        String subject = projectPoint.getPointName()
                + Constant.SLASH + Constant.PROBLEM_TYPE04_DIS
                + Constant.SLASH + dateStr;
        checkResultService.sendAndSaveEmail(subject,subject,Constant.EMAIL_STATUS_ONE);
    }
}

package com.sq.db.service;

import com.sq.db.domain.EmailItem;
import com.sq.db.domain.EmailStatus;
import com.sq.db.repository.EmailStatusRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ywj on 2016/1/6.
 */
@Service("emailStatusService")
public class EmailStatusService extends BaseService<EmailStatus,Long> {
    @BaseComponent
    @Autowired
    private EmailStatusRepository emailStatusRepository;
}

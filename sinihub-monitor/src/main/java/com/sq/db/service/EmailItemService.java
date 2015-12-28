package com.sq.db.service;

import com.sq.db.domain.EmailItem;
import com.sq.db.repository.CheckItemRepository;
import com.sq.db.repository.EmailItemRepository;
import com.sq.inject.annotation.BaseComponent;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ywj on 2015/12/23.
 * 邮件发送记录服务类
 */
@Service("emailItemService")
public class EmailItemService extends BaseService<EmailItem,Long>{

    private static final Logger log = LoggerFactory.getLogger(EmailItemService.class);

    @BaseComponent
    @Autowired
    private EmailItemRepository emailItemRepository;

    /**
     * 将接收邮件的记录保存到数据库中
     * @param emailItem
     */
    public void saveEmailRecord(EmailItem emailItem){
        emailItemRepository.save(emailItem);
    }
}

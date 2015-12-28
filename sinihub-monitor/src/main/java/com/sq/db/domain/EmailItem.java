package com.sq.db.domain;

import com.sq.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by ywj on 2015/12/23.
 * 邮箱信息实体类
 */
@Entity
@Table(name="t_emailreceiverecord")
public class EmailItem extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    //收件人地址
    private String receiver;
    //邮件主题
    private String subject;
    //主体内容
    private String text;
    //发送时间
    private Calendar sendTime;
    //发送状态
    private String sendStatus;

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSendTime(Calendar sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Calendar getSendTime() {
        return sendTime;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}

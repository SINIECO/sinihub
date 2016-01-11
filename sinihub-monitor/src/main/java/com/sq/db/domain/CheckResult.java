package com.sq.db.domain;

import com.sq.entity.AbstractEntity;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ywj on 2015/12/22.
 */
@Entity
@Table(name = "t_checkresult")
public class CheckResult extends AbstractEntity<Long>{

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    //异常数据主键
    private Long checkItemId;
    //异常类型
    private Integer problemType;
    //核查时间
    private Calendar checkTime;
    //状态
    private Integer syncStatus;

    public CheckResult() {
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public void setSyncStatus(Integer syncStatus) {
        this.syncStatus = syncStatus;
    }

    public void setProblemType(Integer problemType) {
        this.problemType = problemType;
    }

    public void setCheckItemId(Long checkItemId) {
        this.checkItemId = checkItemId;
    }

    public Integer getSyncStatus() {
        return syncStatus;
    }

    public Integer getProblemType() {
        return problemType;
    }

    public Long getCheckItemId() {
        return checkItemId;
    }

    public Long getId() {
        return Id;
    }

    public Calendar getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Calendar checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public String toString() {
        return "CheckResult{" +
                "Id=" + Id +
                ", checkItemId=" + checkItemId +
                ", problemType=" + problemType +
                ", checkTime=" + checkTime +
                ", syncStatus=" + syncStatus +
                '}';
    }
}

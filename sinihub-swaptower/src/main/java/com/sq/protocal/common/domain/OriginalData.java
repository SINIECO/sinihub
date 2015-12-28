package com.sq.protocal.common.domain;

import com.sq.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * 测点同步结果集.
 * User: shuiqing
 * Date: 2015/4/6
 * Time: 19:07
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Entity
@Table(name = "t_OriginalData")
public class OriginalData extends AbstractEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false, precision=10)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /** 指标的CODE */
    private String itemCode;

    /** 指标的同步值 */
    private String itemValue;

    /** 获取指标实例的时间点 */
    private Calendar instanceTime;

    /** 系统编码 */
    private String sysCode;

    /** 测点品质 */
    private boolean quality;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public Calendar getInstanceTime() {
        return instanceTime;
    }

    public void setInstanceTime(Calendar instanceTime) {
        this.instanceTime = instanceTime;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public boolean isQuality() {
        return quality;
    }

    public void setQuality(boolean quality) {
        this.quality = quality;
    }
}

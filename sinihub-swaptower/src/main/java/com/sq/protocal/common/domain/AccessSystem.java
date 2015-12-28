package com.sq.protocal.common.domain;

import com.sq.entity.AbstractEntity;

import javax.persistence.*;

/**
 * 接入系统主体对象
 * User: shuiqing
 * Date: 15/12/10
 * Time: 下午2:20
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Entity
@Table(name = "t_AccessSystem")
public class AccessSystem extends AbstractEntity<Long> {

    private static final long serialVersionUID = -7786513455849179469L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /** 接入系统的编码 */
    private String sysCode;

    /** 接入系统的名称 */
    private String sysName;

    /** 接入系统的描述 */
    private String description;

    /** 该子系统所使用的通讯协议 */
    private int protocalType;

    /** 数据同步cron表达式 */
    private String syncCronExp;

    /** 是否同步 */
    private boolean isSync;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProtocalType() {
        return protocalType;
    }

    public void setProtocalType(int protocalType) {
        this.protocalType = protocalType;
    }

    public String getSyncCronExp() {
        return syncCronExp;
    }

    public void setSyncCronExp(String syncCronExp) {
        this.syncCronExp = syncCronExp;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }
}

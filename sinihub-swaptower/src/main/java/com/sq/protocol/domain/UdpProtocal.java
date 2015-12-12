package com.sq.protocol.domain;

import com.sq.entity.AbstractEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * UDP通讯协议
 * User: shuiqing
 * Date: 15/12/10
 * Time: 下午3:15
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class UdpProtocal extends AbstractEntity<Long> {

    private static final long serialVersionUID = -5097907113575595489L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /** 关联的接入系统对象 */
    @OneToOne(cascade= CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinColumn(name="sysId")
    @org.hibernate.annotations.ForeignKey(name="fk_up_sysId")
    @NotFound(action= NotFoundAction.IGNORE)
    private AccessSystem accessSystem;

    /** 接入系统编码 */
    private String sysCode;

    /** 目标IP地址 */
    private String hostIp;

    /** 端口号 */
    private String port;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public AccessSystem getAccessSystem() {
        return accessSystem;
    }

    public void setAccessSystem(AccessSystem accessSystem) {
        this.accessSystem = accessSystem;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}

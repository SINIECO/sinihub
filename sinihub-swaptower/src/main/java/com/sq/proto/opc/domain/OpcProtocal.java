package com.sq.proto.opc.domain;

import com.sq.entity.AbstractEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * opc通讯协议
 * User: shuiqing
 * Date: 15/12/10
 * Time: 下午3:04
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class OpcProtocal extends AbstractEntity<Long> {

    private static final long serialVersionUID = -3259152133351215055L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /** 接入系统编码 */
    private String sysCode;

    /** opc接入驱动类型 */
    private int opcDriverType;

    /** 目标IP地址 */
    private String hostIp;

    /** 域名称 */
    private String domain;

    /** 用户名 */
    private String userName;

    /** 登录密码 */
    private String password;

    /** 注册表id */
    private String clsid;

    /** 对外服务的名称 */
    private String progid;

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

    public int getOpcDriverType() {
        return opcDriverType;
    }

    public void setOpcDriverType(int opcDriverType) {
        this.opcDriverType = opcDriverType;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClsid() {
        return clsid;
    }

    public void setClsid(String clsid) {
        this.clsid = clsid;
    }

    public String getProgid() {
        return progid;
    }

    public void setProgid(String progid) {
        this.progid = progid;
    }
}

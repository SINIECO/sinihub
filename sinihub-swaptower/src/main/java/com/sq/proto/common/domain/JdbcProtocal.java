package com.sq.proto.common.domain;

import com.sq.entity.AbstractEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Jdbc通讯协议
 * User: shuiqing
 * Date: 15/12/10
 * Time: 下午3:26
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class JdbcProtocal extends AbstractEntity<Long> {

    private static final long serialVersionUID = 8158795029755892808L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /** 关联的接入系统对象 */
    @OneToOne(cascade= CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinColumn(name="sysId")
    @org.hibernate.annotations.ForeignKey(name="fk_jp_sysId")
    @NotFound(action= NotFoundAction.IGNORE)
    private AccessSystem accessSystem;

    /** 接入系统编码 */
    private String sysCode;

    /** jdbc驱动类型 */
    private int jdbcDriverType;

    /** 目标IP地址 */
    private String hostIp;

    /** 端口号 */
    private String port;

    /** 数据库名称 */
    private String dbname;

    /** 连接字符串 */
    private String connecturl;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 数据查询sql  用来从目标数据库中查询相应数据 */
    private String selectSql;

    /** 数据转化倍率 */
    private String converRatio;

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

    public int getJdbcDriverType() {
        return jdbcDriverType;
    }

    public void setJdbcDriverType(int jdbcDriverType) {
        this.jdbcDriverType = jdbcDriverType;
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

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getConnecturl() {
        return connecturl;
    }

    public void setConnecturl(String connecturl) {
        this.connecturl = connecturl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    public String getConverRatio() {
        return converRatio;
    }

    public void setConverRatio(String converRatio) {
        this.converRatio = converRatio;
    }
}

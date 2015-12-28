package com.sq.protocal.socket.domain;

import com.sq.entity.AbstractEntity;

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
@Entity
@Table(name = "t_UdpProtocal")
public class UdpProtocal extends AbstractEntity<Long> {

    private static final long serialVersionUID = -5097907113575595489L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /** 接入系统编码 */
    private String sysCode;

    /** 目标IP地址 */
    private String hostIp;

    /** 端口号 */
    private String port;

    /** 数据更新速度 */
    private int udpUpdateRate;

    /** 数据同步速度 */
    private int syncRate;

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

    public int getSyncRate() {
        return syncRate;
    }

    public void setSyncRate(int syncRate) {
        this.syncRate = syncRate;
    }

    public int getUdpUpdateRate() {
        return udpUpdateRate;
    }

    public void setUdpUpdateRate(int udpUpdateRate) {
        this.udpUpdateRate = udpUpdateRate;
    }
}

package com.sq.proto.common.service;

import com.sq.inject.annotation.BaseComponent;
import com.sq.proto.common.domain.AccessSystem;
import com.sq.proto.jdbc.domain.JdbcProtocal;
import com.sq.proto.socket.domain.UdpProtocal;
import com.sq.proto.common.repository.AccessSystemRepository;
import com.sq.proto.jdbc.repository.JdbcProtocalRepository;
import com.sq.proto.opc.repository.OpcProtocalRepository;
import com.sq.proto.socket.repository.UdpProtocalRepository;
import com.sq.proto.opc.domain.OpcProtocal;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 接入系统服务类
 * User: shuiqing
 * Date: 15/12/14
 * Time: 下午3:49
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Service
public class AccessSystemService extends BaseService<AccessSystem, Long> {

    private static final Logger log = LoggerFactory.getLogger(MesuringPointService.class);

    @BaseComponent
    @Autowired
    private AccessSystemRepository accessSystemRepository;

    @Autowired
    private OpcProtocalRepository opcProtocalRepository;

    @Autowired
    private UdpProtocalRepository udpProtocalRepository;

    @Autowired
    private JdbcProtocalRepository jdbcProtocalRepository;

    /**
     * 获取子系统基本信息
     * @param sysCode 子系统编码
     * @return 子系统基本信息
     */
    public AccessSystem fetchAccessSystemConfig(String sysCode) {
        return accessSystemRepository.findAccessSystemBySysCode(sysCode);
    }

    /**
     * 获取opc通信的配置信息
     * @param sysCode 子系统编码
     * @return opc配置信息
     */
    public OpcProtocal fetchOpcProtocalConfig(String sysCode) {
        return opcProtocalRepository.findOpcProtocalBySysCode(sysCode);
    }

    /**
     * 获取jdbc通信的配置信息
     * @param sysCode 子系统编码
     * @return jdbc通信的配置信息
     */
    public JdbcProtocal fetchJdbcProtocalConfig(String sysCode) {
        return jdbcProtocalRepository.findJdbcProtocalBySysCode(sysCode);
    }

    /**
     * 获取udp通信的配置信息
     * @param sysCode 子系统编码
     * @return udp通信的配置信息
     */
    public UdpProtocal fetchUdpProtocalConfig(String sysCode) {
        return udpProtocalRepository.findUdpProtocalBySysCode(sysCode);
    }
}

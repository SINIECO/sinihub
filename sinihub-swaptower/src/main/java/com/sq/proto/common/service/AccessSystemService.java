package com.sq.proto.common.service;

import com.sq.inject.annotation.BaseComponent;
import com.sq.proto.common.domain.AccessSystem;
import com.sq.proto.common.repository.AccessSystemRepository;
import com.sq.proto.common.repository.JdbcProtocalRepository;
import com.sq.proto.common.repository.OpcProtocalRepository;
import com.sq.proto.common.repository.UdpProtocalRepository;
import com.sq.proto.opc.service.MesuringPointService;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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


}

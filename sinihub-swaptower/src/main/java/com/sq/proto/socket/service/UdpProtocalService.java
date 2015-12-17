package com.sq.proto.socket.service;

import com.sq.inject.annotation.BaseComponent;
import com.sq.proto.socket.domain.UdpProtocal;
import com.sq.proto.socket.repository.UdpProtocalRepository;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * UDP通信服务类
 * User: shuiqing
 * Date: 15/12/17
 * Time: 下午4:06
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class UdpProtocalService extends BaseService<UdpProtocal, Long> {

    private static final Logger log = LoggerFactory.getLogger(UdpProtocalService.class);

    @BaseComponent
    @Autowired
    private UdpProtocalRepository udpProtocalRepository;
}

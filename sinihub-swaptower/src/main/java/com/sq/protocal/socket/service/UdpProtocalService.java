package com.sq.protocal.socket.service;

import com.sq.inject.annotation.BaseComponent;
import com.sq.protocal.socket.component.UdpReceiverThread;
import com.sq.protocal.socket.domain.UdpProtocal;
import com.sq.protocal.socket.repository.UdpProtocalRepository;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class UdpProtocalService extends BaseService<UdpProtocal, Long> {

    private static final Logger log = LoggerFactory.getLogger(UdpProtocalService.class);

    @BaseComponent
    @Autowired
    private UdpProtocalRepository udpProtocalRepository;

    /**
     * 开启UDP数据接收服务
     * @param sysCode 子系统编码
     */
    public void startUdpDataReceiveService(String sysCode) {
        UdpProtocal udpProtocal = udpProtocalRepository.findUdpProtocalBySysCode(sysCode);
        if (null == udpProtocal) {
            log.error("startUdpDataReceiveService udpProtocal is null. sysCode ->>> " + sysCode);
            return;
        }

        UdpReceiverThread udpReceiverThread = new UdpReceiverThread();
        udpReceiverThread.setUdpProtocal(udpProtocal);
        udpReceiverThread.start();
    }
}

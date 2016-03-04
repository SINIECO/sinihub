package com.sq.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * UDP数据发送服务.
 * User: shuiqing
 * Date: 2015/7/30
 * Time: 15:12
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Component
public class UdpSender {

    private static final Logger log = LoggerFactory.getLogger(UdpSender.class);

    public static DatagramSocket datagramSocket;

    /**
     * 初始化socket
     */
    @PostConstruct
    public static void init () {
        datagramSocket = initUdpSenderService();
    }

    /**
     * 初始化UDP数据发送器
     * @return
     */
    public static DatagramSocket initUdpSenderService () {
        UdpSocketCfg.loadConfigProperties();
        return UdpSocketCfg.initUdpServer();
    }

    /**
     * 拼装 待发送的数据包
     * @param sendMsg
     * @return
     */
    public static DatagramPacket assemblyDatagramPacket(String sendMsg) {
        log.error("------ sendMsg:" + sendMsg);
        byte[] sendByteMsg = sendMsg.getBytes();
        DatagramPacket datagramPacket = null;
        try {
            String ipConfigStr = UdpSocketCfg.RECEIVE_IP;
            String[] ipStr = ipConfigStr.split("\\.");
            byte[] ipBuf = new byte[4];
            for(int i = 0; i < 4; i++){
                ipBuf[i] = (byte)(Integer.parseInt(ipStr[i])&0xff);
            }
            datagramPacket = new DatagramPacket(
                    sendByteMsg,
                    sendByteMsg.length,
                    InetAddress.getByAddress(ipBuf),
                    UdpSocketCfg.RECEIVE_PORT);
        } catch (UnknownHostException e) {
            log.error("发送的目标地址错误.",e);
        }
        return datagramPacket;
    }
}

package com.sq.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Properties;

/**
 * UDP 配置相关.
 * User: shuiqing
 * Date: 2015/7/30
 * Time: 11:20
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Component
public class UdpSocketCfg {

    private static final Logger log = LoggerFactory.getLogger(UdpSocketCfg.class);

    private static Properties prop;

    public static String CONFIG_SEND_PORT = "SEND_PORT";

    public static String CONFIG_RECEIVE_IP = "RECEIVE_IP";

    public static String CONFIG_RECEIVE_PORT = "RECEIVE_PORT";

    public static String CONFIG_DATAPACKET_SIZE = "DATAPACKET_SIZE";

    public static String CONFIG_SENDER_FOR_SYSTEM = "SENDER_FOR_SYSTEM";

    public static int SEND_PORT;

    public static String RECEIVE_IP;

    public static int RECEIVE_PORT;

    public static int DATAPACKET_SIZE;

    public static int SENDER_FOR_SYSTEM;

    /** 配置文件路径 */
    private final static String CONFIG_FILE_NAME = "/conf/udp-socket-config.properties";

    private static byte[] buf = new byte[1024];

    private static DatagramSocket ds;

    /**
     * 加载socket服务的配置文件
     * @return
     */
    public static Properties loadConfigProperties () {
        prop = new Properties();
        try {
            prop.load(UdpSocketCfg.class.getResourceAsStream(CONFIG_FILE_NAME));
            SEND_PORT = Integer.parseInt(prop.getProperty(CONFIG_SEND_PORT));
            RECEIVE_IP = prop.getProperty(CONFIG_RECEIVE_IP);
            RECEIVE_PORT = Integer.parseInt(prop.getProperty(CONFIG_RECEIVE_PORT));
            DATAPACKET_SIZE = Integer.parseInt(prop.getProperty(CONFIG_DATAPACKET_SIZE));
            SENDER_FOR_SYSTEM = Integer.parseInt(prop.getProperty(CONFIG_SENDER_FOR_SYSTEM));
        } catch (IOException e) {
            log.error("Utgard opc 加载" + CONFIG_FILE_NAME + "配置文件出错.", e);
        }
        return prop;
    }

    /** 初始化UDP server */
    public static DatagramSocket initUdpServer() {
        try {
            ds = new DatagramSocket(SEND_PORT);
            System.out.println("server is on，waiting for send data to client......");
        } catch (SocketException e) {
            log.error("新建DatagramSocket出现错误.",e);
        }
        return ds;
    }
}

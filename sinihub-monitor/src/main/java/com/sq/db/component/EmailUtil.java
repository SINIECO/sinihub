package com.sq.db.component;

import com.sq.db.domain.Constant;
import com.sq.db.domain.EmailItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * Created by ywj on 2015/12/23.
 * 邮箱工具类
 */
@Component
public class EmailUtil {

    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);

    private final static String CONFIG_FILE_NAME = "/conf/email_sender.properties";

    /**
     * 获取属性文件
     * @return
     */
    public static Properties getProperty(){
        Properties prop = new Properties();
        try {
            //加载属性文件获取资源
            prop.load(EmailUtil.class.getResourceAsStream(CONFIG_FILE_NAME));
        } catch (IOException e) {
            log.error(Constant.LOAD_EMAIL_PROP_FAIL);
        }
        return prop;
    }
    //类加载的时候调用
    static {
        getProperty();
    }
    /**
     *取得邮箱服务并发送邮箱
     * @param subject
     * @param text
     * @throws MessagingException
     */
    public static void getEmailServeAndSend(String subject,String text)throws MessagingException {
        Properties props = getProperty();
        String [] receivers = props.getProperty("receiver").split("->");
        for(int i = 0;i < receivers.length;i++){
            // 开启debug调试
            props.setProperty("mail.debug",props.getProperty("mail.debug"));
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth",props.getProperty("mail.smtp.auth"));
            // 设置邮件服务器主机名
            props.setProperty("mail.host",props.getProperty("host"));
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol",props.getProperty("protocol"));
            // 设置环境信息
            Session session = Session.getInstance(props);
            // 创建邮件对象
            Message msg = new MimeMessage(session);
            msg.setSubject(subject);
            // 设置邮件内容
            msg.setText(text);
            // 设置发件人
            msg.setFrom(new InternetAddress(props.getProperty("sender")));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(receivers[i]));
            Transport transport = session.getTransport();
            // 连接邮件服务器
            System.out.println(receivers.length);
            transport.connect(props.getProperty("username"),props.getProperty("pwd"));
            // 发送邮件
            transport.sendMessage(msg,msg.getAllRecipients());
            // 关闭连接
            transport.close();
        }
    }
}

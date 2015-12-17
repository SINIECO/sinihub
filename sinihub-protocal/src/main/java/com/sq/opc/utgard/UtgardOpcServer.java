package com.sq.opc.utgard;

import com.sq.Constants;
import com.sq.OriginalItem;
import com.sq.opc.OpcBaseServer;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIUnsignedInteger;
import org.jinterop.dcom.core.JIUnsignedShort;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.da.OPCSERVERSTATE;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * opc server info.
 *    keep a instance of connected OPC SERVER.
 * User: shuiqing
 * Date: 15/12/10
 * Time: 下午4:41
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class UtgardOpcServer extends OpcBaseServer{

    private static final Logger log = LoggerFactory.getLogger(UtgardOpcServer.class);

    public Server server;

    /** 连接信息 */
    private ConnectionInformation connectionInformation;

    public static class Builder {

        private String sysCode;

        private String hostip;

        private String username;

        private String password;

        private String clsid = "";

        private String progid = "";

        private String domain = "";

        public Builder(String sysCode, String username, String password, String hostip) {
            this.sysCode = sysCode;
            this.username = username;
            this.password = password;
            this.hostip = hostip;
        }

        public Builder clsid(String clsid) {
            this.clsid = clsid;
            return this;
        }

        public Builder progid(String progid) {
            this.progid = progid;
            return this;
        }

        public Builder domain(String domain) {
            this.domain = domain;
            return this;
        }

        public UtgardOpcServer build(){
            return new UtgardOpcServer(this);
        }
    }

    public UtgardOpcServer(Builder builder) {
        this.sysCode = builder.sysCode;
        this.connectionInformation = new ConnectionInformation();
        this.connectionInformation.setHost(builder.hostip);
        this.connectionInformation.setUser(builder.username);
        this.connectionInformation.setPassword(builder.password);
        this.connectionInformation.setClsid(builder.clsid);
        this.connectionInformation.setProgId(builder.progid);
        this.connectionInformation.setDomain(builder.domain);
    }

    /***
     * 连接指定的远程opc服务
     */
    public void connectRemoteOpcService(){
        server = new Server(
                connectionInformation,
                Executors.newSingleThreadScheduledExecutor());
        try {
            server.connect();
        } catch (UnknownHostException e) {
            log.error("目标host的地址错误", e);
        } catch (JIException e) {
            log.error("获取配置文件中内容时出错",e);
        } catch (AlreadyConnectedException e) {
            log.error("连接已经存在，无需再次连接",e);
        }
    }

    /***
     * opc server当前状态
     */
    public OPCSERVERSTATE currentServerStatus() {
        return server.getServerState().getServerState();
    }

    /**
     * 校验测点
     * @param opcItem  测点名
     * @return  校验结果
     */
    public int validOpcItem (String opcItem){
        try {
            Group group = server.addGroup();
            group.setActive(true);
            group.addItem(opcItem);
        }catch (AddFailedException ae){
            return Constants.OPC_ITEM_VALID_ITEMERROR;
        } catch (Exception e) {
            log.error("validOpcItem connect error.");
        }

        return Constants.OPC_ITEM_VALID_SUCCESS;
    }

    @Override
    public String toString() {
        return "UtgardOpcServer{" +
                "syscode=" + sysCode +
                ", host=" + connectionInformation.getHost() +
                ", user=" + connectionInformation.getUser() +
                ", pwd=" + connectionInformation.getPassword() +
                ", domain=" + connectionInformation.getDomain() +
                ", clsid=" + connectionInformation.getClsid() +
                ", progid=" + connectionInformation.getProgId() +
                '}';
    }

}

package com.sq.opc.utgard;

import com.sq.Constants;
import com.sq.OriginalItem;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIUnsignedInteger;
import org.jinterop.dcom.core.JIUnsignedShort;
import org.jinterop.dcom.core.JIVariant;
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
public class UtgardOpcServer {

    private static final Logger log = LoggerFactory.getLogger(UtgardOpcServer.class);

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
        this.conn_status = Constants.OPC_CONNECT_STATUS_DISCONN;
        this.connectionInformation = new ConnectionInformation();
        this.connectionInformation.setHost(builder.hostip);
        this.connectionInformation.setUser(builder.username);
        this.connectionInformation.setPassword(builder.password);
        this.connectionInformation.setClsid(builder.clsid);
        this.connectionInformation.setProgId(builder.progid);
        this.connectionInformation.setDomain(builder.domain);
    }

    private String sysCode;

    private Integer conn_status;

    private Server server;

    private Group group;

    private Item[] itemArr;

    /** 叶子节点 */
    /*private Collection<Leaf> leafs;*/

    /** 连接信息 */
    private ConnectionInformation connectionInformation;

    public ConnectionInformation getConnectionInformation() {
        return connectionInformation;
    }

    public void setConnectionInformation(ConnectionInformation connectionInformation) {
        this.connectionInformation = connectionInformation;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public Integer getConn_status() {
        return conn_status;
    }

    public void setConn_status(Integer conn_status) {
        this.conn_status = conn_status;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Item[] getItemArr() {
        return itemArr;
    }

    public void setItemArr(Item[] itemArr) {
        this.itemArr = itemArr;
    }

    /***
     * 连接指定的opc服务
     */
    public void connectOpcService(){
        server = new Server(
                connectionInformation,
                Executors.newSingleThreadScheduledExecutor());
        try {
            server.connect();
            this.setConn_status(Constants.OPC_CONNECT_STATUS_MISGROUPANDITEM);
        } catch (UnknownHostException e) {
            log.error("目标host的地址错误", e);
        } catch (JIException e) {
            log.error("获取配置文件中内容时出错",e);
        } catch (AlreadyConnectedException e) {
            log.error("连接已经存在，无需再次连接",e);
        }
    }

    /**
     * 注册opc服务的group和item列表
     * @param mesuringPointList 测点编码列表
     */
    public void registerGroupAndItems (List<String> mesuringPointList){
        try {
            int item_flag = 0;
            group = server.addGroup();
            group.setActive(true);
            itemArr = new Item[mesuringPointList.size()];
            for (String mesuringpoint:mesuringPointList) {
                Item item = null;
                try {
                    item = group.addItem(mesuringpoint);
                } catch (AddFailedException e) {
                    log.error("Group add error.itemCode：" + mesuringpoint,e);
                    mesuringPointList.remove(mesuringpoint);
                    registerGroupAndItems(mesuringPointList);
                }
                item.setActive(true);
                log.debug("ItemName:[" + item.getId()
                        + "],value:" + item.read(true).getValue());
                itemArr[item_flag] = item;
                item_flag++;
            }
            this.setConn_status(Constants.OPC_CONNECT_STATUS_READY);
        } catch (UnknownHostException e) {
            log.error("Host unknow error.", e);
        } catch (NotConnectedException e) {
            log.error("Connnect to opc error.", e);
        } catch (JIException e) {
            log.error("Opc server connect error.", e);
        } catch (DuplicateGroupException e) {
            log.error("Group duplicate error.", e);
        }
    }

    public List<OriginalItem> syncIoItems() {
        List<OriginalItem> originalItemList = new ArrayList<OriginalItem>();
        Map<Item, ItemState> syncItems = null;
        try {
            /** arg1 false 读取缓存数据 OPCDATASOURCE.OPC_DS_CACHE  */
            syncItems = group.read(true, itemArr);
            log.debug(this.toString() + ",syncItems size:" + syncItems.size());
            for (Map.Entry<Item, ItemState> entry : syncItems.entrySet()) {
                String itemValue = entry.getValue().getValue().toString();
                String itemCode = entry.getKey().getId();
                try {
                    log.debug("key= " + entry.getKey().getId()
                            + " and value= " + entry.getValue().getValue().toString()
                            + " and type= " + entry.getValue().getValue().getType());
                    JIVariant jiVariant = entry.getValue().getValue();
                    switch (jiVariant.getType()) {
                        case JIVariant.VT_BSTR:
                            JIString jiString = (JIString)jiVariant.getObject();
                            itemValue = jiString.getString();
                            break;
                        case JIVariant.VT_UI2:
                            JIUnsignedShort jiUnsignedShort = (JIUnsignedShort)jiVariant.getObject();
                            itemValue = jiUnsignedShort.getValue().toString();
                            break;
                        case JIVariant.VT_UI4:
                            JIUnsignedInteger jiUnsignedInteger = (JIUnsignedInteger)jiVariant.getObject();
                            itemValue = jiUnsignedInteger.getValue().toString();
                            break;
                        default:
                            itemValue = jiVariant.getObject().toString();
                    }
                    log.debug("UtgardOpcServer->syncIoItems:type-> " + jiVariant.getType() + " ,value -->  " + itemValue);
                } catch (JIException e) {
                    log.error("获取JIVariant数据出错.",e);
                }
                if (itemValue.contains("org.jinterop.dcom.core.VariantBody$EMPTY")) {
                    continue;
                }
                OriginalItem originalItem = new OriginalItem();
                originalItem.setItemCode(itemCode);
                originalItem.setItemValue(itemValue);
                originalItemList.add(originalItem);
            }
        } catch (JIException e) {
            log.error("Read item error.",e);
            this.setConn_status(Constants.OPC_CONNECT_STATUS_DISCONN);
        }
        return originalItemList;
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
                ", conn_status=" + conn_status +
                ", host=" + connectionInformation.getHost() +
                ", user=" + connectionInformation.getUser() +
                ", pwd=" + connectionInformation.getPassword() +
                ", domain=" + connectionInformation.getDomain() +
                ", clsid=" + connectionInformation.getClsid() +
                ", progid=" + connectionInformation.getProgId() +
                /*", leafs=" + ((leafs == null)?0:leafs.size()) +*/
                '}';
    }

    public static void main(String[] args) {
        UtgardOpcServer utgardOpcServer = new UtgardOpcServer
                .Builder("DCS","Administrator","123456","192.168.88.131")
                .clsid("f8582cf2-88fb-11d0-b850-00c0f0104305")
                .build();
        utgardOpcServer.connectOpcService();
        List<String> mlist = new ArrayList<String>();
        mlist.add("Random.Int2");
        utgardOpcServer.registerGroupAndItems(mlist);
        System.out.println(utgardOpcServer.toString());
    }
}

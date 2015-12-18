package com.sq.opc.utgard;

import com.sq.Constants;
import com.sq.OriginalItem;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIUnsignedInteger;
import org.jinterop.dcom.core.JIUnsignedShort;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.da.OPCSERVERSTATE;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 数据订阅服务
 * User: shuiqing
 * Date: 15/12/15
 * Time: 上午10:07
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class DataSubscription {

    private static final Logger log = LoggerFactory.getLogger(UtgardOpcServer.class);

    private UtgardOpcServer utgardOpcServer;

    private Group group;

    private Item[] itemArr;

    public Integer conn_status;

    public DataSubscription() {
        this.conn_status = Constants.OPC_CONNECT_STATUS_DISCONN;
    }

    public UtgardOpcServer getUtgardOpcServer() {
        return utgardOpcServer;
    }

    public void setUtgardOpcServer(UtgardOpcServer utgardOpcServer) {
        this.utgardOpcServer = utgardOpcServer;
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

    public Integer getConn_status() {
        return conn_status;
    }

    public void setConn_status(Integer conn_status) {
        this.conn_status = conn_status;
    }

    /**
     * 注册opc服务的group和item列表
     * @param mesuringPointList 测点编码列表
     */
    public void registerGroupAndItems (List<String> mesuringPointList){
        try {
            int item_flag = 0;
            group = utgardOpcServer.server.addGroup();
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
            this.conn_status = Constants.OPC_CONNECT_STATUS_READY;
        } catch (UnknownHostException e) {
            log.error("Host unknow error.", e);
        } catch (NotConnectedException e) {
            log.error("ConnnecregisterGroupAndItemst to opc error.", e);
        } catch (JIException e) {
            log.error("Opc server connect error.", e);
        } catch (DuplicateGroupException e) {
            log.error("Group duplicate error.", e);
        }
    }

    /***
     * 同步指定的测点，并返回实时数据
     * @return 实时数据
     */
    public List<OriginalItem> syncAccessItem() {
        List<OriginalItem> originalItemList = new ArrayList<OriginalItem>();
        Map<Item, ItemState> syncItems = null;
        try {
            /** arg1 false 读取缓存数据 OPCDATASOURCE.OPC_DS_CACHE  */
            syncItems = group.read(true, itemArr);
            log.error(this.toString() + ",syncItems size:" + syncItems.size());

            for (Map.Entry<Item, ItemState> entry : syncItems.entrySet()) {
                String itemValue = entry.getValue().getValue().toString();
                String itemCode = entry.getKey().getId();
                try {

                    log.error("key= " + entry.getKey().getId()
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
                originalItem.setInstanceTime(entry.getValue().getTimestamp());
                originalItemList.add(originalItem);
            }
        } catch (JIException e) {
            log.error("Read item error.",e);
        }
        return originalItemList;
    }


    /***
     * 检查当前服务器的状态
     * @return 当前服务连接是否正常
     */
    public boolean checkServerStatus() {
        if (utgardOpcServer.currentServerStatus() == OPCSERVERSTATE.OPC_STATUS_RUNNING) {
            return true;
        } else return false;
    }

    public static void main(String[] args) {
        DataSubscription dataSubscription = new DataSubscription();
        UtgardOpcServer utgardOpcServer = new UtgardOpcServer
                .Builder("DCS","Administrator","123456","192.168.88.131")
                .clsid("f8582cf2-88fb-11d0-b850-00c0f0104305")
                .build();
        utgardOpcServer.connectRemoteOpcService();
        List<String> mlist = new ArrayList<String>();
        mlist.add("Random.Int2");
        dataSubscription.registerGroupAndItems(mlist);
        dataSubscription.syncAccessItem();
        System.out.println(utgardOpcServer.toString());
    }
}

package com.sq.opc.service;

import com.sq.opc.component.BaseConfiguration;
import com.sq.opc.component.OpcRegisterFactory;
import com.sq.opc.component.PointDataComparator;
import com.sq.opc.component.UtgardOpcHelper;
import com.sq.opc.domain.MesuringPoint;
import com.sq.opc.domain.OpcServerInfomation;
import com.sq.opc.domain.PointData;
import com.sq.opc.domain.SendMessage;
import com.sq.socket.SocketConsts;
import com.sq.socket.UdpSender;
import com.sq.socket.UdpSocketCfg;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIUnsignedInteger;
import org.jinterop.dcom.core.JIUnsignedShort;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;
import org.openscada.opc.lib.da.browser.Leaf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: shuiqing
 * Date: 2015/7/27
 * Time: 11:43
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Service
public class MesuringPointService {

    private static final Logger log = LoggerFactory.getLogger(MesuringPointService.class);

    public static Group group;

    public static Item[] itemArr = null;

    /**
     * 读取server下所有的ITEM
     * @param cid
     */
    public static Map<Item, ItemState> fetchReadSyncItems (final int cid) {
        long start = System.currentTimeMillis();
        Map<Item, ItemState> itemStateMap = new HashMap<Item, ItemState>();
        OpcServerInfomation opcServerInfomation = OpcRegisterFactory.fetchOpcInfo(cid);

        boolean flag = true;
        if ( !opcServerInfomation.isConnect() ||opcServerInfomation.getLeafs() == null) {
            flag = false;
            opcServerInfomation.setLeafs(null);
            List<MesuringPoint> mesuringPointList = OpcRegisterFactory.registerMesuringPoint(cid);
            log.error("mesuringPointList:----" + mesuringPointList.size());
            OpcRegisterFactory.registerConfigItems(cid, mesuringPointList);
        }
        Collection<Leaf> leafs = opcServerInfomation.getLeafs();
        log.error("leafs:----" + leafs.size());
        Server server = opcServerInfomation.getServer();
        try {
            if (!flag) {
                itemArr = new Item[leafs.size()];
                int item_flag = 0;
                group = server.addGroup();
                group.setActive(true);
                for(Leaf leaf:leafs){
                    Item item = null;
                    try {
                        item = group.addItem(leaf.getItemId());
                    } catch (AddFailedException e) {
                        log.error("Group add error.Error item is ：" + leaf.getItemId(),e);
                    }
                    item.setActive(true);
                    itemArr[item_flag] = item;
                    item_flag++;
                }
                log.error("itemArr:---" + itemArr.length);
                int i = 1;
                for (Item item:itemArr) {
                    log.error(item.getId());
                    i++;
                }
            }
            long start1 = System.currentTimeMillis();
            log.error("     1、拼装item[]用时：" + (start1 - start) + "ms");
            itemStateMap = group.read(true, itemArr);
            log.error("     2、group read 用时：" + (System.currentTimeMillis() - start1) + "ms");
        } catch (UnknownHostException e) {
            log.error("Host unknow error.",e);
        } catch (NotConnectedException e) {
            log.error("Connnect to opc error.",e);
            opcServerInfomation.setIsConnect(false);
            log.error(opcServerInfomation.getConnectionInformation().getHost() +opcServerInfomation.getConnectionInformation().getClsid()+ "  连接异常尝试重新连接 ");
        } catch (JIException e) {
            log.error("Opc server connect error.",e);
            opcServerInfomation.setIsConnect(false);
            log.error(opcServerInfomation.getConnectionInformation().getHost() + "  连接异常尝试重新连接 ");
        } catch (DuplicateGroupException e) {
            log.error("Group duplicate error.",e);
        }
        return itemStateMap;
    }

    /**
     * 各OPC系统数据同步
     */
    public static Map<Item, ItemState> syncOpcItemAllSystem () {
        Map<Item, ItemState> itemItemStateMap = new HashMap<Item, ItemState>();
        for (int i=1;i <= BaseConfiguration.CONFIG_CLIENT_MAX;i++) {
            itemItemStateMap.putAll(fetchReadSyncItems(i));
        }
        return itemItemStateMap;
    }

    /**
     * 构建消息数据包
     * @param syncItems
     */
    public static void buildDataPacket (Map<Item, ItemState> syncItems) {
        log.error("syncItems:----" + syncItems.size());
        List<PointData> msgDataList = new LinkedList<PointData>();
        for (Map.Entry<Item, ItemState> entry : syncItems.entrySet()) {
            String itemCode = entry.getKey().getId();
            String itemValue = entry.getValue().getValue().toString();
            try {
                JIVariant jiVariant = entry.getValue().getValue();
                if (jiVariant.getType() == 18) {
                    JIUnsignedShort jiUnsignedShort = (JIUnsignedShort)jiVariant.getObject();
                    log.error("&&&&&type-18  -->  " + jiUnsignedShort.getValue().toString());
                    itemValue = jiUnsignedShort.getValue().toString();
                } else if (jiVariant.getType() == 19) {
                    JIUnsignedInteger jiUnsignedInteger = (JIUnsignedInteger)jiVariant.getObject();
                    log.error("&&&&&type-19  -->  " + jiUnsignedInteger.getValue().toString());
                    itemValue = jiUnsignedInteger.getValue().toString();
                } else {
                    itemValue = jiVariant.getObject().toString();
                }
            } catch (JIException e) {
                e.printStackTrace();
            }

            if (itemValue.contains("org.jinterop.dcom.core.VariantBody$EMPTY")) {
                itemValue = "0";
            }
            MesuringPoint mesuringPoint = OpcRegisterFactory.fetchPointBySourceCode(itemCode);
            PointData pointData = new PointData();
            pointData.setIndex(mesuringPoint.getIndex());
            pointData.setItemCode(itemCode);
            pointData.setItemValue(itemValue);
            msgDataList.add(pointData);
        }
        Collections.sort(msgDataList, new PointDataComparator());
        int i = 1;
        for (PointData pointData:msgDataList) {
            if (i != Integer.parseInt(pointData.getIndex())) {
                log.error("missing index : " + i);
                i = Integer.parseInt(pointData.getIndex()) + 1;
            } else {
                i = i + 1;
            }
        }
        pointDataUnpackSend(msgDataList);
    }

    /**
     * 将获取的点数据进行拆包
     * @param msgDataList
     * @return
     */
    public static List<DatagramPacket> pointDataUnpackSend (List<PointData> msgDataList) {
        List<DatagramPacket> datagramPacketList = new ArrayList<DatagramPacket>();
        if (msgDataList.isEmpty() || msgDataList.size() == 0) {
            return null;
        }
        int counter = 0;
        for(int i = 0;
            i< msgDataList.size();
            i = i + UdpSocketCfg.DATAPACKET_SIZE) {

            List<PointData> subPointDataList = new LinkedList<PointData>();
            if (msgDataList.size() <= i+UdpSocketCfg.DATAPACKET_SIZE) {
                subPointDataList = msgDataList.subList(i,msgDataList.size());
            } else {
                subPointDataList = msgDataList.subList(i,i+UdpSocketCfg.DATAPACKET_SIZE);
            }
            counter++;
            List<PointData> packetDataList = new LinkedList<PointData>();
            for (PointData pointData:subPointDataList) {
                packetDataList.add(pointData);
            }


            System.out.println(counter + "   counter--------------------------------------------------");
            SendMessage sendMessage = new SendMessage();
            sendMessage.setStartPos(counter);
            sendMessage.setPointAmount(packetDataList.size());
            sendMessage.setData(packetDataList);

            String sendMsg = "";
            if (UdpSocketCfg.SENDER_FOR_SYSTEM == SocketConsts.SENDER_FOR_SYSTEM_INSIDE) {
                sendMsg = sendMessage.genSendMessageForInside();
            } else if (UdpSocketCfg.SENDER_FOR_SYSTEM == SocketConsts.SENDER_FOR_SYSTEM_LUCENT) {
                sendMsg = sendMessage.genSendMessageForLucent();
            }

            DatagramPacket datagramPacket = UdpSender.assemblyDatagramPacket(sendMsg);
            try {
                UdpSender.datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                log.error("IO 通讯异常.", e);
            }
            datagramPacketList.add(datagramPacket);
        }
        return datagramPacketList;
    }

    /**
     * 开启数据接收和发送服务
     */
    public static void startReceiveSenderService () {
        for (int i=1;i<=BaseConfiguration.CONFIG_CLIENT_MAX;i++) {
            UtgardOpcHelper.fetchClassDetails(i);
        }
        while (true) {
            try {
                Thread.sleep(1000l);//获取数据的间隔时间
                log.error("数据转发时间分布：");
                long start = System.currentTimeMillis();

                Map<Item, ItemState> itemItemStateMap = syncOpcItemAllSystem();
                long time1 = System.currentTimeMillis();
                log.error("     3、同步数据总耗时-- " + (time1 - start) + "ms");
                buildDataPacket(itemItemStateMap);
                long time2 = System.currentTimeMillis();
                log.error("     4、拼装和发送数据包总耗时-- " + (time2 - time1) + "ms");
                log.error("-------------------------------------------------");
                log.error("");
            } catch (InterruptedException e) {
                log.error("获取数据sleep出错.", e);
            }
        }
    }

    public static void main(String[] args) {
        MesuringPointService mesuringPointService = new MesuringPointService();

        BaseConfiguration.init();
        UdpSender.init();

        for (int i=1;i<=BaseConfiguration.CONFIG_CLIENT_MAX;i++) {
            int sysId = Integer.parseInt(OpcRegisterFactory.fetchOpcInfo(i).getSysId());
            UtgardOpcHelper.fetchClassDetails(sysId);
        }
        while (true) {
            try {
                Thread.sleep(1000l);//获取数据的间隔时间

                log.error("数据转发时间分布：");
                long start = System.currentTimeMillis();

                Map<Item, ItemState> itemItemStateMap = mesuringPointService.syncOpcItemAllSystem();
                long time1 = System.currentTimeMillis();
                log.error("     3、同步数据总耗时-- " + (time1 - start) + "ms");
                mesuringPointService.buildDataPacket(itemItemStateMap);
                long time2 = System.currentTimeMillis();
                log.error("     4、拼装和发送数据包总耗时-- " + (time2 - time1) + "ms");
                log.error("-------------------------------------------------");
                log.error("");
            } catch (InterruptedException e) {
                log.error("获取数据sleep出错.", e);
            }
        }
    }
}

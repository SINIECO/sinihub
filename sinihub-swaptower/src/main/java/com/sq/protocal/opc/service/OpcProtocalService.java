package com.sq.protocal.opc.service;

import com.sq.Constants;
import com.sq.OriginalItem;
import com.sq.inject.annotation.BaseComponent;
import com.sq.opc.utgard.DataSubscription;
import com.sq.opc.utgard.UtgardOpcServer;
import com.sq.protocal.common.component.RtDataCache;
import com.sq.protocal.common.domain.MesuringPoint;
import com.sq.protocal.common.domain.OriginalData;
import com.sq.protocal.common.repository.MesuringPointRepository;
import com.sq.protocal.common.repository.OriginalDataRepository;
import com.sq.protocal.common.service.AccessSystemService;
import com.sq.protocal.opc.component.OpcSubscriptionHolder;
import com.sq.protocal.opc.domain.OpcProtocal;
import com.sq.protocal.opc.repository.OpcProtocalRepository;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * opc 通信服务
 * User: shuiqing
 * Date: 15/12/15
 * Time: 下午5:29
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Service
public class OpcProtocalService extends BaseService<OpcProtocal, Long> {

    private static final Logger log = LoggerFactory.getLogger(OpcProtocalService.class);

    @BaseComponent
    @Autowired
    private OpcProtocalRepository opcProtocalRepository;

    @Autowired
    private MesuringPointRepository mesuringPointRepository;

    @Autowired
    private OriginalDataRepository originalDataRepository;

    @Autowired
    private OpcSubscriptionHolder opcSubscriptionHolder;

    @Autowired
    private AccessSystemService accessSystemService;

    @Autowired
    private RtDataCache rtDataCache;

    public List<OriginalItem> listOrignalItemByOpcProtocal(String sysCode) {
        List<OriginalItem> originalItemList = new ArrayList<OriginalItem>();
        OpcProtocal opcProtocal = accessSystemService.fetchOpcProtocalConfig(sysCode);
        if (null == opcProtocal) {
            log.error("listOrignalItemByOpcProtocal opcProtocal is null. sysCode ->>> " + sysCode);
            return null;
        }

        DataSubscription dataSubscription = opcSubscriptionHolder.fetchDataSubscription(sysCode);
        /** 如果订阅者服务的连接状态为READY，不需要再向remote opc server注册测点，直接获取实时数据即可 */
        if (dataSubscription.conn_status == Constants.OPC_CONNECT_STATUS_READY) {
            return dataSubscription.syncAccessItem();
        }

        List<MesuringPoint> mesuringPointList = mesuringPointRepository.listMesuringPointBySysCode(sysCode);
        if (mesuringPointList.isEmpty()) {
            log.error("listOrignalItemByOpcProtocal mesuringPointList is empty. sysCode ->>> " + sysCode);
            return null;
        }
        List<String> mpList = new ArrayList<>();
        for (MesuringPoint mp:mesuringPointList) {
            mpList.add(mp.getSourceCode());
        }

        UtgardOpcServer utgardOpcServer = dataSubscription.getUtgardOpcServer();
        if (null == utgardOpcServer || !dataSubscription.checkServerStatus()) {
            utgardOpcServer = new UtgardOpcServer
                    .Builder(opcProtocal.getHostIp()
                        ,opcProtocal.getUserName()
                        ,opcProtocal.getPassword()
                        ,opcProtocal.getDomain())
                    .clsid(opcProtocal.getClsid())
                    .build();
            dataSubscription.setUtgardOpcServer(utgardOpcServer);
            dataSubscription.registerGroupAndItems(mpList);
        }

        return dataSubscription.syncAccessItem();
    }

    /**
     * 接收通信传过来的数据并保存到mysql服务中
     * @param sysCode  系统编码
     * @param originalItemList 通信原始数据集合
     */
    public void receiveDataInMysql (String sysCode, List<OriginalItem> originalItemList) {
        List<OriginalData> originalDataList = new LinkedList<OriginalData>();
        for (OriginalItem originalItem:originalItemList) {
            OriginalData originalData = new OriginalData();
            originalData.setInstanceTime(originalItem.getInstanceTime());
            originalData.setItemCode(originalItem.getItemCode());
            originalData.setItemValue(originalItem.getItemValue());
            originalData.setSysCode(sysCode);
            originalDataList.add(originalData);
        }
        originalDataRepository.save(originalDataList);
    }

    /**
     * 更新通信原始数据缓存
     * @param originalItemList 通信传过来的原始数据
     */
    public void updateOrignalItemDataCache(List<OriginalItem> originalItemList) {
        for (OriginalItem originalItem:originalItemList) {
            rtDataCache.originalItemMap.put(originalItem.getItemCode(),originalItem.getItemValue());
        }
    }
}

package com.sq.proto.common.service;

import com.sq.OriginalItem;
import com.sq.inject.annotation.BaseComponent;
import com.sq.proto.common.domain.AccessSystem;
import com.sq.proto.common.domain.OriginalData;
import com.sq.proto.common.domain.ProtocalConsts;
import com.sq.proto.common.domain.MesuringPoint;
import com.sq.proto.common.repository.MesuringPointRepository;
import com.sq.proto.common.repository.OriginalDataRepository;
import com.sq.proto.opc.service.OpcProtocalService;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * 测点相关业务类.
 * User: shuiqing
 * Date: 2015/4/1
 * Time: 15:01
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Service
public class MesuringPointService extends BaseService<MesuringPoint, Long> {

    private static final Logger log = LoggerFactory.getLogger(MesuringPointService.class);

    @Autowired
    @BaseComponent
    private MesuringPointRepository mesuringPointRepository;

    @Autowired
    private OriginalDataRepository originalDataRepository;

    @Autowired
    private AccessSystemService accessSystemService;

    @Autowired
    private OpcProtocalService opcProtocalService;

    /**
     * 同步子系统的数据
     * @param sysCode 子系统编码
     * @return 同步的结果集
     */
    public void syncRemoteSystemData (String sysCode) {
        AccessSystem accessSystem = accessSystemService.fetchAccessSystemConfig(sysCode);
        if (null == accessSystem) {
            log.error("syncRemoteSystemData accessSystem si null ---- sysCode ->>" + sysCode);
            return;
        }

        List<OriginalItem> originalItemList = new ArrayList<OriginalItem>();
        switch (accessSystem.getProtocalType()) {
            case ProtocalConsts.PROTOCAL_TYPE_OPC:
                originalItemList = opcProtocalService.listOrignalItemByOpcProtocal(sysCode);
                break;
        }

        receiveDataInMysql(sysCode, originalItemList);
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
            originalData.setInstanceTime(Calendar.getInstance());
            originalData.setItemCode(originalItem.getItemCode());
            originalData.setItemValue(originalItem.getItemValue());
            originalData.setSysCode(sysCode);
            originalDataList.add(originalData);
        }
        originalDataRepository.save(originalDataList);
    }

}
package com.sq.proto.common.service;

import com.sq.OriginalItem;
import com.sq.inject.annotation.BaseComponent;
import com.sq.proto.common.domain.AccessSystem;
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
    public List<OriginalItem> syncRemoteSystemData (String sysCode) {
        AccessSystem accessSystem = accessSystemService.fetchAccessSystemConfig(sysCode);
        if (null == accessSystem) {
            log.error("syncRemoteSystemData accessSystem si null ---- sysCode ->>" + sysCode);
            return null;
        }

        List<OriginalItem> originalItemList = new ArrayList<OriginalItem>();
        switch (accessSystem.getProtocalType()) {
            case ProtocalConsts.PROTOCAL_TYPE_OPC:
                originalItemList = opcProtocalService.listOrignalItemByOpcProtocal(sysCode);
        }

        return originalItemList;
    }

}
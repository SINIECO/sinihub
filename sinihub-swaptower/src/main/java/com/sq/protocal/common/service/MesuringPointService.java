package com.sq.protocal.common.service;

import com.sq.OriginalItem;
import com.sq.inject.annotation.BaseComponent;
import com.sq.protocal.common.domain.AccessSystem;
import com.sq.protocal.common.domain.MesuringPoint;
import com.sq.protocal.common.domain.ProtocalConsts;
import com.sq.protocal.common.repository.MesuringPointRepository;
import com.sq.protocal.common.repository.OriginalDataRepository;
import com.sq.protocal.jdbc.service.JdbcProtocalService;
import com.sq.protocal.opc.service.OpcProtocalService;
import com.sq.protocal.socket.service.UdpProtocalService;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UdpProtocalService udpProtocalService;

    @Autowired
    private JdbcProtocalService jdbcProtocalService;

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

        switch (accessSystem.getProtocalType()) {
            case ProtocalConsts.PROTOCAL_TYPE_OPC:
                List<OriginalItem> originalItemList = opcProtocalService.listOrignalItemByOpcProtocal(sysCode);

                /** 缓存通信的实时数据 */
                opcProtocalService.updateOrignalItemDataCache(originalItemList);

                /** 将通信接收的数据存入数据库 */
                opcProtocalService.receiveDataInMysql(sysCode, originalItemList);
                break;
            case ProtocalConsts.PROTOCAL_TYPE_UDP:
                udpProtocalService.startUdpDataReceiveService(sysCode);
                break;
            case ProtocalConsts.PROTOCAL_TYPE_JDBC:
                jdbcProtocalService.syncTradeRealTimeData(sysCode);
        }
    }

}
package com.sq.protocal.common.service;

import com.sq.inject.annotation.BaseComponent;
import com.sq.loadometer.service.TradeDataService;
import com.sq.protocal.common.domain.AccessSystem;
import com.sq.protocal.common.domain.ProtocalConsts;
import com.sq.protocal.jdbc.domain.JdbcProtocal;
import com.sq.protocal.socket.domain.UdpProtocal;
import com.sq.protocal.common.repository.AccessSystemRepository;
import com.sq.protocal.jdbc.repository.JdbcProtocalRepository;
import com.sq.protocal.opc.repository.OpcProtocalRepository;
import com.sq.protocal.socket.repository.UdpProtocalRepository;
import com.sq.protocal.opc.domain.OpcProtocal;
import com.sq.quota.domain.QuotaTemp;
import com.sq.quota.repository.QuotaTempRepository;
import com.sq.quota.service.QuotaComputInsService;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * 接入系统服务类
 * User: shuiqing
 * Date: 15/12/14
 * Time: 下午3:49
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Service
public class AccessSystemService extends BaseService<AccessSystem, Long> {

    private static final Logger log = LoggerFactory.getLogger(MesuringPointService.class);

    @BaseComponent
    @Autowired
    private AccessSystemRepository accessSystemRepository;

    @Autowired
    private OpcProtocalRepository opcProtocalRepository;

    @Autowired
    private UdpProtocalRepository udpProtocalRepository;

    @Autowired
    private JdbcProtocalRepository jdbcProtocalRepository;

    @Autowired
    private QuotaTempRepository quotaTempRepository;

    @Autowired
    private QuotaComputInsService quotaComputInsService;

    @Autowired
    private TradeDataService tradeDataService;

    /**
     * 获取子系统基本信息
     * @param sysCode 子系统编码
     * @return 子系统基本信息
     */
    public AccessSystem fetchAccessSystemConfig(String sysCode) {
        return accessSystemRepository.findAccessSystemBySysCode(sysCode);
    }

    /**
     * 获取opc通信的配置信息
     * @param sysCode 子系统编码
     * @return opc配置信息
     */
    public OpcProtocal fetchOpcProtocalConfig(String sysCode) {
        return opcProtocalRepository.findOpcProtocalBySysCode(sysCode);
    }

    /**
     * 获取jdbc通信的配置信息
     * @param sysCode 子系统编码
     * @return jdbc通信的配置信息
     */
    public JdbcProtocal fetchJdbcProtocalConfig(String sysCode) {
        return jdbcProtocalRepository.findJdbcProtocalBySysCode(sysCode);
    }

    /**
     * 获取udp通信的配置信息
     * @param sysCode 子系统编码
     * @return udp通信的配置信息
     */
    public UdpProtocal fetchUdpProtocalConfig(String sysCode) {
        return udpProtocalRepository.findUdpProtocalBySysCode(sysCode);
    }


    /***
     * 接口数据汇集
     * @param computCal     计算时间
     * @param sysCode       子系统编码
     * @param protocalType  协议类型
     */
    public void interfaceDataGather(Calendar computCal, String sysCode, int protocalType) {
        switch(protocalType) {
            case ProtocalConsts.PROTOCAL_TYPE_JDBC:
                tradeDataGather(computCal);
                break;
            default:
                realTimeDataGather(computCal,sysCode);
        }
    }

    /***
     * 接口实时数据汇集
     * @param computCal  计算时间
     * @param sysCode    子系统编码
     */
    private void realTimeDataGather(Calendar computCal, String sysCode) {
        List<QuotaTemp> quotaTempList = quotaTempRepository.listQuotaTempByMpsyscode(sysCode);

        /** 实时数据小时数据汇集 */
        quotaComputInsService.interfaceDataGather(computCal, quotaTempList);

        /** 实时数据日数据汇集 */
        quotaComputInsService.interfaceIndicatorDataGater(computCal, quotaTempList);
    }

    /***
     * 同步地磅的实时数据
     * @param computCal   计算时间
     * @param sysCode     子系统编码
     */
    private void tradeDataGather(Calendar computCal) {
        tradeDataService.generateLoadometerIndicator(computCal);
    }
}

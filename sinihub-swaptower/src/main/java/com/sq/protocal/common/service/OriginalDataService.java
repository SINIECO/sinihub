package com.sq.protocal.common.service;

import com.sq.inject.annotation.BaseComponent;
import com.sq.protocal.common.domain.OriginalData;
import com.sq.protocal.common.repository.OriginalDataRepository;
import com.sq.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: shuiqing
 * Date: 2015/4/15
 * Time: 16:30
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Service
public class OriginalDataService extends BaseService<OriginalData, Long> {

    private static final Logger log = LoggerFactory.getLogger(MesuringPointService.class);

    @Autowired
    @BaseComponent
    private OriginalDataRepository originalDataRepository;

    /**
     * opc 接口数据迁移
     * @param calculateDay
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public void opcDataMigration(String calculateDay) {
        originalDataRepository.dcsDataMigration(calculateDay);
    }

    /**
     * 大屏数据更新
     */
    @Transactional(propagation= Propagation.REQUIRED)
    public void njmbDataSync() {
        originalDataRepository.njmbDataSync();
    }
}

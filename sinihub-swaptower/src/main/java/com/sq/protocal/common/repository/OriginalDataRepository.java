package com.sq.protocal.common.repository;

import com.sq.protocal.common.domain.OriginalData;
import com.sq.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.List;

/**
 * 测点同步结果仓库.
 * User: shuiqing
 * Date: 15/12/14
 * Time: 下午2:57
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public interface OriginalDataRepository extends BaseRepository<OriginalData, Long> {

    /** DCS原始测点实时数据迁移 */
    void dcsDataMigration(String calculateDay);

    /** 大屏数据更新同步 */
    void njmbDataSync();

    /** 获取下一数据获取数据批次 */
    @Query("select 1 + IFNULL(max(batchNum),0) from OriginalData where sysId = ?1")
    Long gernateNextBatchNumber(int sysId);

    /** 查询指标测点指定时间内的数据 */
    List<OriginalData> listAnHourPreOriginalData(String tableName, String indiCode, Long subMin, Calendar computCal);

    /** 获取指定时间之前的最近的一条记录 */
    List<OriginalData> fetchFrontOriginalDataByCal(String itemCode, Calendar calendar);

    /** 获取指定时间之后的最近的一条记录 */
    List<OriginalData> fetchBehindOriginalDataByCal(String itemCode, Calendar calendar);

    /** 根据测点编码集合获取实时数据 */
    List<OriginalData> fetchOriDataByCodeList(String sysCode, List<String> itemCodeList);
}

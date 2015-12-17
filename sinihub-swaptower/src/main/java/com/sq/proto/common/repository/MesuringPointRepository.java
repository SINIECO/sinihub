package com.sq.proto.common.repository;

import com.sq.proto.common.domain.MesuringPoint;
import com.sq.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 测点仓库.
 * User: shuiqing
 * Date: 2015/4/1
 * Time: 11:33
 * Email: shuiqing301@gmail.com
 * _
 * |_)._ _
 * | o| (_
 */
public interface MesuringPointRepository extends BaseRepository<MesuringPoint, Long> {

    /** 根据编码查询测点 */
    @Query("select m from MesuringPoint m where sourceCode = ?1")
    MesuringPoint fetchMpByCode(String postCode);

    /** 根据系统编码查询测点集 */
    @Query("select m from MesuringPoint m where sysCode = ?1")
    List<MesuringPoint> listMesuringPointBySysCode(String sysCode);
}

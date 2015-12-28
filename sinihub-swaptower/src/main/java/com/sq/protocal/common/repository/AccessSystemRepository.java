package com.sq.protocal.common.repository;

import com.sq.protocal.common.domain.AccessSystem;
import com.sq.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 接入系统仓库
 * User: shuiqing
 * Date: 15/12/14
 * Time: 下午3:28
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public interface AccessSystemRepository extends BaseRepository<AccessSystem, Long> {

    /** 根据系统编码查找AccessSystem子系统基本信息配置记录 */
    @Query("select m from AccessSystem m where sysCode = ?1 ")
    AccessSystem findAccessSystemBySysCode(String sysCode);
}

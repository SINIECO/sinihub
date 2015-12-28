package com.sq.protocal.jdbc.repository;

import com.sq.protocal.jdbc.domain.JdbcProtocal;
import com.sq.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * JDBC通信仓库
 * User: shuiqing
 * Date: 15/12/14
 * Time: 下午3:58
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public interface JdbcProtocalRepository extends BaseRepository<JdbcProtocal, Long> {

    /** 根据系统编码查找opc通讯配置记录 */
    @Query("select m from JdbcProtocal m where sysCode = ?1 ")
    JdbcProtocal findJdbcProtocalBySysCode(String sysCode);
}

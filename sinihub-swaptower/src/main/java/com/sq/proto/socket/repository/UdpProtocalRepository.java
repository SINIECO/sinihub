package com.sq.proto.socket.repository;

import com.sq.proto.socket.domain.UdpProtocal;
import com.sq.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * UDP通信仓库
 * User: shuiqing
 * Date: 15/12/14
 * Time: 下午3:56
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public interface UdpProtocalRepository extends BaseRepository<UdpProtocal, Long> {

    /** 根据系统编码查找opc通讯配置记录 */
    @Query("select m from UdpProtocal m where sysCode = ?1 ")
    UdpProtocal findUdpProtocalBySysCode(String sysCode);
}

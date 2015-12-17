package com.sq.proto.common.domain;

import com.sq.entity.AbstractEntity;
import com.sq.protocol.opc.domain.MeaType;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实时测点.
 * User: shuiqing
 * Date: 2015/3/30
 * Time: 13:57
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Entity
@Table(name = "T_MesuringPoint")
public class MesuringPoint extends AbstractEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false, precision=10)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 源code，DCS或者PLC系统上测点的编码
     */
    @NotBlank
    private String sourceCode;

    /**
     * 目标code, 接口对接方编码
     */
    @NotBlank
    private String targetCode;

    /**
     * 测点名称
     */
    @NotBlank
    private String pointName;

    /**
     * 系统编码
     */
    @NotBlank
    private String sysCode;

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public MesuringPoint() {
    }

}

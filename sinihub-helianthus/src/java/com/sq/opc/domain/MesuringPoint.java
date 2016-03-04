package com.sq.opc.domain;

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
public class MesuringPoint implements Serializable {

    /**
     * 测点索引
     */
    private String index;

    /**
     * 源code，DCS或者PLC系统上测点的编码
     */
    private String sourceCode;

    /**
     * 目标code, 接口对接方编码
     */
    private String targetCode;

    /**
     * 测点名称
     */
    private String pointName;

    /**
     * 测点数据类型
     */
    private OpcDataType dataType;

    /**
     * 系统编号
     */
    private int sysId;

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

    public MesuringPoint() {
    }

    public OpcDataType getDataType() {
        return dataType;
    }

    public void setDataType(OpcDataType dataType) {
        this.dataType = dataType;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    @Override
    public String toString() {
        return "index:" + this.getIndex() + ",sourceCode:" + this.getSourceCode()
                + ",targetCode:" + this.getTargetCode() + ",pointName:" + this.getPointName();
    }
}

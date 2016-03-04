package com.sq.opc.domain;

/**
 * OPC点数据，根据测点表得出点对象.
 * User: shuiqing
 * Date: 2015/7/30
 * Time: 17:22
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class PointData {

    private String index;

    private String itemCode;

    private String itemValue;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    @Override
    public String toString() {
        return "index:" + this.index + ";itemCode:" + this.itemCode;
    }
}

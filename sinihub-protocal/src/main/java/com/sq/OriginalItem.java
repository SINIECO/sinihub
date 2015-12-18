package com.sq;

import java.io.Serializable;
import java.util.Calendar;

/**
 * opc通讯获取的点数据
 *      为了适应UTGARD和jEasyOpc两种opc数据获取方式
 * User: shuiqing
 * Date: 15/12/10
 * Time: 下午5:12
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class OriginalItem implements Serializable {

    private static final long serialVersionUID = 4700989832005062847L;

    private String itemCode;

    private String itemValue;

    private Calendar instanceTime;

    private int valueType;

    public Calendar getInstanceTime() {
        return instanceTime;
    }

    public void setInstanceTime(Calendar instanceTime) {
        this.instanceTime = instanceTime;
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

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }
}

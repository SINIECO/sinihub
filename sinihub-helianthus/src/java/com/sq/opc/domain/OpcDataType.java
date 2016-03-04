package com.sq.opc.domain;

/**
 * OPC数据类型，主要区分开关量和模拟量.
 * User: shuiqing
 * Date: 2015/7/16
 * Time: 10:31
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public enum OpcDataType {

    /** 测点类型-开关量 */
    DATATYPE_DI(0),

    /** 测点类型-模拟量 */
    DATATYPE_AI(1);

    /**
     * 下标值。
     */
    private int index;

    /**
     * 使用下标构造枚举。
     * 构造函数
     * @param index 用于构造枚举的下标。
     */
    private OpcDataType(int index) {
        this.index = index;
    }

    /**
     * 返回当前枚举的下标。
     * @return 返回本枚举的下标值
     */
    public int index() {
        return index;
    }

    public static OpcDataType indexOf(int index) {
        for (OpcDataType item : OpcDataType.values()) {
            if (item.index == index) {
                return item;
            }
        }

        throw new com.sq.core.exception.UnsupportedValueException("枚举类型 AttachType 不支持下标值 " + index);
    }
}

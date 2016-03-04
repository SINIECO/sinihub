package com.sq.core.Enum;

import com.sq.core.exception.UnsupportedValueException;

/**
 * Created with IntelliJ IDEA.
 * User: shuiqing
 * Date: 2015/7/17
 * Time: 10:38
 * Email: shuiqing301@gmail.com
 * _
 * |_)._ _
 * | o| (_
 */
public enum DataType {

    /**
     * 整数类型
     */
    Integer(0),

    /**
     * 单精度浮点数类型
     */
    Float(1),
    /**
     * 双精度浮点数类型
     */
    Double(2),
    /**
     * 时间类型，格式为 yyyy-MM-dd HH:mm:ss
     */
    DateTime(3),

    /**
     * 字符串类型
     */
    String(4),

    /**
     * 布尔类型，支持 true|false，是|否，
     */
    Boolean(5);

    private int value;

    private DataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static DataType valueOf(int value) {
        for (DataType item : DataType.values()) {
            if (item.value == value) {
                return item;
            }
        }
        throw new UnsupportedValueException("枚举类型DataType不支持整型值 " + value);
    }

    public static DataType nameOf(String name) {
        for (DataType item : DataType.values()) {
            if (item.toString().equals(name)) {
                return item;
            }
        }
        throw new UnsupportedValueException("枚举类型DataType不支持字面值 " + name);
    }
}

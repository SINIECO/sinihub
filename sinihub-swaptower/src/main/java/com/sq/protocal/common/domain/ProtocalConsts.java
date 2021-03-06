package com.sq.protocal.common.domain;

/**
 * 通讯相关的常量
 * User: shuiqing
 * Date: 15/12/10
 * Time: 下午2:29
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public interface ProtocalConsts {

    /** ======================
     *    通讯协议类型
     *      1、OPC
     *      2、MODBUS
     *      3、UDP
     *      4、JDBC
     *      5、WS
     */
    public static final int PROTOCAL_TYPE_OPC = 1;

    public static final int PROTOCAL_TYPE_MODBUS = 2;

    public static final int PROTOCAL_TYPE_UDP = 3;

    public static final int PROTOCAL_TYPE_JDBC = 4;

    public static final int PROTOCAL_TYPE_WS = 5;

    /** ======================
     *    OPC通讯协驱动类型
     *      1、utgard
     *      2、jEasyOPC
     */
    public static final int OPC_DRIVER_UTGARD = 1;

    public static final int OPC_DRIVER_JEASYOPC = 2;



}

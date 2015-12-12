package com.sq;

/**
 * 常量
 * User: shuiqing
 * Date: 15/12/10
 * Time: 下午5:28
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public interface Constants {

    /** ======================
     *    OPC服务连接状态
     *      1、未连接opc服务
     *      2、已连接服务，但未添加group和item的内容
     *      3、服务正常，可以通信获取相应测点数据
     */
    public static final int OPC_CONNECT_STATUS_DISCONN = 1;

    public static final int OPC_CONNECT_STATUS_MISGROUPANDITEM = 2;

    public static final int OPC_CONNECT_STATUS_READY = 3;

    /** ======================
     *    OPC数据类型
     *      1、数值
     *      2、布尔值
     *      3、字符串
     */
    public static final int OPC_VALUETYPE_NUMBER = 1;

    public static final int OPC_VALUETYPE_BOOLEAN = 2;

    public static final int OPC_VALUETYPE_STRING = 3;

    /** ======================
     *    OPC数据质量
     *      1、GOOD
     *      2、BAD
     */
    public static final int OPC_QUALITY_GOOD = 1;

    public static final int OPC_QUALITY_BAD = 2;

    /** ======================
     *    OPC item通信验证状态码
     *      1、成功
     *      2、失败：通信异常
     *      3、测点名错误
     */
    public static final int OPC_ITEM_VALID_SUCCESS = 1;

    public static final int OPC_ITEM_VALID_DISCONN = 2;

    public static final int OPC_ITEM_VALID_ITEMERROR = 3;


}

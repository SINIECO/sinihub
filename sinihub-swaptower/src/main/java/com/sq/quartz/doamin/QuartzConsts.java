package com.sq.quartz.doamin;

/**
 * quartz相关的常量
 * User: shuiqing
 * Date: 15/12/22
 * Time: 下午4:11
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public interface QuartzConsts {

    /** ======================
 *      任务当前状态
     *      1、运行中
     *      2、不在运行中
     */
    public static final String STATUS_RUNNING = "1";

    public static final String STATUS_NOT_RUNNING = "2";

    /** ======================
     *  任务是否并发
     *      1、并发
     *      2、不并发
     */
    public static final boolean CONCURRENT_IS = true;

    public static final boolean CONCURRENT_NOT = false;
}

package com.sq.db.domain;

/**
 * Created by ywj on 2015/12/22.
 */
public interface Constant {

     //checkAllCheckItem->检查项目点的通讯状态出错
     public final static String CHECK_COMM_EXCEPTION = "checkAllCheckItem->检查项目点的通讯状态出错";

     //checkAllCheckItem->检查项目点的网络状态出错
     public final static String CHECK_NET_EXCEPTION = "checkAllNetCheckItem->检查项目点的网络连通状态出错";

     //checkAllCheckItem->检查项目点的地磅状态出错
     public final static String CHECK_TRADE_EXCEPTION = "checkAllCheckItemTrade->检查项目点的地磅状态出错";

     //检测时间常量为20分钟
     public final static int CHECK_TIME = 20;

     //代表未超过20分钟类型1
     public final static int PROBLEM_TYPE01 = 1;

     //代表未超过20分钟类型1描述
     public final static String PROBLEM_TYPE01_DIS = "监测正常";

     //代表超过20分钟抛出的异常类型2
     public final static int PROBLEM_TYPE02 = 2;

     //代表超过20分钟抛出的异常类型2描述
     public final static String PROBLEM_TYPE02_DIS = "通信异常";

     //代表网络异常抛出的异常类型3
     public final static int PROBLEM_TYPE03 = 3;

     //代表端口异常抛出的异常类型5
     public final static int PROBLEM_TYPE05 = 5;

     //代表网络异常抛出的异常类型5描述
     public final static String PROBLEM_TYPE05_DIS = "端口异常";

     //代表网络异常抛出的异常类型3描述
     public final static String PROBLEM_TYPE03_DIS = "网络异常";

     //代表地磅数据异常抛出的异常类型3
     public final static int PROBLEM_TYPE04 = 4;

     //代表地磅数据异常抛出的异常类型4描述
     public final static String PROBLEM_TYPE04_DIS = "地磅数据异常";

     //网络连通时间
     public final static int TIME_OUT = 5000;

     // 端口号设置为 0 表示在本地挑选一个可用端口进行连接
     public final static int DE_PORT = 0;

     //代表正常数据不持有任何状态
     public final static int SYNCS_TATUS01 = 1;

     //代表异常数据处理状态为未处理
     public final static int SYNCS_TATUS02 = 2;

     //代表 "->"
     public final static String SLASH = "->";

     public final static String LOG_ERROR01 = "连接邮箱工具失败";

     // 邮件发送成功
     public final static String SEND_STATU01 = "send_success";

     // 邮件发送失败
     public final static String SEND_STATU02 = "send_fail";

     //邮箱属性文件加载失败
     public final static String LOAD_EMAIL_PROP_FAIL = "加载邮箱属性文件失败";

     //socket关闭异常
     public final static String CLOSE_SOCKET_EXCEPTION = "Error occurred while closing socket..";

     public final static String FAIL_CONNECTION = "网络连接失败";

     public final static String GET_LOCALHOSP_FAIL="获取本地address失败";

     //地磅数据正常
     public final static String GOOD_CHECK_TRADE = "地磅监测新数据正常";

     //0次
     public final static int SECOND_TIME_OZEO = 0;

     //5次
     public final static int SECOND_TIME_FIVE = 5;

     //10次
     public final static int SECOND_TIME_TEN = 10;

     //代表邮箱状态为正常0
     public final static int EMAIL_STATUS_ONE = 0;

     //代表邮箱状态为不正常1
     public final static int EMAIL_STATUS_TWO = 1;

     //"失败次数为->"
     public final static String FAIL_TIME = "失败次数为->";

}

package com.sq.db.domain;

/**
 * Created by ywj on 2015/12/22.
 */
public interface Constant {

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

     //代表超过20分钟抛出的异常类型3
     public final static int PROBLEM_TYPE03 = 3;

     //代表超过20分钟抛出的异常类型3描述
     public final static String PROBLEM_TYPE03_DIS = "网络异常";

     //代表正常数据不持有任何状态
     public final static int SYNCS_TATUS01 = 1;

     //代表异常数据处理状态为未处理
     public final static int SYNCS_TATUS02 = 2;

     //判断队列信息
     public final static int QUEUE_SIZE = 0;

     //代表 "->"
     public final static String SLASH = "->";

     public final static String LOG_ERROR01 = "连接邮箱工具失败";

     // 邮件发送成功
     public final static String SEND_STATU01 = "send_success";

     // 邮件发送失败
     public final static String SEND_STATU02 = "send_fail";

     //邮箱属性文件加载失败
     public final static String LOAD_EMAIL_PROP_FAIL = "加载邮箱属性文件失败";

}

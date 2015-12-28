package com.sq.protocal.opc.component;

import com.sq.opc.utgard.DataSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * opc server holder.
 * User: shuiqing
 * Date: 15/12/15
 * Time: 下午5:39
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Component
public class OpcSubscriptionHolder {

    private static final Logger log = LoggerFactory.getLogger(OpcSubscriptionHolder.class);

    /** cache DataSubscription. */
    private HashMap<String, DataSubscription> dataSubscriptionHashMap = new HashMap<String, DataSubscription>();

    /** 缓存DataSubscription对象 */
    public void cacheDataSubscription(String sysCode, DataSubscription dataSubscription) {
        dataSubscriptionHashMap.put(sysCode, dataSubscription);
    }

    /** 获取持有的data subscription对象 */
    public DataSubscription fetchDataSubscription(String sysCode) {
        return dataSubscriptionHashMap.get(sysCode);
    }
}

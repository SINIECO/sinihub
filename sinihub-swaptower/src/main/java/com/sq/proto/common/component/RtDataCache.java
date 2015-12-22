package com.sq.proto.common.component;

import com.sq.proto.common.domain.MesuringPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 实时数据相关的缓存
 * User: shuiqing
 * Date: 15/12/18
 * Time: 上午11:27
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Component
public class RtDataCache {

    private static final Logger log = LoggerFactory.getLogger(RtDataCache.class);

    /** 测点对象的缓存 */
    public HashMap<String, MesuringPoint> mesuringPointHashMap = new HashMap<String, MesuringPoint>();

    /** 实时数据缓存 */
    public HashMap<String,String> originalItemMap = new HashMap<String,String>();
}

package com.sq.opc.component;

import com.sq.opc.domain.PointData;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: shuiqing
 * Date: 2015/7/30
 * Time: 17:33
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class PointDataComparator implements Comparator<PointData> {

    @Override
    public int compare(PointData o1, PointData o2) {
        return Integer.parseInt(o1.getIndex()) - Integer.parseInt(o2.getIndex());
    }
}

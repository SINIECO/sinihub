package com.sq.opc.domain;

import java.util.List;

/**
 * 发送的数据体.
 * User: shuiqing
 * Date: 2015/7/30
 * Time: 16:17
 * Email: shuiqing301@gmail.com
 * GitHub: https://github.com/ShuiQing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public class SendMessage {

    public String head = "FFFFFFFF";

    public long startPos;

    public long pointAmount;

    public List<PointData> pointDatas;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public long getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(long pointAmount) {
        this.pointAmount = pointAmount;
    }

    public List<PointData> getData() {
        return pointDatas;
    }

    public void setData(List<PointData> pointDatas) {
        this.pointDatas = pointDatas;
    }

    public String genSendMessageForLucent () {
        StringBuilder sb = new StringBuilder();
        sb.append(this.head)
                .append(";")
                .append(this.startPos)
                .append(";")
                .append(this.pointAmount)
                .append(";");
        for (PointData pointData:pointDatas) {
            String appendStr = "";
            if (pointData.getItemValue().equals("true")) {
                appendStr = "1";
            } else if (pointData.getItemValue().equals("false")){
                appendStr = "0";
            } else {
                appendStr = pointData.getItemValue();
            }
            sb.append(appendStr).append(";");
        }
        return sb.toString();
    }

    public String genSendMessageForInside () {
        StringBuilder sb = new StringBuilder();
        for (PointData pointData:pointDatas) {
            sb.append(pointData.getItemCode()).append("%%");
            if (pointData.getItemValue().equals("true")) {
                sb.append("1");
            } else if (pointData.getItemValue().equals("false")){
                sb.append("0");
            } else {
                sb.append(pointData.getItemValue());
            }
            sb.append(";");
        }
        return sb.toString();
    }
}

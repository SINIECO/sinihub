package com.sq.opc;

/**
 * opc server 的抽象基础server
 * User: shuiqing
 * Date: 15/12/15
 * Time: 下午5:42
 * Email: annuus.sq@gmail.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
public abstract class OpcBaseServer {

    public String sysCode;

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }
}

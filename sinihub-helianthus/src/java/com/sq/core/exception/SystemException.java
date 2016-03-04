package com.sq.core.exception;

/**
 * 系统级别的错误。这类错误往往可以预料，但是无法处理。比如，运行中网络出错导致系统运行异常。这类错误往往
 * 不需要修改程序，但是需要重启服务器来解决。
 */
public class SystemException extends GenericException {

    private static final long serialVersionUID = 6012143363627561642L;

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.sq.core.exception;

/**
 * 非法参数异常。当在方法中传递的参数不符合逻辑的时候使用。
 */
public class InvalidArgumentException extends GenericException {

    private static final long serialVersionUID = -5557113711606150399L;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}

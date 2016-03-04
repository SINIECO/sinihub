package com.sq.core.exception;

/**
 * 调用了尚未提供支持的方法时抛出。
 */
public class UnsupportedValueException extends GenericException {

    private static final long serialVersionUID = 4332427707224006982L;

    public UnsupportedValueException() {
        super();
    }

    public UnsupportedValueException(String message) {
        super(message);
    }

    public UnsupportedValueException(Throwable cause) {
        super(cause);
    }

    public UnsupportedValueException(String message, Throwable cause) {
        super(message, cause);
    }

}

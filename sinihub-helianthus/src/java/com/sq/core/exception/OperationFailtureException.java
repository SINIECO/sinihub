package com.sq.core.exception;

/**
 * 操作失败异常
 */
public class OperationFailtureException extends GenericException {

    private static final long serialVersionUID = -7278401313989518989L;

    public OperationFailtureException() {
        super();
    }

    /**
     * @param message
     */
    public OperationFailtureException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public OperationFailtureException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public OperationFailtureException(String message, Throwable cause) {
        super(message, cause);
    }

}

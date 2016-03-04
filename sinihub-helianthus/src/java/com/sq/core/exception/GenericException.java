package com.sq.core.exception;

/**
 * 所有程序可预料异常的基类。这个异常可以在controller层作为能被捕获从而获取向最终用户报错信息的异常。
 */
public class GenericException extends RuntimeException {

    private static final long serialVersionUID = -1209197549926518547L;

    public GenericException() {
		super();
	}

	/**
	 * @param message
	 */
	public GenericException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public GenericException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GenericException(String message, Throwable cause) {
		super(message, cause);
	}

}

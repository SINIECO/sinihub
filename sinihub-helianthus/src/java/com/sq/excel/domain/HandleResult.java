package com.sq.excel.domain;

import java.io.Serializable;

/**
 * 单条数据处理结果
 * @author ShuiQing PM
 * @Email shuiqing301@gmail.com	
 * 2014年8月19日 下午10:33:25
 */
public class HandleResult implements Serializable {
	
    private static final long serialVersionUID = 5230898625652217786L;

	/**
	 * 默认构造函数，什么结果页没有
	 */
	public HandleResult() {
		this(false, null);
    }

	/**
	 * 默认构造函数.
	 */
	public HandleResult(boolean success, String message) {
		this.success = success;
		this.message = message;
    }

	/**
	 * 处理是否成功
	 */
	private boolean success;
	
	/**
	 * 处理实体后返回的信息
	 */
	private String  message;
	
	private Throwable throwable;

	/**
     * @return the success
     */
    public boolean isSuccess() {
    	return this.success;
    }

	/**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
    	this.success = success;
    }

	/**
     * @return the message
     */
    public String getMessage() {
    	return this.message;
    }

	/**
     * @param message the message to set
     */
    public void setMessage(String message) {
    	this.message = message;
    }

	/**
     * @return the throwable
     */
    public Throwable getThrowable() {
    	return this.throwable;
    }

	/**
     * @param throwable the throwable to set
     */
    public void setThrowable(Throwable throwable) {
    	this.throwable = throwable;
    }
}

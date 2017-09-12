package com.taunton.exception;

/**
 * 登录注册异常类
 * 
 * @author Administrator
 * 
 */
public class CheckException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 无参的构造方法
	 */
	public CheckException() {

	}

	/**
	 * 带参的构造方法，传入异常信息
	 * 
	 * @param msg
	 */
	public CheckException(String msg) {
		super(msg);
	}
}

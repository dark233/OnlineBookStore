package com.taunton.exception;
/**
 * 数据库异常类
 * @author Administrator
 *
 */
public class DBException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public DBException(){
	
}
public DBException(String msg){
	super(msg);
}
public DBException(String msg,Exception ex){
	super(msg,ex);
}
}

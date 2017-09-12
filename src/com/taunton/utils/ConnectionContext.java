package com.taunton.utils;

import java.sql.Connection;

/**
 * ThreadLocal演变类
 * @author taunton
 *
 */

public class ConnectionContext {
	
	private ConnectionContext(){}
	
	private static ConnectionContext instance = new ConnectionContext();
	
	public static ConnectionContext getInstance() {
		return instance;
	}

	private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
			
	public void bind(Connection connection){
		connectionThreadLocal.set(connection);
	}
	
	public Connection get(){
		return connectionThreadLocal.get();
	}
	
	public void remove(){
		connectionThreadLocal.remove();
	}
	
}

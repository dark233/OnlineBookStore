package com.taunton.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.taunton.exception.DBException;

public class JDBCUtils {
	private static Logger logger = Logger.getLogger(JDBCUtils.class); 
	private static ComboPooledDataSource comboPooledDataSource = null;
	static{
		
		comboPooledDataSource = new ComboPooledDataSource();
	}
/**
 * 创建连接池
 * @return
 */
	public static ComboPooledDataSource getComboPooledDataSource() {
		return comboPooledDataSource;
	}
/**
 * 创建连接
 * @return
 * @throws DBException 
 */
	public static Connection getConnection()  {
		Connection connection = null;
		try {
			connection = JDBCUtils.getComboPooledDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取数据库连接失败!");
		}
		return connection;
	}
/**
 * 释放连接
 * @param connection
 * @throws DBException 
 */
	public static void release(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("释放数据库连接失败!");
		}
	}
	/**
	 * 释放rs，statement连接
	 * @throws DBException 
	 */
	public static void release(ResultSet rs, Statement statement)  {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

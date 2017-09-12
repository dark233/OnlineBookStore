package com.taunton.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.ForEachSupport;

import com.taunton.dao.DAO;
import com.taunton.utils.ConnectionContext;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.ReflectionUtils;

public class BaseDAO<T> implements DAO<T> {
	private QueryRunner qr = new QueryRunner();
	private Class<T> clazz;
	protected static Logger logger = null; 
	public BaseDAO() {
		clazz = ReflectionUtils.getSuperGenericType(getClass());
		logger = Logger.getLogger(clazz);
	}
	@Override
	public long insert(String sql, Object... args) throws SQLException {
		//preparStatement、statement等是sql包里面的，这里不能用mysql.sql包里面的
		Connection conn = ConnectionContext.getInstance().get();
		long id = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			logger.info("sql拼装语句:\n"+sql);
			for (Object object : args) {
				logger.info("预编译参数:"+object);
			}
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getLong(1);
				}
		}finally{
			JDBCUtils.release(rs, ps);
		}
		return id;
		
	}

	@Override
	public void update(String sql, Object... args) throws SQLException {
		Connection conn = ConnectionContext.getInstance().get();
		logger.info("SQL拼装语句:\n"+sql);
		for (Object object : args) {
			logger.info("预编译参数:"+object);
		}
		qr.update(conn,sql, args);

	}

	@Override
//	public User query(String sql, Object... args) {
//		try {
//			return (User) qr.query(sql, new ResultSetHandler<User>(){
//
//				@Override
//				public User handle(ResultSet arg0) throws SQLException {
//					arg0.next();
//					return new User(arg0.getInt(1), arg0.getString(2), arg0.getString(3), arg0.getString(4), arg0.getFloat(5));
//					
//				}
//				
//			}, args);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	public T query(String sql, Object... args) throws SQLException {
		Connection conn = ConnectionContext.getInstance().get();
		logger.info("SQL拼装语句:\n"+sql);
		for (Object object : args) {
			logger.info("预编译参数:"+object);
		}
		return  qr.query(conn,sql, new BeanHandler<T>(clazz), args);

	}
	
	@Override
	public List<T> queryList(String sql, Object... args) throws SQLException  {
		Connection conn = ConnectionContext.getInstance().get();
		logger.info("SQL拼装语句:\n"+sql);
		for (Object object : args) {
			logger.info("预编译参数:"+object);
		}
		return qr.query(conn,sql, new BeanListHandler<T>(clazz),args);

	}

	@Override
	public <V> V querySingle(String sql, Object... args) throws SQLException {
		Connection conn = ConnectionContext.getInstance().get();
		logger.info("SQL拼装语句:\n"+sql);
		for (Object object : args) {
			logger.info("预编译参数:"+object);
		}
		return (V) qr.query(conn,sql, new ScalarHandler(),args);

	}

	@Override
	public void batch(String sql, Object[]... args) throws SQLException {
		Connection conn = ConnectionContext.getInstance().get();
		logger.info("SQL拼装语句:\n"+sql);
		for (Object object : args) {
			logger.info("预编译参数:"+object);
		}
		qr.batch(conn,sql, args);
	}

}

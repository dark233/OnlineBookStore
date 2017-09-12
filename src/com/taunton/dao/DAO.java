package com.taunton.dao;

import java.util.List;

import com.taunton.exception.DBException;

/**
 * 基本的dao接口
 * @author Administrator
 *
 */
public interface DAO<T> {
	/**
	 * 执行插入数据的操作，并返回插入数据的ID的值
	 * @param sql
	 * @param args
	 * @return
	 */
long insert(String sql,Object ... args) throws Exception;
/**
 * 执行Update操作，包括insert、delete，update操作，insert操作没返回值
 * @param sql
 * @param args
 */
void update(String sql,Object ... args) throws Exception;
/**
 * 查询单行数据，返回的是单行数据封装成的一个对象
 * @param sql
 * @param args
 * @return
 */
T query(String sql,Object ... args) throws Exception;
/**
 * 查询多行数据，返回的是多个封装对象组成的一个集合
 * @param sql
 * @param args
 * @return
 * @throws DBException 
 */
List<T> queryList(String sql,Object ... args) throws Exception;
/**
 * 查询单行单列数据，一般在统计数量时使用
 * @param sql
 * @param args
 * @return
 */
<V> V querySingle(String sql,Object ... args) throws Exception;
/**
 * 进行批量更新操作，无返回值,传入的参数是内容和个数都不定长的数组
 * @param sql
 * @param args
 */
void batch(String sql,Object[] ... args) throws Exception;
}

package com.taunton.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.taunton.javabean.Category;
public interface CategoryDAO {
	
	/**
	 * 检查指定类型是否有子类型
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public boolean checkIsHavaChild(String cid) throws SQLException;
	/**
	 * 封装一个完整的Category Bean
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Category toCategory(Category category) throws SQLException;
	
	/**
	 * 遍历穿入的Category集合，对每个Category对象进行封装
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public Set<Category> toCategoryList(Set<Category> categoryList) throws SQLException;
	
	/**
	 * 返回所有分类
	 * @return
	 * @throws SQLException 
	 */
	public Set<Category> findAll() throws SQLException ;
	
	/**
	 * 通过父分类查询子分类
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public Set<Category> findByParent(String pid) throws SQLException ;
	
	/**
	 * 添加分类
	 * @param category
	 * @throws SQLException 
	 */
	public void add(Category category) throws SQLException ;
	
	/**
	 * 获取所有根父分类
	 * @return
	 * @throws SQLException
	 */
	public Set<Category> findParents() throws SQLException;
	
	/**
	 * 加载分类
	 * 即可加载一级分类，也可加载二级分类
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public Category load(String cid) throws SQLException;
	
	/**
	 * 修改分类
	 * 即可修改一级分类，也可修改二级分类
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(Category category) throws SQLException ;
	
	/**
	 * 查询指定父分类下子分类的个数
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public int findChildrenCountByParent(String pid) throws SQLException ;
	
	/**
	 * 删除分类
	 * @param cid
	 * @throws SQLException 
	 */
	public void delete(String cid) throws SQLException ;
	
}

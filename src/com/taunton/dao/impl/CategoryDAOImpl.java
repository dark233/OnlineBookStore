package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;

import com.taunton.dao.CategoryDAO;
import com.taunton.javabean.Category;

public class CategoryDAOImpl extends BaseDAO<Category> implements CategoryDAO {

	@Override
	public boolean checkIsHavaChild(String cid) throws SQLException {
//		String sql = "SELECT COUNT(cid) FROM category WHERE pid = ?";
//		Number count = (Number)querySingle(sql, cid);
//		int count2 = count==null?0:count.intValue();
//		if(count2!=0){
//			return true;
//		}
		return false;
		
	}
	@Override
	public Category toCategory(Category category) throws SQLException {
		if(category.getPid()!=null){
			Category parent = load(category.getPid());
			category.setParent(parent);
		}
		return category;
	}

	@Override
	public Set<Category> toCategoryList(Set<Category> categoryList)
			throws SQLException {
		for (Category category : categoryList) {
			category = toCategory(category);
		}
		return categoryList;
	}

	@Override
	public Set<Category> findAll() throws SQLException {
		/*
		 * 1. 查询出所有一级分类
		 */
		String sql = "SELECT cid,pid,cname,`desc` FROM category WHERE pid is null ORDER BY orderBy";
		Set<Category> categoryList = new LinkedHashSet<Category>(queryList(sql));
		/*
		 * 2.加载父级分类
		 */
		Set<Category> parents = toCategoryList(categoryList);
		/*
		 * 3. 循环遍历所有的一级分类，为每个一级分类加载它的二级分类 
		 * 加载子分类
		 */
		for(Category parent : parents) {
			// 查询出当前父分类的所有子分类
			Set<Category> children = findByParent(parent.getCid());
			// 设置给父分类
			parent.setChildren(children);
		}
//		可以把上的循环做成一个递归方法，这样无论有多少级类型都可以查出来
//		public Set<Categor> setChildren(Set<Category> parents){
//			for(Category parent : parents) {
//				if(checkIsHavaChild(parent.getCid())){
//					Set<Category> categorys = findByParent(parent.getCid());
//					Set<Category> children = setChildren(categorys);
//					parent.setChildren(children);
//				}
//			}
//		return parents;
//		}
		return parents;
	}

	@Override
	public Set<Category> findByParent(String pid) throws SQLException {
		String sql = "SELECT cid,pid,cname,`desc` FROM category where pid= COALESCE(?,pid) ORDER BY orderBy";
		Set<Category> children = new LinkedHashSet<>(queryList(sql, pid));
		return toCategoryList(children);
	}

	@Override
	public void add(Category category) throws SQLException {
		String sql = "INSERT INTO category(cid,pid,cname,`desc`) VALUES(?,?,?,?)";
		Object[] obj = {category.getCid(), category.getPid(),category.getCname(), category.getDesc()};
		update(sql, obj);
	}

	@Override
	public Set<Category> findParents() throws SQLException {
		/*
		 * 1. 查询出所有一级分类
		 */
//		String sql = "SELECT cid,pid,cname,desc,orderBy FROM category where pid is null order by orderBy";
//		String sql = "SELECT a.cid,a.pid,a.cname,a.`desc` FROM category a LEFT OUTER JOIN category b ON a.cid = b.pid "
//				+ "WHERE a.pid IS NULL AND b.cid IS NULL order by orderBy";
		String sql = "SELECT cid,pid,cname,`desc` FROM category WHERE pid IS NULL order by orderBy";
		Set<Category> categoryList = new LinkedHashSet<Category>(queryList(sql));
		return categoryList;
	}

	@Override
	public Category load(String cid) throws SQLException {
		String sql = "SELECT cid,pid,cname,`desc` FROM category WHERE cid=?";
		return toCategory(query(sql, cid));
	}

	@Override
	public void edit(Category category) throws SQLException {
		String sql = "UPDATE category SET cname=?, pid=?, `desc`=? WHERE cid=?";
		Object[] obj = {category.getCname(), category.getPid(), category.getDesc(), category.getCid()};
		update(sql, obj);
	}

	@Override
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql = "SELECT COUNT(*) FROM category WHERE pid= COALESCE(?,pid)";
		Number count = (Number)querySingle(sql, pid);
		return count == null ? 0 : count.intValue();
	}

	@Override
	public void delete(String cid) throws SQLException {
		String sql = "DELETE FROM category WHERE cid=?";
		update(sql, cid);
	}

	

}

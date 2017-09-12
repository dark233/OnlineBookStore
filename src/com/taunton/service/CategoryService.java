package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.taunton.dao.impl.CategoryDAOImpl;
import com.taunton.javabean.Category;
import com.taunton.servlet.CategoryServlet;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;

public class CategoryService {
	private static Logger logger = Logger.getLogger(CategoryService.class); 
	private CategoryDAOImpl cd = null;
	public CategoryService(){
		cd = DomainFactory.createDomainInstance(CategoryDAOImpl.class);
	}
	/**
	 * 查询指定父分类下子分类的个数
	 * 
	 * @param pid
	 * @return
	 */
	public int findChildrenCountByParent(String pid) {
		try {
			return cd.findChildrenCountByParent(pid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);		}
	}

	/**
	 * 删除分类
	 * 
	 * @param cid
	 * @throws SQLException 
	 */
	public boolean delete(String cid) {
		boolean flag = false;
			try {
				cd.delete(cid);
				flag = true;
			} catch (SQLException e) {
				logger.error(e);
				throw new RuntimeException(e);
			}
		return flag;
	}

	/**
	 * 修改分类
	 * 
	 * @param category
	 * @throws SQLException 
	 */
	public boolean edit(Category category) {
		boolean flag = false;
		try {
			cd.edit(category);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}

	/**
	 * 加载分类
	 * 
	 * @param cid
	 * @return
	 */
	public Category load(String cid) {
		try {
			return cd.load(cid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加分类
	 * 
	 * @param category
	 * @throws SQLException 
	 */
	public boolean add(Category category) {
		boolean flag = false;
			try {
				cd.add(category);
				flag = true;
			} catch (SQLException e) {
				logger.error(e);
				throw new RuntimeException(e);
			}
			return flag;
	}

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	public Set<Category> findAll() {
		try {
			return cd.findAll();
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取所有父分类
	 * 
	 * @return
	 */
	public Set<Category> findParents() {
		try {
			return cd.findParents();
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);		}
	}

	/**
	 * 查询指定父分类下的所有子分类
	 * 
	 * @param pid
	 * @return
	 */
	public Set<Category> findChildren(String pid) {
		try {
			return cd.findByParent(pid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);		}
	}
	
	/**
	 * 获取一个图书类型对象（用于存储在图书对象内）
	 * @param cid
	 * @return
	 */
	public Category findOneCategory(String cid){
		Category category = null;
		try {
			category = cd.load(cid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return category;
	}
}

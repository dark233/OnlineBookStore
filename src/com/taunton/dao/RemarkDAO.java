package com.taunton.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.taunton.javabean.Book;
import com.taunton.javabean.Remark;
import com.taunton.web.CriteriaBook;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public interface RemarkDAO {
	/**
	 * 添加一条评论
	 * 
	 * @param remark
	 * @return
	 */
	public void addRemark(Remark remark) throws Exception;
	/**
	 * 批量添加评论
	 * @param coll
	 * @throws Exception
	 */
	public void addRemarkList(Collection<Remark> coll) throws Exception;

	/**
	 * 根据评论id删除评论
	 * 
	 * @param remarkid
	 */
	public void deleteRemarkWithRemarkId(String remarkid) throws Exception;

	/**
	 * 根据用户id删除评论
	 * 
	 * @param userid
	 */
	public void deleteRemarkWithUserId(String userid) throws Exception;

	/**
	 * 根据图书id删除评论
	 * 
	 * @param bookid
	 */
	public void deleteRemarkWithBookId(String bookid) throws Exception;

	/**
	 * 根据评论id更新评论
	 * 
	 * @param remark
	 */
	public void updateRemarkWithRemarkId(Remark remark) throws Exception;
	/**
	 * 根据评论id更新评论展示状态（1.显示评价，2.隐藏/删除评价）
	 * @param isShow
	 * @throws Exception
	 */
	public void updateIsShowWithRemarkId(int isShow,String remarkid) throws Exception;

	/**
	 * 根据评论id获取评论
	 * 
	 * @param remarkid
	 * @return
	 */
	public Remark getRemarkWithRemarkId(String remarkid) throws Exception;

	/**
	 * 根据用户id获取评论集合
	 * 
	 * @param userid
	 * @return
	 */
	public Set<Remark> getRemarkWithUserId(String userid) throws Exception;
	/**
	 * 获取当前图书下的所有评论的page
	 * @param cr
	 * @return
	 * @throws Exception
	 */
	public Page<Remark> getPageWithBookId(CriteriaRemark cr) throws Exception ;
	/**
	 * 获取当前图书下的所有评论数
	 * @param cr
	 * @param bookid
	 * @return
	 * @throws Exception
	 */
	public long getTotalRemarkNumberWithBookId(CriteriaRemark cr) throws Exception ;
	/**
	 * 获取当前图书下的所有评论集合
	 * 
	 * @param bookid
	 * @return
	 */
	public List<Remark> getRemarkListWithBookId(CriteriaRemark cr,int pageSize) throws Exception;

}

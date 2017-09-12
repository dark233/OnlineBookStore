package com.taunton.web;

public class CriteriaRemark {

	private int minLevel = 1;
	private int maxLevel = 5;
	private int pageNo = 1;
	private String bookid;
	
	/**
	 * 获取评价区间中的左闭条件
	 * @return
	 */
	public int getMinLevel() {
		return minLevel;
	}
	/**
	 * 设置评价区间中的左闭条件
	 * @param minLevel
	 */
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	/**
	 * 获取评价区间中的右闭条件
	 * @return
	 */
	public int getMaxLevel() {
		return maxLevel;
	}
	/**
	 * 设置评价区间中的右闭条件
	 * @param maxLevel
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	/**
	 * 获取页面请求参数中的页码
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * 设置页面请求参数中的页码
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * 获取页面传入参数中的图书id
	 * @return
	 */
	public String getBookid() {
		return bookid;
	}
	/**
	 * 设置页面传入参数中的图书id
	 * @param bookid
	 */
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	public CriteriaRemark() {
		super();
	}
	public CriteriaRemark(int minLevel, int maxLevel, int pageNo,String bookid) {
		super();
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.pageNo = pageNo;
		this.bookid = bookid;
	}
	

	

}

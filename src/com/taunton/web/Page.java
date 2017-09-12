package com.taunton.web;

import java.util.List;

public class Page<T> {

	/**
	 * 当前第几页
	 */
	private int pageNo;
	/**
	 * 记录跳转输入框的违法页数
	 */
	/**
	 * 当前页的 List
	 */
	private List<T> list;
	
	/**
	 * 每页显示多少条记录
	 */
	private int pageSize = 4;
	
	/**
	 * 共有多少条记录
	 */
	
	private long totalItemNumber;

	/**
	 * 构造器中需要对 pageNo 进行初始化
	 * 
	 * @param pageNo
	 */
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}
	
	//需要校验页码是否合法
	public int getPageNo() {
		if(pageNo <=0)
			pageNo = 1;
		
		if(pageNo > getTotalPageNumber()){
			pageNo = getTotalPageNumber();
			if(getTotalPageNumber()==0){
				pageNo = 1;
			}
		}
		return pageNo;
	}

	/**
	 * 获取一个页面的记录显示条数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 设置一个页面的记录显示条数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize){
		this.pageSize=pageSize;
	}
	/**
	 * 设置某一个页面的显示内容的集合
	 * @param list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
	/**
	 * 返回一个页面的显示内容的list集合
	 * @return
	 */
	public List<T> getList() {
		return list;
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getTotalPageNumber(){
		
		int totalPageNumber = (int)totalItemNumber / pageSize;
		
		if(totalItemNumber % pageSize != 0){
			totalPageNumber++;
		}
		
		return totalPageNumber;
	}
	/**
	 * 设置总的记录条数
	 * @param totalItemNumber
	 */
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}
	/**
	 * 获取总的记录条数
	 * @return
	 */
	public long getTotalItemNumber() {
		return this.totalItemNumber;
	}
	/**
	 * 是否存在下一页
	 * @return
	 */
	public boolean isHasNext(){
		if(getPageNo() < getTotalPageNumber()){
			return true;
		}
		
		return false;
	}
	/**
	 * 是否存在上一页
	 * @return
	 */
	public boolean isHasPrev(){
		if(getPageNo() > 1){
			return true;
		}
		
		return false;
	}
	/**
	 * 获取上一页的页码
	 * @return
	 */
	public int getPrevPage(){
		if(isHasPrev()){
			return getPageNo() - 1;
		}
		
		return getPageNo();
	}
	/**
	 * 获取上一页的页码
	 * @return
	 */
	public int getNextPage(){
		if(isHasNext()){
			return getPageNo() + 1;
		}
		
		return getPageNo();
	}
}

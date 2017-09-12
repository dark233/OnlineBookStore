package com.taunton.web;

public class CriteriaBook {

	private float minPrice = 0;
	private float maxPrice = Integer.MAX_VALUE;
	private int pageNo;
	private String orderField;
	private String cid;
	private String search;
	/**
	 * 获取页面上价格条件搜索框里面输入的最小价格
	 * 
	 * @return
	 */
	public float getMinPrice() {
		return minPrice;
	}

	/**
	 * 根据页面上价格条件搜索框里面输入的最小价格设置最小价格
	 * 
	 * @param minPrice
	 */
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	/**
	 * 获取页面上价格条件搜索框里面输入的最大价格
	 * 
	 * @return
	 */
	public float getMaxPrice() {
		return maxPrice;
	}

	/**
	 * 根据页面上价格条件搜索框里面输入的最大价格设置最大价格
	 * 
	 * @param maxPrice
	 */
	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	/**
	 * 获取当前页码
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * 设置当前页码
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * 获取排序字段
	 * @return
	 */
	public String getOrderField() {
		return orderField;
	}
	/**
	 * 设置排序字段
	 * @param orderField
	 */
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	/**
	 * 获取页面传入请求参数图书类型id
	 * @return
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * 设置页面请求参数图书类型id
	 * @param cid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * 获取页面请求参数搜索条件
	 * @return
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * 获取页面请求参数搜索条件
	 * @param search
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	
	public CriteriaBook() {
		super();
	}
	public CriteriaBook(float minPrice, float maxPrice, int pageNo,String orderField) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.pageNo = pageNo;
		this.orderField = orderField;
	}

	public CriteriaBook(float minPrice, float maxPrice, int pageNo,String orderField,String cid) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.pageNo = pageNo;
		this.orderField = orderField;
		this.cid = cid;
	}

	

}

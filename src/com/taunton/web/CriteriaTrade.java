package com.taunton.web;

public class CriteriaTrade {

	private Integer tradeStatus = null;
	private String userid = null;
	private int pageNo = 1;
	private String search;
	
	/**
	 * 获取要查询的订单的状态 
	 * @return
	 */
	public Integer getTradeStatus() {
		return tradeStatus;
	}
	/**
	 * 设置要查询的订单的状态
	 * @param status
	 */
	public void setTradeStatus(Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	/**
	 * 获取用户id
	 * @return
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * 设置用户id
	 * @param userid
	 */
	public void setUserid(String userid) {
		this.userid = userid;
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
	 * 获取查询订单条件
	 * @return
	 */
	public String getSearch() {
		return search;
	}
	/**
	 * 设置查询订单条件
	 * @param search
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	@Override
	public String toString() {
		return "CriteriaTrade [tradeStatus=" + tradeStatus + ", userid="
				+ userid + ", pageNo=" + pageNo + ", search=" + search + "]";
	}
	public CriteriaTrade() {
		super();
		
	}
	public CriteriaTrade(Integer tradeStatus, String userid, int pageNo,
			String search) {
		super();
		this.tradeStatus = tradeStatus;
		this.userid = userid;
		this.pageNo = pageNo;
		this.search = search;
	}
	
	
	
	

	

}

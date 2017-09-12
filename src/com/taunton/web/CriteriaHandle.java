package com.taunton.web;

public class CriteriaHandle {

	private Integer handleStatus = null;
	private Integer operate = null;
	private Integer isValid = null;
	private String backStageUserid = null;
	private int pageNo = 1;
	private String search;
	
	/**
	 * 获取要查询的订单的状态 
	 * @return
	 */
	public Integer getHandleStatus() {
		return handleStatus;
	}
	/**
	 * 设置要查询的订单的状态
	 * @param handleStatus
	 */
	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}
	/**
	 * 获取订单处理记录的功能
	 * @return
	 */
	public Integer getOperate() {
		return operate;
	}
	/**
	 * 设置订单处理记录的功能
	 * @param operate
	 */
	public void setOperate(Integer operate) {
		this.operate = operate;
	}
	/**
	 * 获取订单处理记录是否有效（1.有效，2.无效）
	 * @return
	 */
	public Integer getIsValid() {
		return isValid;
	}
	/**
	 * 设置订单处理记录是否有效（1，有效，2.无效）
	 * @param isValid
	 */
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	/**
	 * 获取后台用户id
	 * @return
	 */
	public String getBackStageUserid() {
		return backStageUserid;
	}
	/**
	 * 设置后台用户id
	 * @param backStageUserid
	 */
	public void setBackStageUserid(String backStageUserid) {
		this.backStageUserid = backStageUserid;
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
		return "CriteriaHandle [handleStatus=" + handleStatus + ", operate="
				+ operate + ", isValid=" + isValid + ", backStageUserid="
				+ backStageUserid + ", pageNo=" + pageNo + ", search=" + search
				+ "]";
	}
	public CriteriaHandle() {
		super();
		
	}
	public CriteriaHandle(Integer handleStatus, Integer operate,
			Integer isValid, String backStageUserid, int pageNo, String search) {
		super();
		this.handleStatus = handleStatus;
		this.operate = operate;
		this.isValid = isValid;
		this.backStageUserid = backStageUserid;
		this.pageNo = pageNo;
		this.search = search;
	}
	
}

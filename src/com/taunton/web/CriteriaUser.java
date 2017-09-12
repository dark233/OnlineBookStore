package com.taunton.web;

public class CriteriaUser {
	
	private Integer isBan = null;
	private int pageNo = 1;
	private String search;
	public Integer getIsBan() {
		return isBan;
	}
	
	public void setIsBan(Integer isBan) {
		this.isBan = isBan;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	public CriteriaUser() {
		super();
		
	}

	public CriteriaUser(Integer isBan, int pageNo, String search) {
		super();
		this.isBan = isBan;
		this.pageNo = pageNo;
		this.search = search;
	}
	
	
}

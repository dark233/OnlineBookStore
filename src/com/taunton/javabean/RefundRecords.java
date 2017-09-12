package com.taunton.javabean;

public class RefundRecords {
	private String id;
	private String handleid;
	private int status_;
	private String refundReason;
	private String refusedReason;
	private String createTime;
	private String updateTime;
	/**
	 * 获取退款记录id
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置退款记录id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取退款记录所属的订单处理记录id
	 * @return
	 */
	public String getHandleid() {
		return handleid;
	}
	/**
	 * 设置退款记录所属的订单处理记录id
	 * @param handleid
	 */
	public void setHandleid(String handleid) {
		this.handleid = handleid;
	}
	/**
	 * 获取退款记录状态（1.处理中、2.拒绝、3.成功）
	 * @return
	 */
	public int getStatus_() {
		return status_;
	}
	/**
	 * 设置退款记录状态（1.处理中、2.拒绝、3.成功）
	 * @param status_
	 */
	public void setStatus_(int status_) {
		this.status_ = status_;
	}
	/**
	 * 获取退款原因
	 * @return
	 */
	public String getRefundReason() {
		return refundReason;
	}
	/**
	 * 设置退款原因
	 * @param refundReason
	 */
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	/**
	 * 获取拒绝退款原因
	 * @return
	 */
	public String getRefusedReason() {
		return refusedReason;
	}
	/**
	 * 设置拒绝退款原因
	 * @param refusedReason
	 */
	public void setRefusedReason(String refusedReason) {
		this.refusedReason = refusedReason;
	}
	/**
	 * 获取本条记录创建时间
	 * @return
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 设置本条记录创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取本条记录更新时间
	 * @return
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置本条记录更新时间
	 * @param updateTime
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "RefundRecords [id=" + id + ", handleid=" + handleid
				+ ", status_=" + status_ + ", refundReason=" + refundReason
				+ ", refusedReason=" + refusedReason + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
	public RefundRecords() {
		super();
		
	}
	public RefundRecords(String id, String handleid, int status_,
			String refundReason, String refusedReason, String createTime,
			String updateTime) {
		super();
		this.id = id;
		this.handleid = handleid;
		this.status_ = status_;
		this.refundReason = refundReason;
		this.refusedReason = refusedReason;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	
}

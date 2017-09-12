package com.taunton.javabean;

public class Handle {

private String handleid;
private String tradeid;
private String backStageUserid;
private int operate;
private int status_;
private int isValid;
private String createTime;
private String updateTime;
private Trade trade;
private RefundRecords refundRecords;
/**
 * 获取处理记录号
 * @return
 */
public String getHandleid() {
	return handleid;
}
/**
 * 设置处理记录号
 * @param handleid
 */
public void setHandleid(String handleid) {
	this.handleid = handleid;
}
/**
 * 获取处理记录所属订单号
 * @return
 */
public String getTradeid() {
	return tradeid;
}
/**
 * 设置处理记录所属订单号
 * @param tradeid
 */
public void setTradeid(String tradeid) {
	this.tradeid = tradeid;
}
/**
 * 获取处理记录分配到的后台用户
 * @return
 */
public String getBackStageUserid() {
	return backStageUserid;
}
/**
 * 设置处理记录分配到的后台用户
 * @param backStageUserid
 */
public void setBackStageUserid(String backStageUserid) {
	this.backStageUserid = backStageUserid;
}
/**
 * 获取处理记录的处理事项（1.发货，2.退款）
 * @return
 */
public int getOperate() {
	return operate;
}
/**
 * 设置处理记录的处理事项（1.发货，2.退款）
 * @param operate
 */
public void setOperate(int operate) {
	this.operate = operate;
}
/**
 * 获取处理记录的处理状态（1.处理完成，2.处理中/未完成）
 * @return
 */
public int getStatus_() {
	return status_;
}
/**
 * 设置处理记录的处理状态（1.处理完成，2.处理中/未完成）
 * @param status
 */
public void setStatus_(int status_) {
	this.status_ = status_;
}
/**
 * 获取该订单处理记录是否处于有效状态（1.有效，2.无效）
 * @return
 */
public int getIsValid() {
	return isValid;
}
/**
 * 设置该订单处理记录的有效状态（1.有效，2.无效）
 * @param isValid
 */
public void setIsValid(int isValid) {
	this.isValid = isValid;
}
/**
 * 获取记录创建时间
 * @return
 */
public String getCreateTime() {
	return createTime;
}
/**
 * 设置记录创建时间
 * @param createTime
 */
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
/**
 * 获取记录更新时间
 * @return
 */
public String getUpdateTime() {
	return updateTime;
}
/**
 * 设置记录更新时间
 * @param updateTime
 */
public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
}
/**
 * 获取订单处理记录关联到的订单
 * @return
 */
public Trade getTrade() {
	return trade;
}
/**
 * 设置订单处理记录关联到的订单
 * @param trade
 */
public void setTrade(Trade trade) {
	this.trade = trade;
}
/**
 * 获取订单处理记录下的退款记录
 * @return
 */
public RefundRecords getRefundRecords() {
	return refundRecords;
}
/**
 * 设置订单处理记录下的退款记录
 * @param refundRecords
 */
public void setRefundRecords(RefundRecords refundRecords) {
	this.refundRecords = refundRecords;
}
@Override
public String toString() {
	return "Handle [handleid=" + handleid + ", tradeid=" + tradeid
			+ ", backStageUserid=" + backStageUserid + ", operate=" + operate
			+ ", status_=" + status_ + ", isValid=" + isValid + ", createTime="
			+ createTime + ", updateTime=" + updateTime + ", trade=" + trade
			+ "]";
}
public Handle() {
	super();
	
}
public Handle(String handleid, String tradeid, String backStageUserid,
		int operate, int status_, int isValid, String createTime,
		String updateTime) {
	super();
	this.handleid = handleid;
	this.tradeid = tradeid;
	this.backStageUserid = backStageUserid;
	this.operate = operate;
	this.status_ = status_;
	this.isValid = isValid;
	this.createTime = createTime;
	this.updateTime = updateTime;
}






}

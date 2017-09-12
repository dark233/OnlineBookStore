package com.taunton.javabean;

public class RechargeRecords {
private String id;
private String userid;
private float rechargeMoney;
private String rechargeDate;
private String rechargeWays;
private int isShow;
private User user;


/**
 * 获取充值记录id
 * @return
 */
public String getId() {
	return id;
}
/**
 * 设置充值记录id
 * @param id
 */
public void setId(String id) {
	this.id = id;
}
/**
 * 获取充值用户id
 * @return
 */
public String getUserid() {
	return userid;
}
/**
 * 设置充值用户id
 * @param userid
 */
public void setUserid(String userid) {
	this.userid = userid;
}
/**
 * 获取充值金额
 * @return
 */
public float getRechargeMoney() {
	return rechargeMoney;
}
/**
 * 设置充值金额
 * @param rechargeMoney
 */
public void setRechargeMoney(float rechargeMoney) {
	this.rechargeMoney = rechargeMoney;
}
/**
 * 获取充值日期
 * @return
 */
public String getRechargeDate() {
	return rechargeDate;
}
/**
 * 设置充值日期
 * @param rechargeDate
 */
public void setRechargeDate(String rechargeDate) {
	this.rechargeDate = rechargeDate;
}
/**
 * 获取充值方式
 * @return
 */
public String getRechargeWays() {
	return rechargeWays;
}
/**
 * 设置充值方式
 * @param rechargeWays
 */
public void setRechargeWays(String rechargeWays) {
	this.rechargeWays = rechargeWays;
}
/**
 * 获取此条充值记录是否展示给用户（1表示true，2表示false）
 * @return
 */
public int getIsShow() {
	return isShow;
}
/**
 * 设置此条充值记录是否展示给用户（1表示true，2表示false）
 * @param isShow
 */
public void setIsShow(int isShow) {
	this.isShow = isShow;
}
/**
 * 获取充值记录的用户对象
 * @return
 */
public User getUser() {
	return user;
}
/**
 * 设置充值记录的用户对象
 * @param user
 */
public void setUser(User user) {
	this.user = user;
}
@Override
public String toString() {
	return "RechargeRecords [id=" + id + ", userid=" + userid
			+ ", rechargeMoney=" + rechargeMoney + ", rechargeDate="
			+ rechargeDate + ", rechargeWays=" + rechargeWays + ", isShow="
			+ isShow + ", user=" + user + "]";
}
public RechargeRecords() {
	super();
}
public RechargeRecords(String id, String userid, float rechargeMoney,
		String rechargeDate, String rechargeWays, int isShow) {
	super();
	this.id = id;
	this.userid = userid;
	this.rechargeMoney = rechargeMoney;
	this.rechargeDate = rechargeDate;
	this.rechargeWays = rechargeWays;
	this.isShow = isShow;
}


}

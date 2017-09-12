package com.taunton.javabean;

public class BackStageUser {
private String userid;
private String username;
private String realname;
private String pwd;
private String tel;
private String headImgUrl;
private int handledTradeNum;
private int handlingTradeNum;
private int isBan;
private int roleid;
private String registDate;
private String email;
private Role role;
/**
 * 获取后台用户id
 * @return
 */
public String getUserid() {
	return userid;
}
/**
 * 设置后台用户id
 * @param userid
 */
public void setUserid(String userid) {
	this.userid = userid;
}
/**
 * 获取后台用户用户名
 * @return
 */
public String getUsername() {
	return username;
}
/**
 * 设置管后台用户用户名
 * @param username
 */
public void setUsername(String username) {
	this.username = username;
}
/**
 * 获取后台用户真实姓名
 * @return
 */
public String getRealname() {
	return realname;
}
/**
 * 设置后台用户真实姓名
 * @param realname
 */
public void setRealname(String realname) {
	this.realname = realname;
}
/**
 * 获取后台用户登录密码
 * @return
 */
public String getPwd() {
	return pwd;
}
/**
 * 设置后台用户登录密码
 * @param pwd
 */
public void setPwd(String pwd) {
	this.pwd = pwd;
}
/**
 * 获取后台用户手机号码
 * @return
 */
public String getTel() {
	return tel;
}
/**
 * 设置后台用户手机号码
 * @param tel
 */
public void setTel(String tel) {
	this.tel = tel;
}
/**
 * 获取后台用户头像缓存路径
 * @return
 */
public String getHeadImgUrl() {
	return headImgUrl;
}
/**
 * 设置后台用户头像缓存路径
 * @param headImgUrl
 */
public void setHeadImgUrl(String headImgUrl) {
	this.headImgUrl = headImgUrl;
}
/**
 * 获取后台用户已处理订单
 * @return
 */
public int getHandledTradeNum() {
	return handledTradeNum;
}
/**
 * 设置后台用户已处理订单
 * @param handledTradeNum
 */
public void setHandledTradeNum(int handledTradeNum) {
	this.handledTradeNum = handledTradeNum;
}
/**
 * 获取后台用户处理中订单
 * @return
 */
public int getHandlingTradeNum() {
	return handlingTradeNum;
}
/**
 * 设置后台用户处理中订单
 * @param handlingTradeNum
 */
public void setHandlingTradeNum(int handlingTradeNum) {
	this.handlingTradeNum = handlingTradeNum;
}
/**
 * 获取后台用户被ban状态
 * @return
 */
public int getIsBan() {
	return isBan;
}
/**
 * 设置后台用户被ban状态
 * @param isBan
 */
public void setIsBan(int isBan) {
	this.isBan = isBan;
}
/**
 * 获取后台用户email
 * @return
 */
public String getEmail() {
	return email;
}
/**
 * 设置后台用户email
 * @param email
 */
public void setEmail(String email) {
	this.email = email;
}
/**
 * 获取后台用户所属角色的id
 * @return
 */
public int getRoleid() {
	return roleid;
}
/**
 * 设置后台用户所属角色的id
 * @param roleid
 */
public void setRoleid(int roleid) {
	this.roleid = roleid;
}
/**
 * 获取后台用户注册日期
 * @return
 */
public String getRegistDate() {
	return registDate;
}
/**
 * 设置后台用户注册日期
 * @param registDate
 */
public void setRegistDate(String registDate) {
	this.registDate = registDate;
}
/**
 * 获取后台用户所属的角色的对象
 * @return
 */
public Role getRole() {
	return role;
}
/**
 * 设置后台用户所属角色的对象
 * @param role
 */
public void setRole(Role role) {
	this.role = role;
}
@Override
public String toString() {
	return "BackStageUser [userid=" + userid + ", username=" + username
			+ ", realname=" + realname + ", pwd=" + pwd + ", tel=" + tel
			+ ", headImgUrl=" + headImgUrl + ", handledTradeNum="
			+ handledTradeNum + ", handlingTradeNum=" + handlingTradeNum
			+ ", isBan=" + isBan + ", roleid=" + roleid + ", registDate="
			+ registDate + ", email=" + email + "]";
}
public BackStageUser() {
	super();
	
}
public BackStageUser(String userid, String username, String realname,
		String pwd, String tel, String headImgUrl, int handledTradeNum,
		int handlingTradeNum, int isBan, int roleid, String registDate,
		String email) {
	super();
	this.userid = userid;
	this.username = username;
	this.realname = realname;
	this.pwd = pwd;
	this.tel = tel;
	this.headImgUrl = headImgUrl;
	this.handledTradeNum = handledTradeNum;
	this.handlingTradeNum = handlingTradeNum;
	this.isBan = isBan;
	this.roleid = roleid;
	this.registDate = registDate;
	this.email = email;
}



}

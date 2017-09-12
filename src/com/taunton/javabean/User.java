package com.taunton.javabean;

import java.util.LinkedHashSet;
import java.util.Set;

public class User {
private String userid;
private String username;
private String pwd;
private String pay_pwd;
private float balance;
private String tel;
private int level;
private String headImgUrl;
private int roleid;
private Role role;
private int isBan;
private String registDate;
private String email;
private ShoppingCart shoppingCart;
private Set<Trade> trades = new LinkedHashSet<>();
private Set<ShippingAddress> shippingAddress = new LinkedHashSet<ShippingAddress>();
private Set<Remark> remarks = new LinkedHashSet<Remark>();
private Set<RechargeRecords> rechargeRecords = new LinkedHashSet<RechargeRecords>();


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
 * 获取用户名
 * @return
 */
public String getUsername() {
	return username;
}
/**
 * 设置用户名
 * @param username
 */
public void setUsername(String username) {
	this.username = username;
}
/**
 * 获取用户密码
 * @return
 */
public String getPwd() {
	return pwd;
}
/**
 * 设置用户密码
 * @param pwd
 */
public void setPwd(String pwd) {
	this.pwd = pwd;
}
/**
 * 获取用户支付密码
 * @return
 */
public String getPay_pwd() {
	return pay_pwd;
}
/**
 * 设置用户支付密码
 * @param pay_pwd
 */
public void setPay_pwd(String pay_pwd) {
	this.pay_pwd = pay_pwd;
}
/**
 * 获取用户余额
 * @return
 */
public float getBalance() {
	return balance;
}
/**设置用户余额
 * 
 * @param balance
 */
public void setBalance(float balance) {
	this.balance = balance;
}
/**
 * 获取用户名下的订单集合
 * @return
 */
public Set<Trade> getTrades() {
	return trades;
}
/**
 * 设置用户名下的订单集合
 * @param trades
 */
public void setTrades(Set<Trade> trades) {
	this.trades = trades;
}
/**
 * 获取用户手机号码
 * @return
 */
public String getTel() {
	return tel;
}
/**
 * 设置用户手机号码
 * @param tel
 */
public void setTel(String tel) {
	this.tel = tel;
}
/**
 * 获取用户等级
 * @return
 */
public int getLevel() {
	return level;
}
/**
 * 设置用户等级
 * @param level
 */
public void setLevel(int level) {
	this.level = level;
}
/**
 * 获取用户头像存储路径
 * @return
 */
public String getHeadImgUrl() {
	return headImgUrl;
}
/**
 * 设置用户头像存储路径
 * @param headImgUrl
 */
public void setHeadImgUrl(String headImgUrl) {
	this.headImgUrl = headImgUrl;
}
/**
 * 获取用户拥有角色的角色id
 * @return
 */
public int getRoleid() {
	return roleid;
}
/**
 * 设置用户拥有角色的角色id
 * @param roleid
 */
public void setRoleid(int roleid) {
	this.roleid = roleid;
}
/**
 * 获取用户拥有角色的角色对象
 * @return
 */
public Role getRole() {
	return role;
}
/**
 * 设置用户拥有角色的角色对象
 * @param role
 */
public void setRole(Role role) {
	this.role = role;
}
/**
 * 获取用户是否被拉黑的状态（1为true，2为false）
 * @return
 */
public int getIsBan() {
	return isBan;
}
/**
 * 设置用户是否被拉黑的状态（1为true，2为false）
 * @param isBan
 */
public void setIsBan(int isBan) {
	this.isBan = isBan;
}
/**
 * 获取用户注册日期
 * @return
 */
public String getRegistDate() {
	return registDate;
}
/**
 * 设置用户注册日期
 * @param registDate
 */
public void setRegistDate(String registDate) {
	this.registDate = registDate;
}
/**
 * 获取用户绑定email
 * @return
 */
public String getEmail() {
	return email;
}
/**
 * 设置用户绑定email
 * @param email
 */
public void setEmail(String email) {
	this.email = email;
}
/**
 * 获取用户拥有的购物车对象
 * @return
 */
public ShoppingCart getShoppingCart() {
	return shoppingCart;
}
/**
 * 设置用户拥有的购物车对象
 * @param shoppingCart
 */
public void setShoppingCart(ShoppingCart shoppingCart) {
	this.shoppingCart = shoppingCart;
}
/**
 * 获取用户拥有的地址条目集合
 * @return
 */
public Set<ShippingAddress> getShippingAddress() {
	return shippingAddress;
}
/**
 * 设置用户拥有的地址条目集合
 * @param shippingAddress
 */
public void setShippingAddress(Set<ShippingAddress> shippingAddress) {
	this.shippingAddress = shippingAddress;
}
/**
 * 获取用户拥有的评价条目集合
 * @return
 */
public Set<Remark> getRemarks() {
	return remarks;
}
/**
 * 设置用户拥有的评价条目集合
 * @param remark
 */
public void setRemarks(Set<Remark> remarks) {
	this.remarks = remarks;
}
/**
 * 获取用户的充值记录
 * @return
 */
public Set<RechargeRecords> getRechargeRecords() {
	return rechargeRecords;
}
/**
 * 设置用户的充值记录
 * @param rechargeRecords
 */
public void setRechargeRecords(Set<RechargeRecords> rechargeRecords) {
	this.rechargeRecords = rechargeRecords;
}




@Override
public String toString() {
	return "User [userid=" + userid + ", username=" + username + ", pwd=" + pwd
			+ ", pay_pwd=" + pay_pwd + ", balance=" + balance + ", tel=" + tel
			+ ", level=" + level + ", headImgUrl=" + headImgUrl + ", roleid="
			+ roleid + ", role=" + role + ", isBan=" + isBan + ", registDate="
			+ registDate + ", shoppingCart=" + shoppingCart + ", trades="
			+ trades + ", shippingAddress=" + shippingAddress + ", remarks="
			+ remarks + ", rechargeRecords=" + rechargeRecords + "]";
}
public User() {
	super();
}

public User(String username, String pwd) {
	super();
	this.username = username;
	this.pwd = pwd;
}
/**
 * 
 * @param username	用户名
 * @param pwd	用户登录密码
 * @param pay_pwd	用户支付密码
 * @param balance	用户余额
 * @param tel	用户绑定手机号码
 * @param level 用户等级
 * @param roleid 用户所属角色
 * @param isBan	是否为黑名单用户
 * @param registDate 用户注册日期
 */
public User(String username, String pwd, String pay_pwd, float balance,
		String tel, int level, int roleid, int isBan,String registDate) {
	super();
	this.username = username;
	this.username = username;
	this.pwd = pwd;
	this.pay_pwd = pay_pwd;
	this.balance = balance;
	this.tel = tel;
	this.level = level;
	this.roleid = roleid;
	this.isBan = isBan;
	this.registDate = registDate;
}



}

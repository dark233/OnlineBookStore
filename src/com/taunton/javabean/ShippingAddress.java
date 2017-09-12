package com.taunton.javabean;

public class ShippingAddress {
private String shipAddrid;
private String userid;
private String cnee;
private String tel;
private String shipAddr;
private int isDefu;
private String province;
private String city;
private String district_county;
private String detailAddr;
/**
 * 获取该条收货地址id
 * @return
 */
public String getShipAddrid() {
	return shipAddrid;
}
/**
 * 设置该条收货地址id
 * @param shipAddrid
 */
public void setShipAddrid(String shipAddrid) {
	this.shipAddrid = shipAddrid;
}
/**
 * 获取该条收货地址所属用户的用户id
 * @return
 */
public String getUserid() {
	return userid;
}
/**
 * 设置该条收货地址所属用户的用户id
 * @param userid
 */
public void setUserid(String userid) {
	this.userid = userid;
}
/**
 * 获取该条收货地址中的收件人
 * @return
 */
public String getCnee() {
	return cnee;
}
/**
 * 设置该条收货地址中的收件人
 * @param cnee
 */
public void setCnee(String cnee) {
	this.cnee = cnee;
}
/**
 * 获取该条收货地址中的手机号码
 * @return
 */
public String getTel() {
	return tel;
}
/**
 * 设置该条收货地址中的手机号码
 * @param tel
 */
public void setTel(String tel) {
	this.tel = tel;
}
/**
 * 获取整条完整收货地址
 * @return
 */
public String getShipAddr() {
	return shipAddr;
}
/**
 * 设置整条完整收货地址
 * @param shipAddr
 */
public void setShipAddr(String shipAddr) {
	this.shipAddr = shipAddr;
}
/**
 * 获取该条收货地址是否为默认收货地址，1为true，2为false
 * @return
 */
public int getIsDefu() {
	return isDefu;
}
/**
 * 设置该条收货地址是否为默认收货地址，1为true，2为false
 * @param isDefu
 */
public void setIsDefu(int isDefu) {
	this.isDefu = isDefu;
}
/**
 * 获取该条收货地址的省份地址
 * @return
 */
public String getProvince() {
	return province;
}
/**
 * 设置该条收货地址的省份地址
 * @param province
 */
public void setProvince(String province) {
	this.province = province;
}
/**
 * 获取该条收货地址的城市地址
 * @return
 */
public String getCity() {
	return city;
}
/**
 * 设置该条收货地址的城市地址
 * @param city
 */
public void setCity(String city) {
	this.city = city;
}
/**
 * 获取该条收货地址的区(县)地址
 * @return
 */
public String getDistrict_county() {
	return district_county;
}
/**
 * 设置该条收货地址的区(县)地址
 * @param district_county
 */
public void setDistrict_county(String district_county) {
	this.district_county = district_county;
}
/**
 * 获取该条收货地址的详细地址
 * @return
 */
public String getDetailAddr() {
	return detailAddr;
}
/**
 * 设置该条收货地址的详细地址
 * @param detailAddr
 */
public void setDetailAddr(String detailAddr) {
	this.detailAddr = detailAddr;
}



@Override
public String toString() {
	return "ShippingAddress [shipAddrid=" + shipAddrid + ", userid=" + userid
			+ ", cnee=" + cnee + ", tel=" + tel + ", shipAddr=" + shipAddr
			+ ", isDefu=" + isDefu + ", province=" + province + ", city="
			+ city + ", district_county=" + district_county + ", detailAddr="
			+ detailAddr + "]";
}
public ShippingAddress() {
	super();
}
public ShippingAddress(String shipAddrid, String userid, String cnee,
		String tel,String shipAddr, int isDefu, String province, String city,
		String district_county, String detailAddr) {
	super();
	this.shipAddrid = shipAddrid;
	this.userid = userid;
	this.cnee = cnee;
	this.tel = tel;
	this.shipAddr = shipAddr;
	this.isDefu = isDefu;
	this.province = province;
	this.city = city;
	this.district_county = district_county;
	this.detailAddr = detailAddr;
}



}

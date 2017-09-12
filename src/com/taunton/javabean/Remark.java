package com.taunton.javabean;

import java.sql.Blob;

public class Remark {
private String remarkid;
private String bookid;
private String userid;
private String tradeItemid;
private String remark;
private Blob firstRemarkImg;
private String remarkImgUrl;
private String remarkDate;
private int remarkLevel;
private int isShow;
private User user;
private Book book;
/**
 * 获取本条评论id
 * @return
 */
public String getRemarkid() {
	return remarkid;
}
/**
 * 设置本条评论id
 * @param remarkid
 */
public void setRemarkid(String remarkid) {
	this.remarkid = remarkid;
}
/**
 * 获取本条评价所属图书的图书id
 * @return
 */
public String getBookid() {
	return bookid;
}
/**
 * 设置本条评价所属图书的图书id
 * @param bookid
 */
public void setBookid(String bookid) {
	this.bookid = bookid;
}
/**
 * 获取本条评价所属用户的用户id
 * @return
 */
public String getUserid() {
	return userid;
}
/**
 * 设置本条评价所属用户的用户id
 * @param userid
 */
public void setUserid(String userid) {
	this.userid = userid;
}
/**
 * 获取本条评价所属订单项的订单项id
 * @return
 */
public String getTradeItemid() {
	return tradeItemid;
}
/**
 * 设置本条评价所属订单项的订单id
 * @param tradeitemid
 */
public void setTradeItemid(String tradeItemid) {
	this.tradeItemid = tradeItemid;
}
/**
 * 获取本条评价的文字评价内容
 * @return
 */
public String getRemark() {
	return remark;
}
/**
 * 设置本条评价的文字评价内容
 * @param remark
 */
public void setRemark(String remark) {
	this.remark = remark;
}
/**
 * 获取本条评价的图片评价内容
 * @return
 */
public Blob getFirstRemarkImg() {
	return firstRemarkImg;
}
/**
 * 获取本条评价的图片评价内容
 * @param firstremarkimage
 */
public void setFirstRemarkImg(Blob firstRemarkImg) {
	this.firstRemarkImg = firstRemarkImg;
}
/**
 * 获取本条评论的图片存储地址
 * @return
 */
public String getRemarkImgUrl() {
	return remarkImgUrl;
}
/**
 * 设置本条评论的图片存储地址
 * @param remarkImgUrl
 */
public void setRemarkImgUrl(String remarkImgUrl) {
	this.remarkImgUrl = remarkImgUrl;
}
/**
 * 获取本条评价的评价日期
 * @return
 */
public String getRemarkDate() {
	return remarkDate;
}
/**
 * 设置本条评价的评价日期
 * @param remarkdate
 */
public void setRemarkDate(String remarkDate) {
	this.remarkDate = remarkDate;
}
/**
 * 获取本条评价的评价级别（3为好评，2为中评，1为差评）
 * @return
 */
public int getRemarkLevel() {
	return remarkLevel;
}
/**
 * 设置本条评价的评价级别（3为好评，2为中评，1为差评）
 * @param remarkLevel
 */
public void setRemarkLevel(int remarkLevel) {
	this.remarkLevel = remarkLevel;
}
/**
 * 获取评论展示状态（1.显示评论，2.隐藏评论/删除评论）
 * @return
 */
public int getIsShow() {
	return isShow;
}
/**
 * 设置评论展示状态（1.显示评论，2.隐藏评论/删除评论）
 * @param isShow
 */
public void setIsShow(int isShow) {
	this.isShow = isShow;
}
/**
 * 获取用户
 * @return
 */
public User getUser() {
	return user;
}
/**
 * 设置用户
 * @param user
 */
public void setUser(User user) {
	this.user = user;
}
/**
 * 获取当前评论所属图书
 * @return
 */
public Book getBook() {
	return book;
}
/**
 * 设置当前评论所属图书
 * @param book
 */
public void setBook(Book book) {
	this.book = book;
}

@Override
public String toString() {
	return "Remark [remarkid=" + remarkid + ", bookid=" + bookid + ", userid="
			+ userid + ", tradeItemid=" + tradeItemid + ", remark=" + remark
			+ ", remarkImgUrl=" + remarkImgUrl + ", remarkDate=" + remarkDate
			+ ", remarkLevel=" + remarkLevel + ", isShow=" + isShow + ", user="
			+ user + ", book=" + book + "]";
}

public Remark() {
	super();
}

public Remark(String remarkid,String bookid, String userid, String tradeItemid, String remark,
		String remarkImgUrl ,String remarkDate, int remarkLevel,int isShow) {
	super();
	this.remarkid = remarkid;
	this.bookid = bookid;
	this.userid = userid;
	this.tradeItemid = tradeItemid;
	this.remark = remark;
	this.remarkImgUrl = remarkImgUrl;
	this.remarkDate = remarkDate;
	this.remarkLevel = remarkLevel;
	this.isShow = isShow;
}
public Remark(String remarkid,String bookid, String userid, String tradeItemid, String remark,
		Blob firstRemarkImg,String remarkImgUrl,String remarkDate, int remarkLevel) {
	super();
	this.remarkid = remarkid;
	this.bookid = bookid;
	this.userid = userid;
	this.tradeItemid = tradeItemid;
	this.remark = remark;
	this.firstRemarkImg = firstRemarkImg;
	this.remarkImgUrl = remarkImgUrl;
	this.remarkDate = remarkDate;
	this.remarkLevel = remarkLevel;
}



}

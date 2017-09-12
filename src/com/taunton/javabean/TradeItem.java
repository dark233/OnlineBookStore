package com.taunton.javabean;

public class TradeItem {
private String itemid;
private String bookid;
private int quantity;
private String tradeid;
private float price;
private Book book;
/**
 * 获取订单条目id
 * @return
 */
public String getItemid() {
	return itemid;
}
/**
 * 设置订单条目id
 * @param itemid
 */
public void setItemid(String itemid) {
	this.itemid = itemid;
}
/**
 * 获取图书id
 * @return
 */
public String getBookid() {
	return bookid;
}
/**
 * 设置图书id
 * @param bookid
 */
public void setBookid(String bookid) {
	this.bookid = bookid;
}
/**
 * 获取该条图书条目的数量
 * @return
 */
public int getQuantity() {
	return quantity;
}
/**
 * 设置该条图书条目的数量
 * @param quantity
 */
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
/**
 * 获取该订单条目所属订单的id
 * @return
 */
public String getTradeid() {
	return tradeid;
}
/**
 * 设置该订单条目所属订单的id
 * @param tradeid
 */
public void setTradeid(String tradeid) {
	this.tradeid = tradeid;
}
/**
 * 获取该订单条目下图书的单价
 * @return
 */
public float getPrice() {
	return price;
}
/**
 * 设置该订单条目下图书的单价
 * @param price
 */
public void setPrice(float price) {
	this.price = price;
}
/**
 * 获取该订单条目上的图书
 * @return
 */
public Book getBook() {
	return book;
}
/**
 * 设置该订单条目上的图书
 * @param book
 */
public void setBook(Book book) {
	this.book = book;
}

@Override
public String toString() {
	return "TradeItem [itemid=" + itemid + ", bookid=" + bookid + ", quantity="
			+ quantity + ", tradeid=" + tradeid + ", price=" + price
		    + ", book=" + book + "]";
}
public TradeItem() {
	super();
}

public TradeItem(String itemid, String bookid, int quantity, String tradeid,float price) {
	super();
	this.itemid = itemid;
	this.bookid = bookid;
	this.quantity = quantity;
	this.tradeid = tradeid;
	this.price = price;
}




}

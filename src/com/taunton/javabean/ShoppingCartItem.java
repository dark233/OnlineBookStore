package com.taunton.javabean;

public class ShoppingCartItem {
private String itemid;
private String bookid;
private int quantity;
private String shoppingCartid;
private int isChecked;
private float addPrice;
private String createTime;
private String updateTime;
private Book book;
/**
 * 获取本购物车条目的id
 * @return
 */
public String getItemid() {
	return itemid;
}
/**
 * 设置本购物车条目的id
 * @param itemid
 */
public void setItemid(String itemid) {
	this.itemid = itemid;
}
/**
 * 获取本购物车条目指向图书的图书id
 * @return
 */
public String getBookid() {
	return bookid;
}
/**
 * 设置本购物车条目指向图书的图书id
 * @param bookid
 */
public void setBookid(String bookid) {
	this.bookid = bookid;
}
/**
 * 获取本购物车条目的图书数量
 * @return
 */
public int getQuantity() {
	return quantity;
}
/**
 * 设置本购物车条目的图书数量
 * @param quantity
 */
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
/**
 * 获取本购物车条目所属购物车的购物车id
 * @return
 */
public String getShoppingCartid() {
	return shoppingCartid;
}
/**
 * 设置本购物车条目所属购物车的购物车id
 * @param shoppingCartid
 */
public void setShoppingCartid(String shoppingCartid) {
	this.shoppingCartid = shoppingCartid;
}
/**
 * 获取本购物车条目是否被选中的状态（1表示true，2表示false）
 * @return
 */
public int getIsChecked() {
	return isChecked;
}
/**
 * 设置本购物车条目是否被选中的状态（1表示true，2表示false）
 * @param isChecked
 */
public void setIsChecked(int isChecked) {
	this.isChecked = isChecked;
}
/**
 * 获取购物车项加入购物车时价格
 * @return
 */
public float getAddPrice() {
	return addPrice;
}
/**
 * 设置购物车项加入购物车时价格
 * @param addPrice
 */
public void setAddPrice(float addPrice) {
	this.addPrice = addPrice;
}
/**
 * 获取购物车项创建时间
 * @return
 */
public String getCreateTime() {
	return createTime;
}
/**
 * 设置购物车项创建时间
 * @param createTime
 */
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
/**
 * 获取购物车项最后更新时间
 * @return
 */
public String getUpdateTime() {
	return updateTime;
}
/**
 * 设置购物车项最后更新时间
 * @param updateTime
 */
public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
}
/**
 * 获取本购物车条目下的图书对象
 * @return
 */
public Book getBook() {
	return book;
}
/**
 * 设置本购物车条目下的图书对象
 * @param book
 */
public void setBook(Book book) {
	this.book = book;
}



@Override
public String toString() {
	return "ShoppingCartItem [itemid=" + itemid + ", bookid=" + bookid
			+ ", quantity=" + quantity + ", shoppingCartid=" + shoppingCartid
			+ ", isChecked=" + isChecked + ", addPrice=" + addPrice
			+ ", createTime=" + createTime + ", updateTime=" + updateTime
			+ ", book=" + book + "]";
}
public ShoppingCartItem() {
	super();
}
public ShoppingCartItem(String itemid, String bookid, int quantity,
		String shoppingCartid, int isChecked, float addPrice, String createTime,String updateTime) {
	super();
	this.itemid = itemid;
	this.bookid = bookid;
	this.quantity = quantity;
	this.shoppingCartid = shoppingCartid;
	this.isChecked = isChecked;
	this.addPrice = addPrice;
	this.createTime = createTime;
	this.updateTime = updateTime;
}



}

package com.taunton.javabean;

import java.util.LinkedHashSet;
import java.util.Set;

public class ShoppingCart {
private String shoppingCartid;
private String userid;
private float shoppingCartMoney;
private int itemsNum;
private Set<ShoppingCartItem> shopppingCartItems = new LinkedHashSet<ShoppingCartItem>();
/**
 * 获取购物车id
 * @return
 */
public String getShoppingCartid() {
	return shoppingCartid;
}
/**
 * 设置购物车id
 * @param shoppingCartid
 */
public void setShoppingCartid(String shoppingCartid) {
	this.shoppingCartid = shoppingCartid;
}
/**
 * 获取购物车所属用户的id
 * @return
 */
public String getUserid() {
	return userid;
}
/**
 * 设置购物车所属用户的id
 * @param userid
 */
public void setUserid(String userid) {
	this.userid = userid;
}
/**
 * 获取购物车总金额
 * @return
 */
public float getShoppingCartMoney() {
	return shoppingCartMoney;
}
/**
 * 设置购物车总金额
 * @param shoppingCartMoney
 */
public void setShoppingCartMoney(float shoppingCartMoney) {
	this.shoppingCartMoney = shoppingCartMoney;
}
/**
 * 获取购物车内购物车条目数量
 * @return
 */
public int getItemsNum() {
	return itemsNum;
}
/**
 * 设置购物车内购物车条目数量
 * @param itemsNum
 */
public void setItemsNum(int itemsNum) {
	this.itemsNum = itemsNum;
}
/**
 * 获取购物车条目集合对象（Set集合）
 * @return
 */
public Set<ShoppingCartItem> getShopppingCartItems() {
	return shopppingCartItems;
}
/**
 * 设置购物车条目集合对象
 * @param shopppingCartItems
 */
public void setShopppingCartItems(Set<ShoppingCartItem> shopppingCartItems) {
	this.shopppingCartItems = shopppingCartItems;
}

@Override
public String toString() {
	return "ShoppingCart [shoppingCartid=" + shoppingCartid + ", userid="
			+ userid + ", shoppingCartMoney=" + shoppingCartMoney
			+ ", itemsNum=" + itemsNum + ", shopppingCartItems="
			+ shopppingCartItems + "]";
}
public ShoppingCart() {
	super();
}

public ShoppingCart(String shoppingCartid,String userid, float shoppingCartMoney,int itemsNum) {
	super();
	this.shoppingCartid = shoppingCartid;
	this.userid = userid;
	this.shoppingCartMoney = shoppingCartMoney;
	this.itemsNum = itemsNum;
}

/**
 * ---------------------------------------分割线---------------------------------------------------------------------以下为旧版本内容，仅为改版提供参考
 * ---------------------------------------分割线---------------------------------------------------------------------
 */

//private  Map<Integer, ShoppingCartItem> shoppingCart = new HashMap<Integer,ShoppingCartItem>();
//
///**
// * 获取书籍条目的集合的value
// * @return
// */
//public List<ShoppingCartItem> getShoppingCartItems() {
//	List<ShoppingCartItem> list = new ArrayList<ShoppingCartItem>();
//	for(Entry<Integer, ShoppingCartItem> e:shoppingCart.entrySet()){
//		list.add(e.getValue());
//	}
//	return list;
//}
///**
// * 获取书籍条目的集合
// * @return
// */
//public Map<Integer, ShoppingCartItem> getShoppingCart(){
//	return shoppingCart;
//}
///**
// * 获取书籍条目的数量
// * @return
// */
//public int getShoppingCartItemsNum(){
//	return shoppingCart.size();
//}
///**
// * 获取购物车书籍总金额
// * @return
// */
//public float getTotalMoney(){
//	float totalMoney=0;
//	for (Entry<Integer, ShoppingCartItem> sc : shoppingCart.entrySet()) {
//		totalMoney+=sc.getValue().getMoney();
//	}
//	return totalMoney;
//}
///**
// * 获取购物车书籍总数量
// * @return
// */
//public int getTotalQuantity(){
//	int totalQuantity = 0;
//	for(Entry<Integer, ShoppingCartItem> sc:shoppingCart.entrySet()){
//		totalQuantity+=sc.getValue().getQuantity();
//	}
//	return totalQuantity;
//	
//}
///**
// * 根据key获取购物车内书籍条目
// * @param itemId
// * @return
// */
//public ShoppingCartItem getShoppingCartItem(int key){
//	ShoppingCartItem shoppingCartItem = shoppingCart.get(key);
//	return shoppingCartItem;
//}
///**
// * 购物车内是否存在此条书籍条目
// * @param key
// * @return
// */
//public boolean hasShoppingCartItem(int key){
//	boolean hi =  shoppingCart.containsKey(key);
//	return hi;
//	
//}
///**
// * 添加key对应的图书条目
// * @param key
// * @param sci
// */
//public void setShoppingCartItem(int key,ShoppingCartItem sci){
//	shoppingCart.put(key, sci);
//}
///**
// * 删除key对应的图书条目
// * @param key
// */
//public void deleteShoppingCartItem(int key){
//	shoppingCart.remove(key);
//}
///**
// * 清空购物车
// */
//public void clearShoppingCart(){
//	shoppingCart.clear();
//}
}

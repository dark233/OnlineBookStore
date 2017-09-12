package com.taunton.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.taunton.javabean.ShoppingCartItem;
import com.taunton.javabean.TradeItem;

public interface ShoppingCartItemDAO {
	/**
	 * 添加购物车项
	 * @param shoppingCartItem
	 * @return
	 */
	public void addShoppingCartItem(ShoppingCartItem shoppingCartItem) throws Exception;
	/**
	 * 根据购物车项id删除购物车项
	 * @param shoppingCartItemId
	 */
	public void deleteShoppingCartItemWithShoppingCartItemId(String shoppingCartItemId) throws Exception;
	/**
	 * 根据购物车id删除购物车项
	 * @param shoppingCartItemId
	 */
	public void deleteShoppingCartItemWithShoppingCartId(String shoppingCartId) throws Exception;
	/**
	 * 根据购物车id及购物车项是否被选中来删除购物车项
	 * @param shoppingCartId
	 */
	public void deleteShopppingCartItemWithisCheckedAndShoppingCartId(String shoppingCartId,Integer isChecked) throws Exception;
	/**
	 * 根据传入的TradeItem集合批量删除购物车项
	 * @param tradeItems
	 * @param s
	 * @throws SQLException
	 */
	public void batchDeleteItem(Collection<TradeItem> tradeItems,String shoppingCartId) throws SQLException ;
	/**
	 * 根据购物车项id更新购物车项图书数量
	 * @param shoppingCartItem
	 */
	public void updateQuantityAndUpdateTimeWithShoppingCartItemId(Integer quantity,String updateTime,String shoppingCartItemId) throws Exception;
	/**
	 * 根据图书id和购物车id更新购物车项图书数量
	 * @param quantity
	 * @param bookId
	 */
	public void updateQuantityAndUpdateTimeWithBookIdAndShoppingCartId(Integer quantity,String updateTime,String bookId,String shoppingCartId) throws Exception;
	
	/**
	 * 根据购物车项id更新购物车项选定状态
	 * @param shoppingCartItem
	 */
	public void updateisCheckedWithShoppingCartItemId(Integer isChecked,String updateTime,String shoppingCartItemId) throws Exception;
	/**
	 * 根据购物车id更新购物车项选定状态
	 * @param shoppingCartId
	 */
	public void updateisCheckedWithShoppingCartId(Integer isChecked,String updateTime,String shoppingCartId) throws Exception;
	/**
	 * 更新购物车项最新更新时间根据购物车项id
	 * @param updateTime
	 * @param shoppingCartItemId
	 * @throws Exception
	 */
	public void updateupdateTimeWithShoppingCartItemId(String updateTime,String shoppingCartItemId) throws Exception;
	/**
	 * 更新购物车项最新更新时间根据购物车id
	 * @param updateTime
	 * @param shoppingCartId
	 * @throws Exception
	 */
	public void updateUpdateTimeWithShoppingCartId(String updateTime,String shoppingCartId) throws Exception;
	/**
	 * 根据传入的TradeItem集合批量更改购物车项的数量
	 * @param tradeItems
	 * @param s
	 * @throws SQLException
	 */
	public void batchUpdateQuantity(Collection<TradeItem> tradeItems,String shoppingCartId) throws SQLException ;
	/**
	 * 根据传入的购物车id来获取购物车项的数量
	 * @param ShoppingCartId
	 * @return
	 * @throws Exception
	 */
	public int getShoppingCartItemsNumWithShoppingCartIdAndIsChecked(String shoppingCartId,int isChecked) throws Exception;
	/**
	 * 根据购物车id来获取购物车总金额
	 * @param shoppingCartId
	 * @return
	 * @throws Exception
	 */
	public float getShoppingCartTotalMoneyWithShoppingCartId(String shoppingCartId) throws Exception;
	/**
	 * 根据图书id和购物车id来获取购物车项
	 * @param bookid
	 * @return
	 */
	public ShoppingCartItem getShoppingCartItemWithBookIdAndShoppingCartId(String bookid,String shoppingCartId) throws Exception;
	/**
	 * 根据购物车项id来获取购物车项
	 * @param shoppingCartItemId
	 * @return
	 * @throws Exception
	 */
	public ShoppingCartItem getShoppingCartItemWithShoppingCartItemId(String shoppingCartItemId) throws Exception;
	/**
	 * 根据购物车id获取购物车项集合
	 * @param ShoppingCartId
	 * @return
	 */
	public Set<ShoppingCartItem> getShoppingCartItemWithShoppingCartId(String shoppingCartId) throws Exception;
	/**
	 * 根据购物车id及购物车项是否被选中来获取购物车项集合
	 * @param shoppingCartId
	 * @return
	 */
	public Set<ShoppingCartItem> getShoppingCartItemWithisCheckedAndShoppingCartId(String shoppingCartId,Integer isChecked) throws Exception;
	
	
}

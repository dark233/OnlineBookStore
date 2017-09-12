package com.taunton.dao;

import com.taunton.javabean.ShoppingCart;

public interface ShoppingCartDAO {
	/**
	 * 添加购物车
	 * @param shoppingCart
	 * @return
	 */
	public void addShoppingCart(ShoppingCart shoppingCart) throws Exception;
	/**
	 * 根据购物车id更新购物车所属用户id
	 * @param shoppingCart
	 */
	public void updateUserIdWithShoppingCartId(ShoppingCart shoppingCart) throws Exception;
	/**
	 * 根据购物车id更新购物车总金额
	 * @param shoppingCart
	 */
	public void updateShoppingCartMoneyWithShoppingCartId(Float shoppingCartMoney,String shoppingCartId) throws Exception;
	/**
	 * 根据购物车id更新购物车条目数
	 * @param itemsNum
	 * @param shoppingCartId
	 */
	public void updateShoppingItemsNumWithShoppingCartId(Integer itemsNum,String shoppingCartId) throws Exception;
	/**
	 * 根据用户id获取购物车
	 * @param userid
	 * @return
	 */
	public ShoppingCart getShoppingCartWithUserId(String userid) throws Exception;
	/**
	 * 根据购物车id获取购物车
	 * @param shoppingCartId
	 * @return
	 */
	public ShoppingCart getShoppingCartWithShoppingCartId(String shoppingCartId) throws Exception;
	
}

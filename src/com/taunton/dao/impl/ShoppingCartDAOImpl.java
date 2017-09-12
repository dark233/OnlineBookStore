package com.taunton.dao.impl;

import java.sql.SQLException;

import com.taunton.dao.ShoppingCartDAO;
import com.taunton.javabean.ShoppingCart;

public class ShoppingCartDAOImpl extends BaseDAO<ShoppingCart> implements ShoppingCartDAO {

	@Override
	public void addShoppingCart(ShoppingCart shoppingCart) throws SQLException {
		String sql = "INSERT INTO shoppingCart(shoppingcartid,userid,shoppingCartMoney,itemsnum) VALUES(?,?,?,?)";
		Object[] obj = {shoppingCart.getShoppingCartid(),shoppingCart.getUserid(),shoppingCart.getShoppingCartMoney(),shoppingCart.getItemsNum()};
		update(sql, obj);
	}

	@Override
	public void updateUserIdWithShoppingCartId(ShoppingCart shoppingCart) throws SQLException {
		String sql = "UPDATE shoppingcart SET userid = ? WHERE shoppingCartid = ?";
		Object[] obj = {shoppingCart.getUserid(),shoppingCart.getShoppingCartid()};
		update(sql, obj);
	}

	@Override
	public void updateShoppingCartMoneyWithShoppingCartId(
			Float shoppingCartMoney,String shoppingCartId) throws SQLException {
		String sql = "UPDATE shoppingcart SET shoppingcartmoney = ? WHERE shoppingcartid = ?";
		Object[] obj = {shoppingCartMoney,shoppingCartId};
		update(sql, obj);
	}
	
	@Override
	public void updateShoppingItemsNumWithShoppingCartId(Integer itemsNum,
			String shoppingCartId) throws SQLException {
		String sql = "UPDATE shoppingcart SET itemsNum = ? WHERE shoppingcartid = ?";
		Object[] obj = {itemsNum,shoppingCartId};
		update(sql, obj);
	}

	@Override
	public ShoppingCart getShoppingCartWithUserId(String userid) throws SQLException {
		String sql = "SELECT shoppingCartid, userid, shoppingCartMoney,itemsNum FROM shoppingcart WHERE userid =  ?";
		return query(sql, userid);
	}

	@Override
	public ShoppingCart getShoppingCartWithShoppingCartId(String shoppingCartId) throws SQLException {
		String sql = "SELECT shoppingCartid,userid,shoppingCartMoney,itemsNum FROM shoppingcart　WHERE shoppingcartid = ? ";
		ShoppingCart shoppingCart = query(sql, shoppingCartId);
		return shoppingCart;
	}

	

}

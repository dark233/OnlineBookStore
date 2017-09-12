package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.taunton.dao.ShoppingCartItemDAO;
import com.taunton.javabean.ShoppingCartItem;
import com.taunton.javabean.TradeItem;

public class ShoppingCartItemDAOImpl extends BaseDAO<ShoppingCartItem> implements
		ShoppingCartItemDAO {

	@Override
	public void addShoppingCartItem(ShoppingCartItem shoppingCartItem) throws SQLException {
		String sql = "INSERT INTO shoppingcartitem(itemid,bookid,quantity,shoppingCartid,isChecked,addPrice,createTime,updateTime) VALUES(?,?,?,?,?,?,?,?)";
		Object[] obj = {shoppingCartItem.getItemid(),shoppingCartItem.getBookid(),shoppingCartItem.getQuantity(),shoppingCartItem.getShoppingCartid()
				,shoppingCartItem.getIsChecked(),shoppingCartItem.getAddPrice(),shoppingCartItem.getCreateTime(),shoppingCartItem.getUpdateTime()};
		update(sql, obj);
	}

	@Override
	public void deleteShoppingCartItemWithShoppingCartItemId(
			String shoppingCartItemId) throws SQLException {
		String sql = "DELETE FROM shoppingcartitem WHERE itemid = ?";
		update(sql, shoppingCartItemId);
	}

	@Override
	public void deleteShoppingCartItemWithShoppingCartId(String shoppingCartId) throws SQLException {
		String sql = "DELETE FROM shoppingcartitem WHERE shoppingcartid = ?";
		update(sql, shoppingCartId);
	}

	@Override
	public void deleteShopppingCartItemWithisCheckedAndShoppingCartId(String shoppingCartId,Integer isChecked) throws SQLException {
		String sql = "DELETE FROM shoppingcartitem WHERE shoppingcartid = ? AND ischecked = ?";
		Object[] obj = {shoppingCartId,isChecked};
		update(sql, obj);
	}

	@Override
	public void updateQuantityAndUpdateTimeWithShoppingCartItemId(
			Integer quantity,String updateTime,String shoppingCartItemId) throws SQLException {
		String sql = "UPDATE shoppingcartitem SET quantity = ?,updateTime = ? WHERE itemid = ?";
		Object[] obj = {quantity,updateTime,shoppingCartItemId};
		update(sql, obj);
	}
	
	@Override
	public void updateQuantityAndUpdateTimeWithBookIdAndShoppingCartId(Integer quantity,String updateTime,String bookId,String shoppingCartId) throws SQLException {
		String sql = "UPDATE shoppingcartitem SET quantity = quantity+?,updateTime = ? WHERE bookid = ? AND shoppingcartid = ?";
		Object[] obj = {quantity,updateTime,bookId,shoppingCartId};
		update(sql, obj);
	}

	@Override
	public void updateisCheckedWithShoppingCartItemId(
			Integer isChecked,String updateTime,String shoppingCartItemId) throws SQLException {
		String sql = "UPDATE shoppingcartitem SET ischecked = ? WHERE itemid = ?";
		Object[] obj = {isChecked,shoppingCartItemId};
		update(sql, obj);
//		updateupdateTimeWithShoppingCartItemId(updateTime, shoppingCartItemId);
	}
	
	@Override
	public void updateisCheckedWithShoppingCartId(Integer isChecked,String updateTime,String shoppingCartId) throws SQLException {
		String sql = null;
//		if(isChecked == 1){
//			 sql = "UPDATE shoppingcartitem,book SET ischecked = ? WHERE shoppingcartitem.bookid=book.id AND book.isShow=1 AND book.Storenumber>0 AND shoppingcartid = ?";
//		}else{
//			 sql = "UPDATE shoppingcartitem,book SET ischecked = ? WHERE shoppingcartitem.bookid=book.id AND shoppingcartid = ?";
//		}
//		直接在结算时统一校验比较好
		sql = "UPDATE shoppingcartitem,book SET ischecked = ? WHERE shoppingcartitem.bookid=book.id AND book.isShow = 1 AND book.storeNumber >0 AND shoppingcartitem.shoppingcartid = ?";
		Object[] obj = {isChecked,shoppingCartId};
		update(sql, obj);
//		updateUpdateTimeWithShoppingCartId(updateTime, shoppingCartId);
	}
	
	@Override
	public void updateupdateTimeWithShoppingCartItemId(String updateTime,
			String shoppingCartItemId) throws SQLException {
		String sql = "UPDATE shoppingcartitem SET updateTime = ? WHERE itemid = ?";
		Object[] obj = {updateTime,shoppingCartItemId};
		update(sql, obj);
	}

	@Override
	public void updateUpdateTimeWithShoppingCartId(String updateTime,
			String shoppingCartId) throws SQLException {
		String sql = "UPDATE shoppingcartitem,book SET shoppingcartitem.updateTime = ? WHERE shoppingcartitem.bookid=book.id AND book.isShow=1 AND book.Storenumber>0 AND shoppingcartid = ?";
		Object[] obj = {updateTime,shoppingCartId};
		update(sql, obj);
	}

	
	@Override
	public void batchUpdateQuantity(Collection<TradeItem> tradeItems,String shoppingCartId)
			throws SQLException {
		String sql = null;
		sql = "UPDATE shoppingcartitem SET quantity = quantity - ? WHERE shoppingcartid = ? AND bookid = ?";
		
		List<TradeItem> list = new ArrayList<>(tradeItems);
		Object [][] obj = new Object[tradeItems.size()][3];
		for(int i = 0;i<list.size();i++){
			obj[i][0] = list.get(i).getQuantity();
			obj[i][1] = shoppingCartId;
			obj[i][2] = list.get(i).getBookid();
		}
		batch(sql, obj);
	}
	
	@Override
	public void batchDeleteItem(Collection<TradeItem> tradeItems,
			String shoppingCartId) throws SQLException {
		String sql = null;
		sql = "DELETE FROM shoppingcartitem  WHERE shoppingcartid = ? AND bookid = ?";
		
		List<TradeItem> list = new ArrayList<>(tradeItems);
		Object [][] obj = new Object[tradeItems.size()][2];
		for(int i = 0;i<list.size();i++){
			obj[i][0] = shoppingCartId;
			obj[i][1] = list.get(i).getBookid();
		}
		batch(sql, obj);
	}
	
	@Override
	public ShoppingCartItem getShoppingCartItemWithBookIdAndShoppingCartId(String bookid,String shoppingCartId) throws SQLException {
		String sql = "SELECT itemid,bookid,quantity,shoppingCartid,isChecked,addPrice FROM shoppingcartitem WHERE bookid = ? AND shoppingcartid = ?";
		Object[] obj = {bookid,shoppingCartId};
		return query(sql, obj); 
	}
	
	@Override
	public ShoppingCartItem getShoppingCartItemWithShoppingCartItemId(
			String shoppingCartItemId) throws Exception {
		String sql = "SELECT itemid,bookid,quantity,shoppingCartid,isChecked,addPrice FROM shoppingcartitem WHERE itemid = ?";
		return query(sql, shoppingCartItemId); 
	}
	
	@Override
	public Set<ShoppingCartItem> getShoppingCartItemWithShoppingCartId(
			String shoppingCartId) throws SQLException {
		String sql = "SELECT itemid,bookid,quantity,shoppingCartid,isChecked,addPrice FROM shoppingcartitem WHERE shoppingcartid = ? "
				+ "ORDER BY updateTime DESC";
		return new LinkedHashSet<>(queryList(sql, shoppingCartId));
	}

	@Override
	public Set<ShoppingCartItem> getShoppingCartItemWithisCheckedAndShoppingCartId(
			String shoppingCartId,Integer isChecked) throws SQLException {
		String sql = "SELECT itemid,bookid,quantity,shoppingCartid,isChecked,addPrice FROM shoppingcartitem WHERE shoppingcartid = ? AND "
				+ "ischecked = COALESCE(?,ischecked) ORDER BY updateTime DESC";
		Object[] obj = {shoppingCartId,isChecked};
		return new LinkedHashSet<>(queryList(sql, obj));
		
	}

	@Override
	public float getShoppingCartTotalMoneyWithShoppingCartId(
			String shoppingCartId) throws Exception {
		String sql = "SELECT SUM(a.quantity*b.currprice) FROM shoppingcartitem a,book b WHERE a.bookid=b.id AND a.ischecked = 1 AND a.shoppingcartid = ?";
		Double totalMoney1 = querySingle(sql, shoppingCartId);
		if(totalMoney1==null){
			double d = 0 ;
			totalMoney1 = d ;
		}
		double totalMoney2 = totalMoney1;
		float totalMoney = (float) totalMoney2;
		return totalMoney;
	}

	@Override
	public int getShoppingCartItemsNumWithShoppingCartIdAndIsChecked(String shoppingCartId,int isChecked)
			throws Exception {
		String sql = "SELECT COUNT(itemid) FROM shoppingcartitem WHERE shoppingcartid = ? AND isChecked = ?";
		Object[] obj = {shoppingCartId,isChecked};
		Long l1 = querySingle(sql,obj);
		long l2 = l1;
		int itemsNum = (int) l2;
		return itemsNum;
	}







	


	

}

package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import com.taunton.dao.impl.BookDAOImpl;
import com.taunton.dao.impl.ShippingAddressDAOImpl;
import com.taunton.dao.impl.ShoppingCartDAOImpl;
import com.taunton.dao.impl.ShoppingCartItemDAOImpl;
import com.taunton.dao.impl.TradeDAOImpl;
import com.taunton.dao.impl.TradeItemDAOImpl;
import com.taunton.javabean.Book;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.ShoppingCartItem;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;

public class ShoppingCartService {

	private static Logger logger = Logger.getLogger(ShoppingCartService.class); 
	private ShoppingCartDAOImpl scd = null;
	private ShoppingCartItemDAOImpl scid = null;
	private BookDAOImpl bd = null;
	public ShoppingCartService(){
		scd = DomainFactory.createDomainInstance(ShoppingCartDAOImpl.class);
		scid = DomainFactory.createDomainInstance(ShoppingCartItemDAOImpl.class);
		bd = DomainFactory.createDomainInstance(BookDAOImpl.class);
	}
	
	/**
	 * 获取选定的购物车项组成的购物车
	 * @param userid
	 * @return
	 */
//	public ShoppingCart findPreparShoppingCart(String userid,String shoppingCartid){
//		ShoppingCart sc2 = null;
//		Set<ShoppingCartItem> sci = null;
//		try {
			//进行购物车项检查
//			checkShoppingCartItems(shoppingCartid,1);
//			updateShoppingCartMoney(shoppingCartid);
//			updateShoppingCartItemsNum(shoppingCartid);2013-08-08 08：08：08
//			sci = scid.getShoppingCartItemWithisCheckedAndShoppingCartId(shoppingCartid, 1);
//			for (ShoppingCartItem shoppingCartItem : sci) {
//				Book book = bd.getBook(shoppingCartItem.getBookid());
//				shoppingCartItem.setBook(book);
//			}
//			sc2 = scd.getShoppingCartWithUserId(userid);
//			sc2.setShopppingCartItems(sci);
//		} catch (Exception e) {
//			logger.error("获取购物车失败！"+e);
//			throw new RuntimeException(e);
//		}
//		return sc2;
//	}
	
	/**
	 * 通过用户id获取用户id下的完整购物车
	 * @param userid
	 */
	public ShoppingCart findShoppingCartWithUserid(String userid){
//		ShoppingCart sc1 = null;
		ShoppingCart sc2 = null;
//		Set<ShoppingCartItem> sci = null;
		try {
//			sc1 = scd.getShoppingCartWithUserId(userid);
//			String shoppingCartid = sc1.getShoppingCartid();
			//进行购物车项检查
//			checkShoppingCartItems(shoppingCartid,2);
//			updateShoppingCartMoney(shoppingCartid);
//			updateShoppingCartItemsNum(shoppingCartid);
//			sci = scid.getShoppingCartItemWithShoppingCartId(shoppingCartid);
//			if(sci.size()==0){
//				return null;
//			}
//			for (ShoppingCartItem shoppingCartItem : sci) {
//				Book book = bd.getBook(shoppingCartItem.getBookid());
//				shoppingCartItem.setBook(book);
//			}
			sc2 = scd.getShoppingCartWithUserId(userid);
//			sc2.setShopppingCartItems(sci);
		} catch (Exception e) {
			logger.error("获取购物车失败！"+e);
			throw new RuntimeException(e);
		}
		return sc2;
	}
	
	/**
	 * 根据购物车id及isChecked获取购物车项集合。isChecked为null时获取购物车id下的所有购物车项
	 * @param shoppingCartid
	 * @param isChecked
	 * @return
	 */
	public Set<ShoppingCartItem> findShoppingCartItemsByShoppingCartidAndIsChecked(String shoppingCartid,Integer isChecked){
		Set<ShoppingCartItem> sci = null;
		try {
			sci = scid.getShoppingCartItemWithisCheckedAndShoppingCartId(shoppingCartid, isChecked);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return sci;
	}
	
	
	/**
	 * 添加商品到购物车
	 * @param sc
	 * @param count
	 * @param bookid
	 * @throws Exception 
	 */
	public boolean addToShoppingCart(int count,String bookid,Float currPrice,String shoppingCartid){
			try {
				ShoppingCartItem sci = scid.getShoppingCartItemWithBookIdAndShoppingCartId(bookid,shoppingCartid);
				String currTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
				if(sci==null){
					String shoppingCartItemid = shoppingCartid+UUIDUtils.getUUID(4);
					ShoppingCartItem sci1 = new ShoppingCartItem(shoppingCartItemid, bookid, count, shoppingCartid, 1, currPrice, currTime,currTime);
					scid.addShoppingCartItem(sci1); 
					updateShoppingCartMoney(shoppingCartid);
					updateShoppingCartItemsNum(shoppingCartid);
				}else{
					scid.updateQuantityAndUpdateTimeWithBookIdAndShoppingCartId(count,currTime, bookid, shoppingCartid);
					updateShoppingCartMoney(shoppingCartid);
				}
			} catch (Exception e) {
				logger.error("添加商品到购物车出现异常"+e);
				throw new RuntimeException(e);
			}
		return true;
	}
	/**
	 * 修改购物车项数量
	 * @param count
	 * @param shoppingCartItemid
	 * @param shoppingCartid
	 * @return
	 */
	public boolean updateQuantityOfShoppingCart(int count,String shoppingCartItemid,String shoppingCartid){
		String currTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		try {
			scid.updateQuantityAndUpdateTimeWithShoppingCartItemId(count, currTime, shoppingCartItemid);
			updateShoppingCartMoney(shoppingCartid);
		} catch (Exception e) {
			logger.error("修改购物车数量异常"+e);
			throw new RuntimeException(e);
		};
		return true;
	}
	/**
	 * 从购物车里删除选中记录
	 * @param sc
	 * @param bookid
	 * @throws Exception 
	 */
	public boolean deleteShoppingCartItems(String shoppingCartid){
		try {
			scid.deleteShopppingCartItemWithisCheckedAndShoppingCartId(shoppingCartid, 1);
			updateShoppingCartMoney(shoppingCartid);
			updateShoppingCartItemsNum(shoppingCartid);
		}catch (Exception e) {
			logger.error("从购物车删除选中记录失败"+e);
			throw new RuntimeException(e);
		}
		return true;
	}
	/**
	 * 从购物车里删除一条记录
	 * @param sc
	 * @param bookid
	 * @throws Exception 
	 */
	public boolean deleteOneShoppingCartItem(String shoppingCartItemid,
			String shoppingCartid) {
		try {
			scid.deleteShoppingCartItemWithShoppingCartItemId(shoppingCartItemid);
			updateShoppingCartMoney(shoppingCartid);
			updateShoppingCartItemsNum(shoppingCartid);
		}catch (Exception e) {
			logger.error("从购物车删除一条记录失败"+e);
			throw new RuntimeException(e);
		}
		return true;
	}
	/**
	 * 清空购物车
	 * @param sc
	 * @throws Exception 
	 */
	public boolean clearShoppingCart(String shoppingCartid){
		try {
			scid.deleteShoppingCartItemWithShoppingCartId(shoppingCartid);
			updateShoppingCartMoney(shoppingCartid);
			updateShoppingCartItemsNum(shoppingCartid);
		}catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
	}
	/**
	 * 更新购物车总金额
	 * @param shoppingCartid
	 * @return
	 * @throws Exception 
	 */
	public void updateShoppingCartMoney(String shoppingCartid){
			try {
				float totalMoney = scid.getShoppingCartTotalMoneyWithShoppingCartId(shoppingCartid);
				scd.updateShoppingCartMoneyWithShoppingCartId(totalMoney, shoppingCartid);
			} catch (Exception e) {
				logger.error(e);
				throw new RuntimeException(e);
			}
	}
	/**
	 * 更新购物车购物车项条目数
	 * @param shoppingCartid
	 * @throws Exception 
	 */
	public void updateShoppingCartItemsNum(String shoppingCartid){
			try {
				int itemsNum = scid.getShoppingCartItemsNumWithShoppingCartIdAndIsChecked(shoppingCartid,1);
				scd.updateShoppingItemsNumWithShoppingCartId(itemsNum, shoppingCartid);
			} catch (Exception e) {
				logger.error(e);
				throw new RuntimeException(e);
			}
	}
	/**
	 * 更新单项购物车项选定状态
	 * @param shoppingCartItemid
	 * @param isChecked
	 * @param shoppingCartid
	 */
	public boolean updateisCheckedWithShoppingCartItemId(String shoppingCartItemid,Integer isChecked,String shoppingCartid){
		String currTime = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_1);
		try {
			scid.updateisCheckedWithShoppingCartItemId(isChecked,currTime, shoppingCartItemid);
			updateShoppingCartMoney(shoppingCartid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
	}
	/**
	 * 修改整个购物车购物车项选定状态
	 * @param shoppingCartid
	 * @param isChecked
	 */
	public boolean updateisCheckedWithShoppingCartId(String shoppingCartid,Integer isChecked){
		String currTime = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_1);
		try {
			scid.updateisCheckedWithShoppingCartId(isChecked,currTime, shoppingCartid);
			this.updateShoppingCartMoney(shoppingCartid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
	}
	/**
	 * 及时检查更新购物车项的选定状态
	 * 可以给本方法public后面加sychronized static 然后用类名直接调用，等同于给整个方法加上了同一把锁(这里的ShoppingCartItemDAO对象不是静态变量，所以这个在这方式里不可用)
	 * @param shoppingCartid
	 * @param isCheckChecked 只检查购物车里的选定项(1),检查整个购物车(2)
	 * @throws SQLException 
	 */
	public String checkShoppingCartItems(String shoppingCartid,int isCheckChecked) {
		String isExist = null;
		int flag = 0;
		String currTime = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_1);
		synchronized (BookService.class) {
			try {
				Set<ShoppingCartItem> sci = null;
					if(isCheckChecked==1){
						sci = scid.getShoppingCartItemWithisCheckedAndShoppingCartId(shoppingCartid, 1);
					}else if(isCheckChecked==2){
						sci = scid.getShoppingCartItemWithShoppingCartId(shoppingCartid);
					}else{
						return null;
					}
					for (ShoppingCartItem shoppingCartItem : sci) {
						Book book = bd.getBook(shoppingCartItem.getBookid());
						if (book.getIsShow() == 2) {
							scid.updateisCheckedWithShoppingCartItemId(2,currTime, shoppingCartItem.getItemid());
							if(flag!=1){
								isExist = "结算图书中含下架图书";
							}
							flag = 1;
						} else if (book.getStoreNumber() == 0) {
							scid.updateisCheckedWithShoppingCartItemId(2,currTime, shoppingCartItem.getItemid());
							if(flag != 1 && flag != 2){
								isExist = "结算图书中含无货图书";
							}
							flag = 2;
						} else if (book.getStoreNumber() < shoppingCartItem.getQuantity()) {
							if(flag == 0){
								isExist = "结算图书中含库存不足图书";
							}
							flag = 3;
						}
					}
			} catch (SQLException e) {
				logger.error(e);
				throw new RuntimeException(e);
			}

		}
//		if(flag==1){
//			isExist = "您选择的图书已下架";
//		}else if(flag == 2){
//			isExist = "您选择的图书无货";
//		}else if(flag == 3){
//			isExist = "您选择的商品库存不足";
//		}
		return isExist;
	}
	/**
	 * 获取一个购物车项通过图书id和购物车id
	 * @param bookid
	 * @param shoppingCartid
	 * @return
	 */
	public ShoppingCartItem findShoppingCartItemWithBookidAndShoppingCartId(String bookid,String shoppingCartid){
		ShoppingCartItem sci = null;
		try {
			 sci = scid.getShoppingCartItemWithBookIdAndShoppingCartId(bookid, shoppingCartid);
//			 if(sci != null){
//				 Book book = bd.getBook(sci.getBookid());
//				 sci.setBook(book);
//			 }
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return sci;
		
	}
	/**
	 * 获取一个购物车项通过购物车项id
	 * @param shoppingCartItemid
	 * @return
	 */
	public ShoppingCartItem findShoppingCartItemWithItemid(String shoppingCartItemid){
		ShoppingCartItem sci = null;
		try {
			sci = scid.getShoppingCartItemWithShoppingCartItemId(shoppingCartItemid);
//			Book book = bd.getBook(sci.getBookid());
//			sci.setBook(book);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return sci;
		
	}
	
}

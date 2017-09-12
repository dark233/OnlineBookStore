package com.taunton.service;

import java.security.DomainCombiner;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.taunton.dao.impl.BookDAOImpl;
import com.taunton.dao.impl.HandleDAOImpl;
import com.taunton.dao.impl.RemarkDAOImpl;
import com.taunton.dao.impl.TradeDAOImpl;
import com.taunton.dao.impl.TradeItemDAOImpl;
import com.taunton.javabean.Book;
import com.taunton.javabean.Handle;
import com.taunton.javabean.Remark;
import com.taunton.javabean.ShippingAddress;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.ShoppingCartItem;
import com.taunton.javabean.Trade;
import com.taunton.javabean.TradeItem;
import com.taunton.javabean.User;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.web.CriteriaTrade;
import com.taunton.web.Page;

public class TradeService {

	private static Logger logger = Logger.getLogger(TradeService.class); 
	private TradeDAOImpl td = null;
	private TradeItemDAOImpl tid = null;
	private BookDAOImpl bd = null;
	private HandleDAOImpl hd = null;
	private RemarkDAOImpl rd = null;
	public TradeService(){
		td = DomainFactory.createDomainInstance(TradeDAOImpl.class);
		tid = DomainFactory.createDomainInstance(TradeItemDAOImpl.class);
		bd = DomainFactory.createDomainInstance(BookDAOImpl.class);
		hd = DomainFactory.createDomainInstance(HandleDAOImpl.class);
		rd = DomainFactory.createDomainInstance(RemarkDAOImpl.class);
	}


	/**
	 * 结算
	 * @param userid
	 * @param pay_pwd
	 * @param sc
	 * @param trade
	 * @return
	 */
	/*public String cash(int userid,String pay_pwd,ShoppingCart sc,Trade trade){
		//结算时需先更正购物车项isExit状态，以及检查图书库存是否能满足购物车项图书数量
		User user = ud.getUserWithUserId(userid);
		Map<Integer, ShoppingCartItem> map =  sc.getShoppingCart();
		String storeMsg = "";
		//验证支付密码
		if(!(pay_pwd.equals(user.getPay_pwd()))){
			return "支付密码有误";
		}
		//为以下循环加一个同步锁
		//验证图书数量是否满足
		
		for(Entry<Integer, ShoppingCartItem> e:map.entrySet()){
			ShoppingCartItem sci = e.getValue();
			if(sci.getQuantity()>bd.getStoreNumber(sci.getId())){
			synchronized (this.getClass()) {
				if(sci.getQuantity()>bd.getStoreNumber(sci.getId())){
					storeMsg+=sci.getTitle()+",";
					}
				}
			}
		}
		if(!(storeMsg.trim().isEmpty())){
			return storeMsg+"库存不足";
		}
		//验证余额是否满足
		if((user.getBalance()-sc.getTotalMoney())<0){
			return "您的余额不足";
		}
		
		List<TradeItem> tradeItems = new ArrayList<>();
		
		//结算时是否要加上事务？
		ud.updateBalance(userid, user.getBalance()-sc.getTotalMoney());
		bd.batchUpdateStoreNumberAndSalesAmount(sc.getShoppingCartItems());
		td.insert(trade);
		for(Entry<Integer, ShoppingCartItem> e:map.entrySet()){
			ShoppingCartItem sci = e.getValue();
			TradeItem tradeItem = new TradeItem(sci.getId(), sci.getQuantity(), trade.getTradeid(),sci.getPrice());
			tradeItems.add(tradeItem);
		}
		tid.batchSave(tradeItems);
		return "支付成功";
		
	}*/
	/**
	 * 创建订单（此时未支付）
	 * @param sc
	 * @param shippingAddress
	 * @return
	 */
	public boolean createTrade(Trade trade,Set<TradeItem> tradeItems){
		try {
			td.insert(trade);
			tid.batchSave(tradeItems);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
		
	}
	/**
	 * 获取所有指定状态的订单进行定时任务检查
	 * @param status 订单状态
	 * @return
	 */
	public Set<Trade> findTradesWithStatusForTask(Integer status){
		Set<Trade> tradeList = null;
		try {
			tradeList = td.getTradeWithStatus(status);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return tradeList;
		
	}

	/**
	 * 获取订单pages
	 * @param ct
	 * @param isShow
	 * @return
	 */
	public Page<Trade> findTradePage(CriteriaTrade ct,Integer isShow){
		Page<Trade> pages = null;
		try {
			pages = td.getPageWithStatus(ct, isShow);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return pages;
	}
	
	/**
	 * 根据订单id查找对应订单
	 * @param tradeid
	 * @return
	 */
	public Trade findTradeWithTradeid(String tradeid){
		Trade trade = null;
		try {
			trade = td.getTradeWithTradeId(tradeid);
//			Set<TradeItem> tradeItems = this.findTradeItemsWithTradeid(tradeid);
//			for (TradeItem tradeItem : tradeItems) {
//				tradeItem.setBook(bd.getBook(tradeItem.getBookid()));
//			}
//			trade.setTradeItems(tradeItems);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return trade;
		
	}
	/**
	 * 根据订单id查找订单项集合
	 * @param tradeid
	 * @return
	 */
	public Set<TradeItem> findTradeItemsWithTradeid(String tradeid){
		Set<TradeItem> tradeItems = null;
		try {
			tradeItems = tid.getTradeItemsWithTradeId(tradeid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return tradeItems;
	}
	/**
	 * 订单支付业务
	 * @param tradeid
	 * @return
	 */
	public boolean pay(String tradeid){
//		需要更新支付时间，，支付状态，分配后台负责人员  BackStageUserid，paytime，
		boolean flag = false;
		//更新订单状态
		this.updateStatusWithTradeid(tradeid, DBInitValConstant.TRADE_STATUS_PAID);
		try {
		//更新订单支付时间
			td.updatePayTimeWithTradeId(TimeUtils.getCurrTime(TimeUtils.FORMATTER_1), tradeid);
		//批量更新图书销量
			Set<TradeItem> tradeItems = tid.getTradeItemsWithTradeId(tradeid);
			bd.batchUpdateSalesAmount(tradeItems);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	/**
	 * 退款业务
	 * @param tradeid
	 * @return
	 */
	public boolean refund(String tradeid){
		boolean flag = false;
		try {
			//将正在处理中的发货订单处理记录废弃
			hd.updateIsValidWithOperateAndStatusAndTradeIdTradeId(DBInitValConstant.HANDLE_ISVALID_UNVALID, 
					DBInitValConstant.HANDLE_OPERATE_SHIP, DBInitValConstant.HANDLE_STATUS_PROCESS,
					DBInitValConstant.HANDLE_ISVALID_VALID, tradeid,TimeUtils.getCurrTime(TimeUtils.FORMATTER_1));
			//更新订单的状态为退款中
			td.updateStatusWithTradeId(DBInitValConstant.TRADE_STATUS_REFUNDING, tradeid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		
		
		return flag;
	}
	/**
	 * 取消订单业务
	 * @param tradeid
	 * @return
	 */
	public boolean cancle(String tradeid){
		boolean flag = false;
		try {
			Set<TradeItem> tradeItems = tid.getTradeItemsWithTradeId(tradeid);
			//批量更新图书库存
			bd.batchUpdateStoreNumber(tradeItems);
			//更新订单状态
			td.updateStatusWithTradeId(DBInitValConstant.TRADE_STATUS_CANCEL, tradeid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	/**
	 * 取消退款
	 * @param tradeid
	 * @return
	 */
	public boolean cancleRefund(String tradeid){
		boolean flag = false;
		try {
//			Handle handle = hd.getRecentlyHandleWithOperateAndStatusAndIsValidAndTradeId(operate, status, isValid, tradeid);
			//更新正在处理中的有效的订单退款处理记录是否有效状态为无效
			hd.updateIsValidWithOperateAndStatusAndTradeIdTradeId(DBInitValConstant.HANDLE_ISVALID_UNVALID, 
					DBInitValConstant.HANDLE_OPERATE_REFUND, DBInitValConstant.HANDLE_STATUS_PROCESS,
					DBInitValConstant.HANDLE_ISVALID_VALID, tradeid,TimeUtils.getCurrTime(TimeUtils.FORMATTER_1));
			//更新订单状态
			td.updateStatusWithTradeId(DBInitValConstant.TRADE_STATUS_PAID, tradeid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * 确认收货业务
	 * @param tradeid
	 * @return
	 */
	public boolean confirm(String tradeid){
		boolean flag = false;
		try {
			//更新订单状态
			td.updateStatusWithTradeId(DBInitValConstant.TRADE_STATUS_NOT_REMARKED, tradeid);
			//更新订单交易完成时间
			td.updateTransactionTimeWithTradeId(TimeUtils.getCurrTime(TimeUtils.FORMATTER_1), tradeid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * 评价事务
	 * @param tradeid
	 * @param remarks
	 * @return
	 */
	public boolean remark(String tradeid,Set<Remark> remarks){
		boolean flag = false;
		try {
			//更新订单状态为已完成
			td.updateStatusWithTradeId(DBInitValConstant.TRADE_STATUS_FINISHED, tradeid);
			//批量新增评价记录
			rd.addRemarkList(remarks);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
		
	}
	/**
	 * 通过订单id删除订单(不是物理意义上的删除，只是相对用户来说的删除)
	 * @param tradeid
	 * @throws SQLException 
	 */
	public boolean deleteTradeWithTradeid(String tradeid){
			try {
				td.updateIsShowWithTradeId(DBInitValConstant.IS_SHOW_NO, tradeid);
			} catch (SQLException e) {
				logger.error(e);
				throw new RuntimeException(e);
			}
		return true;
	}

	/**
	 * 通过订单id更新订单状态
	 * @param tradeid
	 * @param status
	 * @return
	 */
	public boolean updateStatusWithTradeid(String tradeid,Integer status){
		try {
			td.updateStatusWithTradeId(status, tradeid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
		
	}
	public boolean ship(Trade trade){
		boolean flag = false;
		
		try {
			td.updateShipTimeWithTradeId(TimeUtils.getCurrTime(TimeUtils.FORMATTER_1), trade.getTradeid());
			td.updateConfirmReceiptOverDaysWithTradeId(trade.getConfirmReceiptOverDays(), trade.getConfirmReceiptExpirtyTime(), trade.getTradeid());
			td.updateStatusWithTradeId(trade.getStatus_(), trade.getTradeid());
			td.updateExpressWithTradeId(trade.getExpressOrder(), trade.getExpressComp(), trade.getTradeid());
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
}

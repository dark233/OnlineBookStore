package com.taunton.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.taunton.javabean.Handle;
import com.taunton.javabean.ShippingAddress;
import com.taunton.javabean.Trade;
import com.taunton.web.CriteriaTrade;
import com.taunton.web.Page;

public interface TradeDAO {
	/**
	 * 插入订单记录
	 * 
	 * @param trade
	 */
	public void insert(Trade trade) throws Exception ;
	/**
	 * 通过订单id删除订单
	 * @param tradeId
	 */
	public void deleteTradeWithTradeid(String tradeId) throws Exception ;
	/**
	 * 通过用户id删除订单
	 * @param userId
	 */
	public void deleteTradeWithUserid(String userId) throws Exception ;
	/**
	 * 根据订单id更新支付超时天数
	 * @param payOverDays
	 * @param tradeId
	 */
	public void updatePayOverDaysWithTradeId(Double payOverDays,String payExpirtyTime,String tradeId) throws Exception ;
	/**
	 * 根据订单id更新确认收货超时天数和确认收货超时时间
	 * @param confirmReceiptDays
	 * @param tradeId
	 */
	public void updateConfirmReceiptOverDaysWithTradeId(Double confirmReceiptDays,String confirmReceiptExpirtyTime,String tradeId) throws Exception ;
	
	/**
	 * 通过订单id更新订单金额
	 * @param tradeId
	 */
	public void updateTradeMoneyWithTradeId(Float tradeMoney,String tradeId) throws Exception ;
	/**
	 * 根据订单id更新订单状态(
    1:代付款,
	2：已付款，待发货,
	3：已发货，等待确认,
	4：确认收货，待评价,
	5：取消订单 ,
	6：退款中,
	7：退款完成,
	8：订单已评价，完成订单)
	 * @param trade
	 */
	public void updateStatusWithTradeId(Integer status,String tradeId) throws Exception ;
	/**
	 * 根据订单id更新订单支付时间
	 * @param payTime
	 * @param tradeid
	 * @throws Exception
	 */
	public void updatePayTimeWithTradeId(String payTime,String tradeid) throws Exception;
	/**
	 * 根据订单id更新订单发货时间
	 * @param shipTime
	 * @param tradeid
	 * @throws Exception
	 */
	public void updateShipTimeWithTradeId(String shipTime,String tradeid) throws Exception;
	/**
	 * 根据订单id更新订单确认收货时间
	 * @param transactionTime
	 * @param tradeid
	 * @throws Exception
	 */
	public void updateTransactionTimeWithTradeId(String transactionTime,String tradeid) throws Exception;
	/**
	 * 根据订单id更新订单快递(expressComp&expressOrder)
	 * @param trade
	 */
	public void updateExpressWithTradeId(String expressOrder,String expressComp,String tradeId) throws Exception ;
	/**
	 * 更新用户是否将订单展示出来的状态通过订单id (1表示true将订单展示给用户,2表示false不将订单展示给用户)根据订单id，做用户的删除效果使用
	 * @param isShow
	 * @param tradeId
	 * @return
	 */
	public void updateIsShowWithTradeId(Integer isShow,String tradeId) throws Exception ;
	/**
	 * 更新用户是否将订单展示出来的状态通过用户id (1表示true将订单展示给用户,2表示false不将订单展示给用户)根据用户id，做用户的删除效果使用
	 * @param isShow
	 * @param userId
	 * @return
	 */
	public void updateIsShowWithUserId(Integer isShow,String userId) throws Exception ;
	/**
	 * 通过订单id获取订单状态
	 * @param tradeid
	 * @return
	 * @throws Exception
	 */
	public Integer getTradeStatusWithTradeId(String tradeid) throws Exception;
	/**
	 * 通过订单id查找订单
	 * @param tradeId
	 * @return
	 */
	public Trade getTradeWithTradeId(String tradeId) throws Exception ;
	/**
	 * 通过用户id查找该用户的所有订单
	 * @param userId
	 * @return
	 */
	public Set<Trade> getTradeWithUserId(String userId) throws Exception ;
	/**
	 * 通过用户名查找该用户的所有订单
	 * @param username
	 * @return
	 */
	public Set<Trade> getTradeWithUserName(String username) throws Exception ;
	/**
	 * 
	 * 查找指定状态的订单用于定时任务检查
	  ( 1:代付款,
		2：已付款，待发货,
		3：已发货，等待确认,
		4：确认收货，待评价,
		5：取消订单 ,
		6：退款中,
		7：退款完成,
		8：订单已评价，完成订单)
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Set<Trade> getTradeWithStatus(Integer status) throws Exception;
	
	/**
	 * 根据订单状态获取订单page
	 * @param ct
	 * @param isShow
	 * @return
	 * @throws SQLException
	 */
	public Page<Trade> getPageWithStatus(CriteriaTrade ct,Integer isShow) throws SQLException ;
	/**
	 * 根据订单状态获取订单总条数
	 * @param ct
	 * @param isShow
	 * @return
	 * @throws SQLException
	 */
	public long getTotalTradeNumberWithStatus(CriteriaTrade ct,Integer isShow) throws SQLException ;
	/**
	 * 根据订单状态获取订单集合
	 * @param ct
	 * @param pageSize
	 * @param isShow
	 * @return
	 * @throws SQLException
	 */
	public List<Trade> getTradeListWithStatus(CriteriaTrade ct, int pageSize,Integer isShow) throws SQLException ;

}

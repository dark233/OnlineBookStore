package com.taunton.dao;

import java.util.Collection;
import java.util.Set;

import com.taunton.javabean.TradeItem;

public interface TradeItemDAO {
	/**
	 * 批量存储订单条目
	 * 
	 * @param tradeItems
	 */
	public void batchSave(Collection<TradeItem> tradeItems) throws Exception;

	/**
	 * 根据订单id删除订单条目
	 * @param tradeId
	 */
	public void deleteTradeItemsWithTradeid(String tradeId) throws Exception;
	/**
	 * 根据订单id获取订单条目集合
	 * 
	 * @param tradeId
	 * @return
	 */
	public Set<TradeItem> getTradeItemsWithTradeId(String tradeId) throws Exception;

}

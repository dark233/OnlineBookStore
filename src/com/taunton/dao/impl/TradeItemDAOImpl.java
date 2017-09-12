package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.taunton.dao.TradeItemDAO;
import com.taunton.javabean.TradeItem;

public class TradeItemDAOImpl extends BaseDAO<TradeItem> implements TradeItemDAO {

	@Override
	public void batchSave(Collection<TradeItem> tradeItems) throws SQLException {
		String sql = "INSERT INTO tradeitem(itemid,bookid,quantity,tradeid,price) " +
				"VALUES(?,?,?,?,?)";
		List<TradeItem> list = new ArrayList<>(tradeItems);
		Object [][] obj = new Object[tradeItems.size()][5];
		for(int i = 0;i<list.size();i++){
			obj[i][0] = list.get(i).getItemid();
			obj[i][1] = list.get(i).getBookid();
			obj[i][2] = list.get(i).getQuantity();
			obj[i][3] = list.get(i).getTradeid();
			obj[i][4] = list.get(i).getPrice();
		}
			batch(sql, obj);
	}
	@Override
	public void deleteTradeItemsWithTradeid(String tradeId) throws SQLException {
		String sql = "DELETE FROM tradeitem WHERE tradeid = ?";
		update(sql, tradeId);
		}
	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(String tradeId) throws SQLException {
		String sql =  "SELECT itemid, bookid,quantity, tradeid ,price FROM tradeitem WHERE tradeid = ?";
		return new LinkedHashSet<>(queryList(sql, tradeId));
	}


	}


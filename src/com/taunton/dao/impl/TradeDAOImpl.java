package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.taunton.dao.TradeDAO;
import com.taunton.javabean.Book;
import com.taunton.javabean.ShippingAddress;
import com.taunton.javabean.Trade;
import com.taunton.web.CriteriaBook;
import com.taunton.web.CriteriaTrade;
import com.taunton.web.Page;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO {

	@Override
	public void insert(Trade trade) throws SQLException {
		String sql = "INSERT INTO trade(tradeid,userid,shipaddrid,tradetime,payoverdays,payexpirtytime," +
				"trademoney,status_,isshow,cnee,tel,shipAddr) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {trade.getTradeid(),trade.getUserid(),trade.getShipAddrid(),
				trade.getTradeTime(),trade.getPayOverDays(),trade.getPayExpirtyTime(),trade.getTradeMoney(),
				trade.getStatus_(),trade.getIsShow(),trade.getCnee(),trade.getTel(),trade.getShipAddr()};
		update(sql, obj);
//		insert(sql,obj);
//		long tradeid = insert(sql, obj);
//		trade.setTradeid((int) t);
	}
	@Override
	public void deleteTradeWithTradeid(String tradeId) throws SQLException {
		String sql = "DELETE FROM trade WHERE tradeid = ?";
		update(sql, tradeId);
		
	}
	
	@Override
	public void deleteTradeWithUserid(String userId) throws SQLException {
		String sql = "DELETE FROM trade WHERE userid = ?";
		update(sql, userId);
	}
	
	@Override
	public void updateTradeMoneyWithTradeId(Float tradeMoney, String tradeId) throws SQLException {
		String sql = "UPDATE trade SET trademoney = ? WHERE tradeid = ?";
		Object[] obj = {tradeMoney,tradeId};
		update(sql, obj);
	}

	@Override
	public void updateStatusWithTradeId(Integer status_,
			String tradeId) throws SQLException {
		String sql = "UPDATE trade SET status_ = ? WHERE tradeid = ?";
		Object[] obj = {status_,tradeId};
		update(sql, obj);
	}
	
	@Override
	public void updatePayTimeWithTradeId(String payTime, String tradeid)
			throws SQLException {
		String sql = "UPDATE trade SET paytime = ? WHERE tradeid = ?";
		Object[] obj = {payTime,tradeid};
		update(sql, obj);
	}
	@Override
	public void updateShipTimeWithTradeId(String shipTime, String tradeid)
			throws SQLException {
		String sql = "UPDATE trade SET shipTime = ? WHERE tradeid = ?";
		Object[] obj = {shipTime,tradeid};
		update(sql, obj);
	}
	@Override
	public void updateTransactionTimeWithTradeId(String transactionTime,
			String tradeid) throws SQLException {
		String sql = "UPDATE trade SET transactiontime = ? WHERE tradeid = ?";
		Object[] obj = {transactionTime,tradeid};
		update(sql, obj);
	}

	@Override
	public void updateExpressWithTradeId(String expressOrder,String expressComp,String tradeId) throws SQLException {
		String sql = "UPDATE trade SET expressorder = ?,expresscomp = ? WHERE tradeid = ?";
		Object[] obj = {expressOrder,expressComp,tradeId};
		update(sql, obj);
	}
	
	@Override
	public void updatePayOverDaysWithTradeId(Double payOverDays,String payExpirtyTime, String tradeId) throws SQLException {
		String sql = "UPDATE trade SET payoverdays = ?,payexpirtytime = ? WHERE tradeid = ?";
		Object[] obj = {payOverDays,payExpirtyTime,tradeId};
		update(sql, obj);
	}
	@Override
	public void updateConfirmReceiptOverDaysWithTradeId(
			Double confirmReceiptDays,String confirmReceiptExpirtyTime, String tradeId) throws SQLException {
		String sql = "UPDATE trade SET confirmreceiptOverDays = ?,confirmreceiptexpirtyTime = ? WHERE tradeid = ?";
		Object[] obj = {confirmReceiptDays,confirmReceiptExpirtyTime,tradeId};
		update(sql, obj);
	}
	
	@Override
	public void updateIsShowWithTradeId(Integer isShow, String tradeId) throws SQLException {
		String sql = "UPDATE trade SET isShow = ? WHERE tradeid = ?";
		Object[] obj = {isShow,tradeId};
		update(sql, obj);

	}
	@Override
	public void updateIsShowWithUserId(Integer isShow, String userId) throws SQLException {
		String sql = "UPDATE trade SET isShow = ? WHERE userid = ?";
		Object[] obj = {isShow,userId};
		update(sql, obj);

	}
	
	@Override
	public Integer getTradeStatusWithTradeId(String tradeid) throws Exception {
		String sql = "SELECT status_ FROM trade WHERE tradeid = ?";
		Number number = querySingle(sql, tradeid);
		return number.intValue();
	}
	
	@Override
	public Trade getTradeWithTradeId(String tradeId) throws SQLException {
		String sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
				+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,"
				+ "expressOrder,expressComp,isShow,cnee,tel,shipAddr FROM trade "
				+ "WHERE tradeid = ?";
		return query(sql, tradeId);
	}
	
	@Override
	public Set<Trade> getTradeWithUserId(String userId) throws SQLException {
		String sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
				+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,"
				+ "expressOrder,expressComp,isShow,cnee,tel,shipAddr FROM trade "
				+ "WHERE userId = ? ORDER BY tradeTime DESC";
		return new LinkedHashSet<>(queryList(sql, userId));
	}
	
	@Override
	public Set<Trade> getTradeWithUserName(String username) throws SQLException {
		String sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
				+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,"
				+ "expressOrder,expressComp,isShow,cnee,tel,shipAddr FROM trade "
				+ "WHERE username = ? ORDER BY tradeTime DESC";
		return new LinkedHashSet<>(queryList(sql, username));
	}
	
	@Override
	public Set<Trade> getTradeWithStatus(Integer status_) throws Exception {

		String sql = null;
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE status_ = COALESCE(?,status_) "
					+ "ORDER BY tradeTime DESC ";
		return new LinkedHashSet<>(queryList(sql, status_));
	
	}
	
	@Override
	public Page<Trade> getPageWithStatus(CriteriaTrade ct,Integer isShow) throws SQLException {
		Page<Trade> page = new Page<>(ct.getPageNo());
		page.setTotalItemNumber(getTotalTradeNumberWithStatus(ct,isShow));
		//此时传入page对象里的pageNo已经被纠正了，这时也应该将cb里的pageNo纠正
		ct.setPageNo(page.getPageNo());
		page.setList(getTradeListWithStatus(ct, page.getPageSize(),isShow));
		return page;
	}

	@Override
	public long getTotalTradeNumberWithStatus(CriteriaTrade ct,Integer isShow) throws SQLException {
		String sql = null;
		List<Object> list = new ArrayList<>();
		list.add(ct.getTradeStatus());
		list.add(isShow);
		list.add(ct.getUserid());
		switch (ct.getTradeStatus()) {
		case 1:
			sql = "SELECT COUNT(*) FROM trade WHERE NOW() <= payexpirtytime AND status_ = ? AND isShow = ? AND userid = ?";
		break;
		case 3:
//			sql = "SELECT COUNT(*) FROM "
//					+"(SELECT trade.tradeid FROM trade JOIN handle ON trade.tradeid = handle.tradeid WHERE NOW() <= trade.confirmreceiptexpirtytime "
//							+"AND trade.status_ = ? AND trade.isShow = ? AND handle.operate = 2 AND trade.userid = ? GROUP BY trade.tradeid "
//					+"UNION "
//					+"SELECT tradeid FROM trade WHERE NOW() <= trade.confirmreceiptexpirtytime AND trade.status_ = ? AND trade.isShow = ? AND userid = ?) a";
			sql = "SELECT COUNT(*) FROM trade WHERE NOW() <= confirmreceiptexpirtytime AND status_ = ? AND isShow = ? AND userid = ?";
			break;
		case 4:
			sql = "SELECT COUNT(*) FROM "
			+ "(SELECT tradeid FROM trade WHERE NOW() > confirmreceiptexpirtytime AND status_ = ? AND isShow = ? AND userid = ?"
			+ "UNION "
			+ "SELECT tradeid FROM trade WHERE status_ = ? AND isShow = ? AND userid = ?) a";
			list.clear();
			list.add(3);
			list.add(isShow);
			list.add(ct.getUserid());
			list.add(ct.getTradeStatus());
			list.add(isShow);
			list.add(ct.getUserid());
			break;
		case 5:
			sql = "SELECT COUNT(*) FROM "
			+ "(SELECT tradeid FROM trade WHERE NOW() > payexpirtytime AND status_ = ? AND isShow = ? AND userid = ?"
			+ "UNION "
			+ "SELECT tradeid FROM trade WHERE status_ = ? AND isShow = ? AND userid = ?) a";
			list.clear();
			list.add(1);
			list.add(isShow);
			list.add(ct.getUserid());
			list.add(ct.getTradeStatus());
			list.add(isShow);
			list.add(ct.getUserid());
			break;
		case 2:
		case 8:
			sql = "SELECT COUNT(*) FROM trade WHERE status_ = ? AND isShow = ? AND userid = ?";
			break;
		case 6:
		case 7:
			sql = "SELECT COUNT(*) FROM "
					+ "(SELECT DISTINCT handle.tradeid FROM handle JOIN trade ON handle.tradeid = trade.tradeid WHERE handle.operate = 2 AND userid = ?) a "
					+ "JOIN  trade "
					+ "ON a.tradeid = trade.tradeid WHERE isShow = ? "
					+ "ORDER BY tradeTime DESC ";
			list.clear();
			list.add(ct.getUserid());
			list.add(isShow);
			break;
		default:
			sql = "SELECT COUNT(*) FROM trade WHERE status_ = status_ AND isShow = ? AND userid = ?";
			list.clear();
			list.add(isShow);
			list.add(ct.getUserid());
			break;
		}
		Object[] params = list.toArray();
		return querySingle(sql, params);
	
	}

//	推测：在同时使用order by和limit时，MySQL进行了某些优化，将语句执行逻辑从"where——order by——limit"变成了"order by——limit——where"，
//	导致在某些情况下，同时使用order by和limit会导致查无结果（或结果数量不足）。具体出现问题与否是与表中数据有关的。!!!如果行不通，就只能变成子查询的格式了，子查询排序，外层分页
	@Override
	public List<Trade> getTradeListWithStatus(CriteriaTrade ct, int pageSize,Integer isShow) throws SQLException {
		String sql = null;
		List<Object> list = new ArrayList<>();
		list.add(ct.getTradeStatus());
		list.add(isShow);
		list.add(ct.getUserid());
		list.add((ct.getPageNo() - 1) * pageSize);
		list.add(pageSize);
		switch (ct.getTradeStatus()) {
		case 1:
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE NOW() <= payexpirtytime AND status_ = ? AND isShow = ? AND userid = ? "
					+ "ORDER BY tradeTime DESC) a "
					+ "limit ?,?";
		break;
		case 3:
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE NOW() <= confirmreceiptexpirtytime AND status_ = ? AND isShow = ? AND userid = ? "
					+ "ORDER BY shipTime DESC) a "
					+ "limit ?,?";
			break;
		case 4:
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM"
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE NOW() > confirmreceiptexpirtytime AND status_ = ? AND isShow = ? AND userid = ? "
					+ "UNION "
					+ "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE status_ = ? AND isShow = ? AND userid = ?) a ORDER BY transactionTime DESC) b "
					+ "limit ?,?";
			list.clear();
			list.add(3);
			list.add(isShow);
			list.add(ct.getUserid());
			list.add(ct.getTradeStatus());
			list.add(isShow);
			list.add(ct.getUserid());
			list.add((ct.getPageNo() - 1) * pageSize);
			list.add(pageSize);
			break;
		case 5:
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM"
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE NOW() > payexpirtytime AND status_ = ? AND isShow = ? AND userid = ? "
					+ "UNION "
					+ "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE status_ = ? AND isShow = ? AND userid = ?) a ORDER BY tradeTime DESC) b "
					+ "limit ?,?";
			list.clear();
			list.add(1);
			list.add(isShow);
			list.add(ct.getUserid());
			list.add(ct.getTradeStatus());
			list.add(isShow);
			list.add(ct.getUserid());
			list.add((ct.getPageNo() - 1) * pageSize);
			list.add(pageSize);
			break;
		case 2:
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE status_ = ? AND isShow = ? AND userid = ? "
					+ "ORDER BY payTime DESC) a "
					+ "limit ?,?";
			break;
		case 8:
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE status_ = ? AND isShow = ? AND userid = ? "
					+ "ORDER BY tradeTime DESC) a "
					+ "limit ?,?";
			break;
		case 6:
		case 7:
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT trade.tradeid tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT handle.tradeid,MAX(createTime) ct FROM handle JOIN trade ON handle.tradeid = trade.tradeid WHERE "
					+ "handle.operate = 2 AND userid = ? GROUP BY handle.tradeid) a "
					+ "JOIN  trade "
					+ "ON a.tradeid = trade.tradeid WHERE isShow = ? "
					+ "ORDER BY a.ct DESC) b "
					+ "limit ?,?";
			list.clear();
			list.add(ct.getUserid());
			list.add(isShow);
			list.add((ct.getPageNo() - 1) * pageSize);
			list.add(pageSize);
			break;
		default:
			sql = "SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM "
					+ "(SELECT tradeid, userid,shipAddrid,tradeTime,payTime,shipTime,transactionTime,payOverDays,"
					+ "payExpirtyTime,confirmReceiptOverDays,confirmReceiptExpirtyTime,tradeMoney,status_,expressOrder,"
					+ "expressComp,isShow,cnee,tel,shipAddr FROM trade WHERE status_ = status_ AND isShow = ? AND userid = ? "
					+ "ORDER BY tradeTime DESC) a "
					+ "limit ?,?";
			list.clear();
			list.add(isShow);
			list.add(ct.getUserid());
			list.add((ct.getPageNo() - 1) * pageSize);
			list.add(pageSize);
			break;
		}
		Object[] params = list.toArray();
		List<Trade> tl = queryList(sql, params);
		return queryList(sql, params);
	}
	

}

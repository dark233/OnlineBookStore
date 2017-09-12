package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.taunton.dao.RechargeRecordsDAO;
import com.taunton.javabean.RechargeRecords;

public class RechargeRecordsDAOImpl extends BaseDAO<RechargeRecords> implements RechargeRecordsDAO{

	@Override
	public void add(RechargeRecords rechargeRecords) throws SQLException {
		String sql = "INSERT INTO rechargerecords(id,userid,rechargemoney,rechargetime,rechargeways,isshow) VALUES(?,?,?,?,?,?)";
		Object[] obj = {rechargeRecords.getId(),rechargeRecords.getUserid(),rechargeRecords.getRechargeMoney(),rechargeRecords.getRechargeDate(),
				rechargeRecords.getRechargeWays(),rechargeRecords.getIsShow()};
		update(sql, obj);
	}

	@Override
	public void deleteRechargeRecordsWithId(String id) throws SQLException {
		String sql = "DELETE FROM rechargerecords WHERE id = ?";
		update(sql, id);
	
	}

	@Override
	public void deleteRechargeRecordsWithUserid(String userId) throws SQLException {
		String sql = "DELETE FROM rechargerecords WHERE userid = ?";
		update(sql, userId);
	}

	@Override
	public void updateIsShowWithId(Integer isShow,String id) throws SQLException {
		String sql = "UPDATE rechargerecords SET isshow = ? WHERE id = ?";
		Object[] obj = {isShow,id};
		update(sql, obj);
	}

	@Override
	public void updateIsShowWithUserid(Integer isShow,String userId) throws SQLException {
		String sql = "UPDATE rechargerecords SET isshow = ? WHERE userid = ?";
		Object[] obj = {isShow,userId};
		update(sql, obj);
	}

	@Override
	public Set<RechargeRecords> getRechargeRecordsWithUserid(String userId) throws SQLException {
		String sql = "SELECT id, userid,rechargeMoney, rechargeTime,rechargeWays,isShow FROM rechargerecords " +
				"WHERE userid = ? ORDER BY rechargetime DESC";
		return new LinkedHashSet<>(queryList(sql,userId));
	}

	@Override
	public Set<RechargeRecords> getRechargeRecordsWithSometime(
			String startDate, String endDate) throws SQLException {
		String sql = "SELECT id, userid,rechargeMoney, rechargeTime,rechargeWays,isShow FROM rechargerecords " +
				"WHERE rechargetime BETWEEN ? AND ? ORDER BY rechargetime DESC";
		Object[] obj = {startDate,endDate};
		return new LinkedHashSet<>(queryList(sql,obj));
	}

	@Override
	public Set<RechargeRecords> getRechargeRecords() throws SQLException {
		String sql = "SELECT id, userid,rechargeMoney, rechargeTime,rechargeWays,isShow FROM rechargerecords " ;
		return new LinkedHashSet<>(queryList(sql));
	}

}

package com.taunton.dao.impl;

import java.sql.SQLException;

import com.taunton.dao.RechargeDAO;
import com.taunton.javabean.Recharge;

public class RechargeDAOImpl extends BaseDAO<Recharge> implements RechargeDAO {

	@Override
	public Recharge getFaceValueWithRechargeid(String rechargeid) throws SQLException {
		String sql = "SELECT id,face_value FROM recharge WHERE id = ?";
		return query(sql, rechargeid);
	}

	@Override
	public void deleteFaceValueWithRechargeid(String rechargeid) throws SQLException {
		String sql = "DELETE FROM recharge WHERE id = ?";
		update(sql, rechargeid);
	}

}

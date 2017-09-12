package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.taunton.dao.HandleDAO;
import com.taunton.dao.RefundRecordsDAO;
import com.taunton.dao.RemarkDAO;
import com.taunton.javabean.Book;
import com.taunton.javabean.Handle;
import com.taunton.javabean.RefundRecords;
import com.taunton.javabean.Remark;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public class RefundRecordsDAOImpl extends BaseDAO<RefundRecords> implements RefundRecordsDAO {

	@Override
	public void addRefundRecords(RefundRecords refundRecords) throws SQLException {
		String sql = "INSERT INTO refundrecords(id,handleid,status_,refundReason,refusedReason,createTime,updateTime) VALUES(?,?,?,?,?,?,?)";
		Object[] obj = {refundRecords.getId(),refundRecords.getHandleid(),refundRecords.getStatus_(),refundRecords.getRefundReason(),refundRecords.getRefusedReason(),
				refundRecords.getCreateTime(),refundRecords.getUpdateTime()};
		update(sql, obj);
	}

	@Override
	public void deleteRefundRecords(String id) throws SQLException {
		String sql = "DELETE FROM refundrecords WHERE id = ?";
		update(sql, id);
	}

	@Override
	public void updateStatusWithId(Integer status_, String id) throws SQLException {
		String sql = "UPDATE refundrecords SET status_ = ? WHERE id = ?";
		Object[] obj = {status_,id};
		update(sql, obj);
	}
	
	@Override
	public void handleRefund(Integer status_,String refusedReason,String updateTime, String handleid) throws SQLException {
		String sql = "UPDATE refundrecords SET status_ = ?,refusedReason = ?,updateTime = ? WHERE handleid = ?";
		Object[] obj = {status_,refusedReason,updateTime,handleid};
		update(sql, obj);
	}

	@Override
	public void updateUpdateTimeWithId(String updateTime, String id)
			throws SQLException {
		String sql = "UPDATE refundrecords SET updateTime = ? WHERE id = ?";
		Object[] obj = {updateTime,id};
		update(sql, obj);
	}

	@Override
	public RefundRecords getRefundRecordsWithId(String id) throws SQLException {
		String sql = "SELECT id,handleid,status_,refundReason,refusedReason,createTime,updateTime FROM refundrecords WHERE id = ?";
		return query(sql, id);
	}

	@Override
	public RefundRecords getRefundRecordsWithHandleId(String handleid)
			throws SQLException {
		String sql = "SELECT id,handleid,status_,refundReason,refusedReason,createTime,updateTime FROM refundrecords WHERE handleid = ?";
		return query(sql, handleid);
	}


}

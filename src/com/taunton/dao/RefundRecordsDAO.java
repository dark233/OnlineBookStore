package com.taunton.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.taunton.javabean.Book;
import com.taunton.javabean.Handle;
import com.taunton.javabean.RefundRecords;
import com.taunton.javabean.Remark;
import com.taunton.web.CriteriaBook;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public interface RefundRecordsDAO {

	/**
	 * 新增一条退款记录
	 * @param refundRecords
	 * @throws Exception
	 */
	public void addRefundRecords(RefundRecords refundRecords) throws Exception;
	/**
	 * 根据退款记录编号id删除一条退款记录
	 * @param handleid
	 * @throws Exception
	 */
	public void deleteRefundRecords(String id) throws Exception;
	/**
	 * 根据退款记录编号更新该条退款记录状态
	 * @param status
	 * @param id
	 * @throws Exception
	 */
	public void updateStatusWithId(Integer status,String id) throws Exception;
	/**
	 * 根据退款编号id更新该条记录更新时间
	 * @param updateTime
	 * @param id
	 * @throws Exception
	 */
	public void updateUpdateTimeWithId(String updateTime,String id) throws Exception;
	/**
	 * 后台用户处理退款
	 * @param status_
	 * @param refusedReason
	 * @param updateTime
	 * @param handleid
	 * @throws SQLException
	 */
	public void handleRefund(Integer status_,String refusedReason,String updateTime, String handleid) throws SQLException ;
	/**
	 * 根据退款记录编号id获取一条退款记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RefundRecords getRefundRecordsWithId(String id) throws Exception;
	/**
	 * 根据订单处理记录id获取一条对应的订单退款记录
	 * @param handleid
	 * @return
	 * @throws Exception
	 */
	public RefundRecords getRefundRecordsWithHandleId(String handleid) throws Exception;
}

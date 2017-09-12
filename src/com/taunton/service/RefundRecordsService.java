package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.taunton.dao.HandleDAO;
import com.taunton.dao.impl.BackStageUserDAOImpl;
import com.taunton.dao.impl.HandleDAOImpl;
import com.taunton.dao.impl.RefundRecordsDAOImpl;
import com.taunton.dao.impl.ShoppingCartDAOImpl;
import com.taunton.dao.impl.UserDAOImpl;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Handle;
import com.taunton.javabean.Recharge;
import com.taunton.javabean.RefundRecords;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.User;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;

/**
 * 订单退款记录业务
 * @author taunton
 *
 */
public class RefundRecordsService {
	private static Logger logger = Logger.getLogger(RefundRecordsService.class); 

	//这里应使用工厂减少耦合
	private HandleDAOImpl hd = null;
	private BackStageUserDAOImpl bsd = null;
	private RefundRecordsDAOImpl rrd = null;
	public RefundRecordsService() {
		hd = DomainFactory.createDomainInstance(HandleDAOImpl.class);
	    bsd = DomainFactory.createDomainInstance(BackStageUserDAOImpl.class);
	    rrd = DomainFactory.createDomainInstance(RefundRecordsDAOImpl.class);
	}
	
	/**
	 * 新建一条退款记录
	 * @param refundRecords
	 * @return
	 */
	public boolean createRefundRecords(RefundRecords refundRecords){
		boolean flag = false;
		try {
			rrd.addRefundRecords(refundRecords);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * 根据订单处理记录id获取相应的订单退款记录
	 * @param handleid
	 * @return
	 */
	public RefundRecords findRefundRecordsWithHandleId(String handleid){
		RefundRecords refundRecords = null;
		try {
			refundRecords = rrd.getRefundRecordsWithHandleId(handleid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return refundRecords;
	}
	
	/**
	 * 后台用户处理退款
	 * @param handleid
	 * @return
	 */
	public boolean handleRefund(Integer status,String refusedReason,String updateTime,String handleid){
		boolean flag = false;
		try {
			rrd.handleRefund(status, refusedReason, updateTime, handleid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
}

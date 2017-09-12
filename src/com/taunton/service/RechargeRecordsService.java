package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.taunton.dao.HandleDAO;
import com.taunton.dao.impl.BackStageUserDAOImpl;
import com.taunton.dao.impl.HandleDAOImpl;
import com.taunton.dao.impl.RechargeRecordsDAOImpl;
import com.taunton.dao.impl.RefundRecordsDAOImpl;
import com.taunton.dao.impl.ShoppingCartDAOImpl;
import com.taunton.dao.impl.UserDAOImpl;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Handle;
import com.taunton.javabean.Recharge;
import com.taunton.javabean.RechargeRecords;
import com.taunton.javabean.RefundRecords;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.User;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.web.Page;

/**
 * 订单退款记录业务
 * @author taunton
 *
 */
public class RechargeRecordsService {
	private static Logger logger = Logger.getLogger(RechargeRecordsService.class); 

	private RechargeRecordsDAOImpl rrd = null;
	public RechargeRecordsService() {
		rrd = DomainFactory.createDomainInstance(RechargeRecordsDAOImpl.class);
	}
	
	/**
	 * 新建一条充值记录
	 * @param rechargeRecords
	 * @return
	 */
	public boolean createRechargeRecords(RechargeRecords rechargeRecords){
		boolean flag = false;
		try {
			rrd.add(rechargeRecords);;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * 根据订用户id获取充值记录page
	 * @param handleid
	 * @return
	 */
	public Page<RechargeRecords> findPageByUserId(String userid){
		return null;
	}

	
}

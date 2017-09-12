package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.taunton.dao.HandleDAO;
import com.taunton.dao.impl.BackStageUserDAOImpl;
import com.taunton.dao.impl.HandleDAOImpl;
import com.taunton.dao.impl.RechargeDAOImpl;
import com.taunton.dao.impl.ShoppingCartDAOImpl;
import com.taunton.dao.impl.TradeDAOImpl;
import com.taunton.dao.impl.UserDAOImpl;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Handle;
import com.taunton.javabean.Recharge;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.User;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.web.CriteriaHandle;
import com.taunton.web.Page;

/**
 * 订单处理业务
 * @author taunton
 *
 */
public class HandleService {
	private static Logger logger = Logger.getLogger(HandleService.class); 

	//这里应使用工厂减少耦合
	private HandleDAOImpl hd = null;
	private BackStageUserDAOImpl bsd = null;
	private TradeDAOImpl td = null;
	public HandleService() {
		hd = DomainFactory.createDomainInstance(HandleDAOImpl.class);
	    bsd = DomainFactory.createDomainInstance(BackStageUserDAOImpl.class);
	    td = DomainFactory.createDomainInstance(TradeDAOImpl.class);
	}
	
	/**
	 * 自动分单
	 * @param handle
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean assignTrade(Handle handle){
		boolean flag = false;
		try {
			//获取可分配订单处理的后台人员集合，在得到的集合中任意选取一个即可
			Collection<Handle> handleColl = null;
			List<Handle> handleList = null;
			//获取在相应处理功能的订单处理记录中没有过处理记录的后台用户，任取一个即可
			Collection<BackStageUser> userColl = bsd.getEmptyHandleBackStageUserId(handle.getOperate(),DBInitValConstant.HANDLE_STATUS_PROCESS,DBInitValConstant.HANDLE_ISVALID_VALID);
			List<BackStageUser> userList = new ArrayList<>(userColl);
			if(userList != null && userList.size() != 0){
				handle.setBackStageUserid(userList.get(0).getUserid());
			}else{
				int a = 1;
				handleColl = hd.getAssignedBackStageUserId4Trade(handle.getOperate());
				handleList = new ArrayList<>(handleColl);
				//为订单分配后台处理人员
				handle.setBackStageUserid(handleList.get(0).getBackStageUserid());
			}
			//新增一条处理记录
			hd.addHandle(handle);
			bsd.updateHandlingTradeNumWithUserId(1, handle.getBackStageUserid());
			flag = true;
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
		
	}

	/**
	 * 获取订单处理记录page
	 * @param ch
	 * @return
	 */
	public Page<Handle> findHandleWithOperateAndStatusAndBackStageUserId(CriteriaHandle ch){
		Page<Handle> pages = null;
		try {
			pages = hd.getPageWithStatusAndOperate(ch);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return pages;
	}
	/**
	 * 根据订单处理记录功能、状态、是否有效及后台用户id获取后台用户下相应的订单处理记录数目
	 * @param operate
	 * @param status
	 * @param isValid
	 * @param userid
	 * @return
	 */
	public long findHandleNumberWithOperateAndStatusAndIsValidAndBackStageUserId(Integer operate,Integer status,Integer isValid,String userid){
		long num =0;
		CriteriaHandle ch = new CriteriaHandle(status, operate, isValid, userid, 1, null);
		try {
			num = hd.getTotalHandleNumberWithStatusAndOperate(ch);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return num;
	}
	/**
	 * 根据订单处理记录功能、订单处理记录状态及订单id获取该订单在最近一次的订单处理记录（主要用在前台用户的订单详情页面，显示退款情况）
	 * @param operate
	 * @param status
	 * @param tradeid
	 * @return
	 */
	public Handle findRecentlyHandle(Integer operate,Integer status,Integer isValid,String tradeid){
		Handle handle = null;
		try {
			handle = hd.getRecentlyHandleWithOperateAndStatusAndIsValidAndTradeId(operate, status, isValid, tradeid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return handle;
		
	}
	/**
	 * 根据订单处理记录功能、状态、是否有效及前台用户id获取订单处理记录数量
	 * @param operate
	 * @param status
	 * @param isValid
	 * @param userid
	 * @return
	 */
	public Long findHandleNumberWithOperateAndStatusAndIsValidAndUserId(Integer operate,Integer status,Integer isValid,String userid){
		Long handleNum = null;
		try {
			handleNum = hd.getNumberWithOperateAndStatusAndIsValidAndUserId(operate, status, isValid, userid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return handleNum;
	}
	/**
	 * 根据订单处理记录功能、状态、是否有效及订单id获取订单处理记录数量
	 * @param operate
	 * @param status
	 * @param isValid
	 * @param tradeid
	 * @return
	 */
	public Long findHandleNumberWithOperateAndStatusAndIsValidAndTradeId(Integer operate,Integer status,Integer isValid,String tradeid){
		Long handleNum = null;
		try {
			handleNum = hd.getNumberWithOperateAndStatusAndIsValidAndTradeId(operate, status, isValid, tradeid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return handleNum;
	}
	/**
	 * 根据订单处理记录功能、状态、是否有效及订单id获取相应的订单处理记录集合
	 * @param operate
	 * @param status
	 * @param isValid
	 * @param tradeid
	 * @return
	 */
	public Set<Handle> findHandleListWithOperateAndStatusAndIsValidAndTradeId(Integer operate,Integer status,Integer isValid,String tradeid){
		Set<Handle> handleList = null;
		try {
			handleList = hd.getHandleListWithOperateAndStatusAndIsValidAndTradeId(operate, status, isValid, tradeid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return handleList;
	}
	
	/**
	 * 更新订单处理记录状态
	 * @param status
	 * @param handleid
	 * @return
	 */
	public boolean updateHandleStatusByHandleId(Integer status,String handleid){
		boolean flag = false;
		try {
			hd.updateHandleStatusWithHandleId(status,TimeUtils.getCurrTime(TimeUtils.FORMATTER_1), handleid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
		
	}
}

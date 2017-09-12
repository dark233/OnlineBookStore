package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import com.taunton.dao.impl.BackStageUserDAOImpl;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.User;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;

public class BackStageUserService {

	private static Logger logger = Logger.getLogger(BackStageUserService.class); 
	private BackStageUserDAOImpl bsud = null;
	public BackStageUserService(){
		bsud = DomainFactory.createDomainInstance(BackStageUserDAOImpl.class);
	}

	
	/**
	 * 注册后台用户
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public boolean registBackStageUser(BackStageUser user) {
		boolean flag = false;
		try {
			bsud.addUser(user);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}

		return flag;
	}
	
	/**
	 * 获取对应用户的被ban状态
	 * @param username
	 * @return
	 */
	public boolean findIsBanByUsername(String username){
		boolean flag = true;
		try {
			Integer isBan = bsud.getIsBanWithUsername(username);
			if(isBan == 2){
				flag = false;
			}else{
				flag = true;
			}
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * 通过用户名为username或tel的正则表达式获取用户
	 * @param username
	 * @return
	 */
	public BackStageUser findUserByUsernameRegex(String username){
		String regex = "1[34578]\\d{9}";
		BackStageUser bsuser = null;
		try {
				if(username.matches(regex)){
					bsuser = bsud.getUserWithTel(username);
				}else{
					bsuser = bsud.getUserWithUserName(username);
				}
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return bsuser;
		
	}
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public BackStageUser findUserByUsername(String username){
		BackStageUser backStageUser = null;
		try {
			backStageUser =  bsud.getUserWithUserName(username);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return backStageUser;
	}
	/**
	 * 根据绑定手机号码查找用户
	 * @param tel
	 * @return
	 */
	public BackStageUser findUserByTel(String tel){
		BackStageUser backStageUser = null;
		try {
				backStageUser = bsud.getUserWithTel(tel);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return backStageUser;
	}
	/**
	 * 改变后台人员处理完成订单的数量。正数为在当前基础上增加，负数为在当前基础上减少
	 * @param userid
	 * @param num
	 * @return
	 */
	public boolean changeHandledTradeNum(String userid,Integer num){
		boolean flag = false;
		try {
			bsud.updateHandledTradeNumWithUserId(num, userid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	/**
	 * 改变后台人员处理中订单的数量。正数为在当前基础上增加，负数为在当前基础上减少
	 * @param userid
	 * @param num
	 * @return
	 */
	public boolean changeHandingTradeNum(String userid,Integer num){
		boolean flag = false;
		try {
			bsud.updateHandlingTradeNumWithUserId(num, userid);;
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
}

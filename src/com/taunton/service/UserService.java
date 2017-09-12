package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.taunton.dao.impl.BackStageUserDAOImpl;
import com.taunton.dao.impl.RechargeDAOImpl;
import com.taunton.dao.impl.ShoppingCartDAOImpl;
import com.taunton.dao.impl.UserDAOImpl;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Recharge;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.User;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.web.CriteriaUser;
import com.taunton.web.Page;

public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class); 

	//这里应使用工厂减少耦合
	private  UserDAOImpl ud = null;
	private  RechargeDAOImpl rd = null;
	private  ShoppingCartDAOImpl scd =null;
	public UserService() {
	    ud = DomainFactory.createDomainInstance(UserDAOImpl.class);
		rd = DomainFactory.createDomainInstance(RechargeDAOImpl.class);
		scd = DomainFactory.createDomainInstance(ShoppingCartDAOImpl.class);
	}
	
	/**
	 * 根据isBan获取用户page
	 * @param cu
	 * @return
	 */
	public Page<User> findPageByIsBan(CriteriaUser cu){
		Page<User> pages = null;
		try {
			pages = ud.getPageWithIsBan(cu);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return pages;
	}
	
	/**
	 * 修改用户封禁状态
	 * @param isBan
	 * @return
	 */
	public boolean ban(Integer isBan,String userid){
		boolean flag = false;
		try {
			ud.banUserWithUserId(isBan, userid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	public long findUserNumByIsBan(Integer isBan){
		long num = 0;
		CriteriaUser cu = new CriteriaUser(isBan, 1, null);
		try {
			num = ud.getTotalUserNumberWithIsBan(cu);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return num;
	}
	
	/**
	 * 注册普通用户
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public boolean regist(User user,ShoppingCart sc) {
			try {
				User user1 = ud.getUserWithUserName(user.getUsername());
				if(user1==null){
					ud.addUser(user);
					scd.addShoppingCart(sc);
				}else{
					return false;
				}
			} catch (SQLException e) {
				logger.error(e);
				throw new RuntimeException(e);
			}
		return true;
	}
	
	
	/**
	 * 登录用户
	 * @param username
	 * @param pwd
	 * @return
	 */
	public boolean login(String username,String pwd){
		String regex = "1[34578]\\d{9}";
		try {
				User user = null;
				if(username.matches(regex)){
					user = ud.getUserWithTel(username);
				}else{
					user = ud.getUserWithUserName(username);
				}
				if(user==null){
					return false;
				}
				String pwd2 = user.getPwd();
				if(pwd.equals(pwd2)){
					return true;
				}
		} catch (SQLException e) {
			logger.error("出现异常!"+"用户	username = "+username+"登录失败!");
			throw new RuntimeException(e);
		}
		return false;
		
	}
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username){
		User user = null;
		String regex = "1[34578]\\d{9}";
		try {
				if(username.matches(regex)){
					user = ud.getUserWithTel(username);
				}else{
					user =  ud.getUserWithUserName(username);
				}
				return user;
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
//		为什么不返回null？是因为在注册时调用此方法若出现异常返回null即表明此用户未曾注册，会返回校验成功的标志
	}
	/**
	 * 根据绑定手机号码查找用户
	 * @param username
	 * @return
	 */
	public User getUserByTel(String tel){
		try {
				return ud.getUserWithTel(tel);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		
	}	
	/**
	 * 获取用户被ban状态
	 * @param username
	 * @return
	 */
	public boolean getUserIsBanedByUsername(String username){
		int isBan = 1;
		String regex = "1[34578]\\d{9}";
		try {
			if(username.matches(regex)){
				isBan = ud.getIsBanWithUserName(username);
			}else{
				isBan = ud.getIsBanWithUserName(username);
			}
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		if(isBan==2){
			return false;
		}
		return true;
	}
	/**
	 * 获取用户被ban状态通过用户id
	 * @param username
	 * @return
	 */
	public boolean findIsBanByUserid(String userid){
		int isBan = 1;
		try {
			isBan = ud.getisBanWithUserId(userid);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		if(isBan==2){
			return false;
		}
		return true;
	}
	/**
	 * 修改密码
	 * @param userid
	 * @param newPwd
	 * @return
	 */
	public boolean changePwd(String userid,String newPwd){
		boolean flag = false;
		try {
			ud.updatePwdWithUserId(newPwd, userid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	/**
	 * 修改支付密码
	 * @param userid
	 * @param newPay_pwd
	 * @return
	 */
	public boolean changePay_pwd(String userid,String newPay_pwd){
		try {
			ud.updatePay_pwdWithUserId(newPay_pwd, userid);;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
	}
	/**
	 * 修改用户余额
	 * @param userid
	 * @param money
	 * @return
	 */
	public boolean changeBalance(String userid,float money){
		try {
			ud.updateBalance(userid, money);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
	}
	/**
	 * 修改用户名
	 * @param userid
	 * @param username
	 * @return
	 */
	public boolean changeUsername(String userid,String username){
		boolean flag = false;
		try {
			ud.updateUserNameWithUserId(username, userid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * 获取用户余额
	 * @param userid
	 * @return
	 */
	public float findBalance(String userid){
		float balance = 0;
		try {
			balance = ud.getBalanceWithUserId(userid);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return balance;
	}
	
	
	/**
	 * 充值
	 * @param userid
	 * @param rechargeid
	 * @throws SQLException 
	 */
	public boolean recharge(String userid,String rechargeid) throws SQLException{
		User user = null;
		Recharge recharge = null;
		try {
			user = ud.getUserWithUserId(userid);
			recharge = rd.getFaceValueWithRechargeid(rechargeid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		if(recharge==null){
			return false;
		}
		ud.updateBalance(userid, user.getBalance()+recharge.getFace_value());
		rd.deleteFaceValueWithRechargeid(rechargeid);
		return true;
	}
}

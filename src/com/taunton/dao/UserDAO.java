package com.taunton.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.Trade;
import com.taunton.javabean.User;
import com.taunton.web.CriteriaTrade;
import com.taunton.web.CriteriaUser;
import com.taunton.web.Page;

public interface UserDAO {
	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void addUser(User user) throws Exception;
	/**
	 * 根据用户id删除用户
	 * @param userid
	 */
	public void deleteUserWithUserId(String userid) throws Exception;
	/**
	 * 根据用户名删除用户
	 * @param username
	 */
	public void deleteUserWithUserName(String username) throws Exception;
	/**
	 * 更新指定用户的余额
	 * @param balance
	 */
	public void updateBalance(String userid,float money) throws Exception;
	/**
	 * 根据用户id更新用户
	 * @param user
	 */
	public void updateUserWithUserId(User user) throws Exception;
	/**
	 * 根据用户id更新用户名
	 * @param username
	 * @param userid
	 */
	public void updateUserNameWithUserId(String username,String userid) throws Exception;
	/**
	 * 根据用户id更新用户登录密码
	 * @param user
	 */
	public void updatePwdWithUserId(String pwd,String userid) throws Exception;
	/**
	 * 根据用户id更新用户支付密码
	 * @param user
	 */
	public void updatePay_pwdWithUserId(String pay_pwd,String userid) throws Exception;
	/**
	 * 根据用户id更新用户等级
	 * @param user
	 */
	public void updateLevelWithUserId(Integer level,String userid) throws Exception;
	/**
	 * 根据用户id更新用户头头像缓存路径
	 * @param headImgUrl
	 * @param userid
	 */
	public void updateHeadImgUrlWithUserId(String headImgUrl,String userid) throws Exception;
	/**
	 * 根据用户id拉黑用户
	 * @param user
	 */
	public void banUserWithUserId(Integer isBan,String userid) throws  Exception;
	/**
	 * 根据用户名拉黑用户
	 * @param isBan
	 * @param username
	 */
	public void banUserWithUserName(Integer isBan,String username) throws Exception;
	/**
	 * 根据用户id获取用户余额
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public float getBalanceWithUserId(String userid) throws Exception;
	/**
	 * 根据用户绑定手机号获取用户余额
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public float getBalanceWithTel(String tel) throws Exception;
	/**
	 * 根据用户名获取用户余额
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public float getBalanceWithUsername(String username) throws Exception;
	/**
	 * 根据用户所属的角色id获取用户集合
	 * @param roleid
	 * @return
	 */
	public Set<User> getUserWithRoleId(Integer roleid) throws Exception;
	/**
	 * 根据用户的用户等级获取用户集合
	 * @param level
	 * @return
	 */
	public Set<User> getUserWithLevel(int level) throws Exception;
	/**
	 * 根据用户id获取用户
	 * 
	 * @param userid
	 * @return
	 */
	public User getUserWithUserId(String userid) throws Exception;
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public User getUserWithUserName(String username) throws Exception;
	/**
	 * 根据用户名模糊查询获取用户集合
	 * @param username
	 * @return
	 */
	public Set<User> getUsersLikeUserName(String username) throws Exception;
	/**
	 * 根据用户绑定手机号码获取用户
	 * @param username
	 * @return
	 */
	public User getUserWithTel(String tel) throws Exception;
	/**
	 * 根据用户名获取用户被ban状态
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int getIsBanWithUserName(String username) throws Exception;
	/**
	 * 根据用户手机号码获取用户被ban状态
	 * @param tel
	 * @return
	 * @throws Exception
	 */
	public int getIsBanWithTel(String tel) throws Exception;
	/**
	 * 根据用户id获取用户被ban状态
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public int getisBanWithUserId(String userid) throws Exception;
	/**
	 * 获取全部用户
	 * @return
	 */
	public Set<User> getUsers() throws Exception;

	/**
	 * 根据用户是否被ban获取用户page
	 * @param cu
	 * @return
	 * @throws SQLException
	 */
	public Page<User> getPageWithIsBan(CriteriaUser cu) throws SQLException ;
	/**
	 * 根据用户是否被ban获取用户总数
	 * @param cu
	 * @return
	 * @throws SQLException
	 */
	public long getTotalUserNumberWithIsBan(CriteriaUser cu) throws SQLException ;
	/**
	 * 根据用户是否被ban获取用户集合
	 * @param cu
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public List<User> getPageListWithIsBan(CriteriaUser cu, int pageSize) throws SQLException ;
}

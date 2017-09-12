package com.taunton.dao;

import java.util.Set;

import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Handle;


public interface BackStageUserDAO {
	/**
	 * 添加后台用户
	 * @param user
	 * @return
	 */
	public void addUser(BackStageUser user) throws Exception;
	/**
	 * 根据后台用户id删除用户
	 * @param userid
	 */
	public void deleteUserWithUserId(String userid) throws Exception;
	/**
	 * 根据后台用户名删除用户
	 * @param username
	 */
	public void deleteUserWithUserName(String username) throws Exception;
	/**
	 * 根据后台用户id更新后台用户
	 * @param user
	 */
	public void updateUserWithUserId(BackStageUser user) throws Exception;
	/**
	 * 根据后台用户id更新后台用户密码
	 * @param user
	 */
	public void updatePwdWithUserId(String pwd,String userid) throws Exception;
	/**
	 * 根据后台用户id更新后台用户所属角色id
	 * @param user
	 */
	public void updateRoleIdWithUserId(Integer roleid,String userid) throws Exception;
	/**
	 * 根据后台用户id更新后台用户已处理订单数量
	 * @param handledTradeNum
	 * @param userid
	 */
	public void updateHandledTradeNumWithUserId(Integer handledTradeNum,String userid) throws Exception;
	/**
	 * 根据后台用户id更新后台用户正在处理订单数量
	 * @param handlingTradeNUm
	 * @param userid
	 */
	public void updateHandlingTradeNumWithUserId(Integer handlingTradeNum,String userid) throws Exception;
	/**
	 * 根据后台用户id更新后台用户头像缓存路径
	 * @param headImgUrl
	 * @param userid
	 */
	public void updateHeadImgUrlWithUserId(String headImgUrl,String userid) throws Exception;
	/**
	 * 根据用户名获取用户是否被ban
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Integer getIsBanWithUsername(String username) throws Exception;
	/**
	 * 根据后台用户id获取用户
	 * @param userid
	 * @return
	 */
	public BackStageUser getUserWithUserId(String userid) throws Exception;
	/**
	 * 根据后台用户名获取用户
	 * @param username
	 * @return
	 */
	public BackStageUser getUserWithUserName(String username) throws Exception;
	/**
	 * 根据后台用户绑定手机号获取用户
	 * @param tel
	 * @return
	 */
	public BackStageUser getUserWithTel(String tel) throws Exception;
	/**
	 * 根据后台用户真实姓名获取用户集合
	 * @param realname
	 * @return
	 */
	public Set<BackStageUser> getUserWithRealName(String realname) throws Exception;
	/**
	 * 根据后台用户所属角色id获取用户集合
	 * @param roleid
	 * @return
	 */
	public Set<BackStageUser> getUserWithRoleId(Integer roleid) throws Exception;
	/**
	 * 查询进行中任务比最小的后台人员的集合
	 * @return
	 * @throws Exception
	 */
	public Set<BackStageUser> getUser4AssignTrade() throws Exception;
	/**
	 * 获取后台用户集合
	 * @return
	 */
	public Set<BackStageUser> getUsers() throws Exception;
	/**
	 * 获取在订单处理记录里没有处理记录的用户集合
	 * @param operate
	 * @param status
	 * @param isValid
	 * @return
	 * @throws Exception
	 */
	public Set<BackStageUser> getEmptyHandleBackStageUserId(Integer operate,Integer status,Integer isValid) throws Exception;
}

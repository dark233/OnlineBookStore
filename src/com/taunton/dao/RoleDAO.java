package com.taunton.dao;

import com.taunton.javabean.Role;

public interface RoleDAO {
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public boolean addRole(Role role) throws Exception;
	/**
	 * 根据角色id删除角色
	 * @param roleid
	 */
	public void deleteRoleWithRoleId(Integer roleid) throws Exception;
	/**
	 * 根据角色名删除角色
	 * @param rolename
	 */
	public void deleteRoleWithRoleName(String rolename) throws Exception;
	/**
	 * 更新角色
	 * @param role
	 */
	public void updateRoleWithRoleId(Role role) throws Exception;
	/**
	 * 根据角色id更新角色名称
	 * @param RoleName
	 */
	public void updateRoleNameWithRoleId(String rolename,Integer roleid) throws Exception;
	/**
	 * 根据角色id更新角色功能权限
	 * @param fun_per
	 */
	public void updateFun_perWithRoleId(String fun_per,Integer roleid) throws Exception;
	/**
	 * 根据角色id获取角色
	 * @param roleid
	 */
	public Role getRoleWithRoleId(Integer roleid) throws Exception;
	/**
	 * 根据角色名称获取角色
	 * @param rolename
	 */
	public Role getRoleWithRoleName(String rolename) throws Exception;
	
}

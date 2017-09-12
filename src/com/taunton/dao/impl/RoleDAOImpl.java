package com.taunton.dao.impl;

import java.sql.SQLException;

import com.taunton.dao.RoleDAO;
import com.taunton.javabean.Role;

public class RoleDAOImpl extends BaseDAO<Role> implements RoleDAO {

	@Override
	public boolean addRole(Role role) throws SQLException {
		String sql = "INSERT INTO role(rolename,fun_per) VALUES(?,?)";
		Object[] obj = {role.getRolename(),role.getFun_per()};
		long id = insert(sql, obj);
		if(id>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void deleteRoleWithRoleId(Integer roleid) throws SQLException {
		String sql = "DELETE FROM role WHERE roleid = ?";
		update(sql, roleid);
	}

	@Override
	public void deleteRoleWithRoleName(String rolename) throws SQLException {
		String sql = "DELETE FROM role WHERE rolename = ?";
		update(sql, rolename);
	}

	@Override
	public void updateRoleWithRoleId(Role role) throws SQLException {
		String sql = "UPDATE role SET rolename = ?,fun_per = ? WHERE roleid = ?";
		Object[] obj = {role.getRolename(),role.getFun_per(),role.getRoleid()};
		update(sql, obj);
	}

	@Override
	public void updateRoleNameWithRoleId(String rolename,Integer roleid) throws SQLException {
		String sql = "UPDATE role SET rolename = ? WHERE roleid = ?";
		Object[] obj = {rolename,roleid};
		update(sql, obj);
	}

	@Override
	public void updateFun_perWithRoleId(String fun_per,Integer roleid) throws SQLException {
		String sql = "UPDATE role SET fun_per = ? WHERE roleid = ?";
		Object[] obj = {fun_per,roleid};
		update(sql, obj);
	}

	@Override
	public Role getRoleWithRoleId(Integer roleid) throws SQLException {
		String sql = "SELECT roleid,rolename,fun_per FROM role WHERE roleid = ?";
		return query(sql, roleid);
	}

	@Override
	public Role getRoleWithRoleName(String rolename) throws SQLException {
		String sql = "SELECT roleid,rolename,fun_per FROM role WHERE rolename = ?";
		return query(sql, rolename);
	}

}

package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.taunton.dao.BackStageUserDAO;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Handle;

public class BackStageUserDAOImpl extends BaseDAO<BackStageUser> implements
		BackStageUserDAO {

	@Override
	public void addUser(BackStageUser user) throws SQLException {
		String sql = "INSERT INTO backstageuser(userid,username,realname,pwd,tel,headimgurl,handledtradenum,handlingtradenum,roleid,registDate,isban,email) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {user.getUserid(),user.getUsername(),user.getRealname(),user.getPwd(),user.getTel(),user.getHeadImgUrl(),user.getHandledTradeNum(),
				user.getHandlingTradeNum(),user.getRoleid(),user.getRegistDate(),user.getIsBan(),user.getEmail()};
		update(sql, obj);
	}

	@Override
	public void deleteUserWithUserId(String userid) throws SQLException {
		String sql = "DELETE FROM backstageuser WHERE userid = ?";
		update(sql, userid);
	}

	@Override
	public void deleteUserWithUserName(String username) throws SQLException {
		String sql = "DELETE FROM backstageuser WHERE username = ?";
		update(sql, username);
	}

	@Override
	public void updateUserWithUserId(BackStageUser user) throws SQLException {
		String sql = "UPDATE backstageuser SET username = ?,realname = ?,tel = ? WHERE userid = ?";
		Object[] obj = {user.getUsername(),user.getRealname(),user.getTel(),user.getUserid()};
		update(sql, obj);
	}
	
	@Override
	public void updatePwdWithUserId(String pwd,String userid) throws SQLException {
		String sql = "UPDATE backstageuser SET pwd = ? WHERE userid = ?";
		Object[] obj = {pwd,userid};
		update(sql, obj);
	}

	@Override
	public void updateRoleIdWithUserId(Integer roleid,String userid) throws SQLException {
		String sql = "UPDATE backstageuser SET roleid = ? WHERE userid = ?";
		Object[] obj = {roleid,userid};
		update(sql, obj);
	}
	
	@Override
	public void updateHandledTradeNumWithUserId(Integer handledTradeNum,String userid) throws SQLException {
		String sql = "UPDATE backstageuser SET handledtradenum = handledtradenum+? WHERE userid = ?";
		Object[] obj = {handledTradeNum,userid};
		update(sql, obj);
	}
	
	@Override
	public void updateHandlingTradeNumWithUserId(Integer handlingTradeNum,String userid) throws SQLException{
		String sql = "UPDATE backstageuser SET handledtradenum = handlingtradenum+? WHERE userid = ?";
		Object[] obj = {handlingTradeNum,userid};
		update(sql, obj);
	}
	
	@Override
	public void updateHeadImgUrlWithUserId(String headImgUrl, String userid) throws SQLException {
		String sql = "UPDATE backstageuser SET headimgurl = ? WHERE userid = ?";
		Object[] obj = {headImgUrl,userid};
		update(sql, obj);
		
	}
	
	@Override
	public BackStageUser getUserWithUserId(String userid) throws SQLException {
		String sql = "SELECT userid, username,realname, pwd,tel,headImgUrl,handledTradeNum,handlingTradeNum,roleid,registDate,isBan,email FROM backstageuser " +
				"WHERE userid = ?";
		return query(sql, userid);
	}

	@Override
	public BackStageUser getUserWithUserName(String username) throws SQLException {
		String sql = "SELECT userid, username,realname, pwd,tel,headImgUrl,handledTradeNum,handlingTradeNum,roleid,registDate,isBan,email FROM backstageuser " +
				"WHERE username = ?";
		return query(sql, username);
	}

	@Override
	public Set<BackStageUser> getUserWithRealName(String realname) throws SQLException {
		String sql = "SELECT userid, username,realname, pwd,tel,headImgUrl,handledTradeNum,handlingTradeNum,roleid,registDate,isBan,email FROM backstageuser " +
				"WHERE realname = ? ORDER BY userid DESC";
		return new LinkedHashSet<>(queryList(sql,realname));
	}

	@Override
	public Set<BackStageUser> getUserWithRoleId(Integer roleid) throws SQLException {
		String sql = "SELECT userid, username,realname, pwd,tel,headImgUrl,handledTradeNum,handlingTradeNum,roleid,registDate,isBan,email FROM backstageuser " +
				"WHERE roleid = ? ORDER BY userid DESC";
		return new LinkedHashSet<>(queryList(sql,roleid));
	}

	@Override
	public Set<BackStageUser> getUsers() throws SQLException {
		String sql = "SELECT userid, username,realname, pwd,tel,headImgUrl,handledTradeNum,handlingTradeNum,roleid,registDate,isBan,email FROM backstageuser "
				+ "ORDER BY userid DESC";
		return new LinkedHashSet<>(queryList(sql));
		
	}

	@Override
	public BackStageUser getUserWithTel(String tel) throws SQLException {
		String sql = "SELECT userid, username,realname, pwd,tel,headImgUrl,handledTradeNum,handlingTradeNum,roleid,registDate,isBan,email FROM backstageuser " +
				"WHERE tel = ?";
		return query(sql, tel);
	}
	
	@Override
	public Set<BackStageUser> getEmptyHandleBackStageUserId(Integer operate,Integer status,Integer isValid) throws Exception {
		String sql = "SELECT userid FROM "
				+ "(SELECT backstageuserid FROM handle WHERE operate = ? AND status_ = ? AND isValid = ? GROUP BY backstageuserid) a RIGHT OUTER JOIN backstageuser ON a.backstageuserid=backstageuser.userid "
				+ "WHERE a.backstageuserid IS NULL";
		Object[] params = {operate,status,isValid};
		return new LinkedHashSet<>(queryList(sql, params));
	}

	@Override
	public Set<BackStageUser> getUser4AssignTrade() throws Exception {
		return null;
	}

	@Override
	public Integer getIsBanWithUsername(String username) throws Exception {
		String sql = "SELECT isBan FROM backstageuser WHERE username = ? " ;
		Number number = querySingle(sql, username);
		return number.intValue();
	}

}

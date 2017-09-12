package com.taunton.dao.impl;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.taunton.dao.UserDAO;
import com.taunton.javabean.Handle;
import com.taunton.javabean.Trade;
import com.taunton.javabean.User;
import com.taunton.web.CriteriaUser;
import com.taunton.web.Page;



public class UserDAOImpl extends BaseDAO<User>  implements UserDAO {

	@Override
	public void addUser(User user) throws SQLException {
		String sql = "INSERT INTO user(userid,username,pwd,pay_pwd,balance,tel,level,roleid,isBan,registDate,email) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {user.getUserid(),user.getUsername(),user.getPwd(),user.getPay_pwd(),user.getBalance(),user.getTel(),user.getLevel(),user.getRoleid(),
				user.getIsBan(),user.getRegistDate(),user.getEmail()};
		update(sql, obj);
	}

	@Override
	public void deleteUserWithUserId(String userid) throws SQLException {
		String sql = "DELETE FROM user WHERE userid = ?";
		update(sql, userid);
	}

	@Override
	public void deleteUserWithUserName(String username) throws SQLException {
		String sql = "DELETE FROM user WHERE username = ?";
		update(sql, username);
	}
	
	@Override
	public void updateUserNameWithUserId(String username, String userid) throws SQLException {
		String sql = "UPDATE user SET username = ? WHERE userId = ?";
		Object[] obj = {username,userid};
			update(sql, obj);
	}
	
	@Override
	public void updateBalance(String userid,float money) throws SQLException {
		String sql = "UPDATE user SET balance = balance + ? WHERE userId = ?";
		Object[] obj = {money,userid};
			update(sql, obj);
	}
	
	@Override
	public void updatePwdWithUserId(String pwd,String userid) throws SQLException {
		String sql = "UPDATE user SET pwd = ? WHERE userid = ? ";
		Object[] obj = {pwd,userid};
		update(sql, obj);
	}


	@Override
	public void updatePay_pwdWithUserId(String pay_pwd,String userid) throws SQLException {
		String sql = "UPDATE user SET pay_pwd = ? WHERE userid = ? ";
		Object[] obj = {pay_pwd,userid};
		update(sql, obj);
	}

	@Override
	public void updateLevelWithUserId(Integer level,String userid) throws SQLException {
		String sql = "UPDATE user SET level = ? WHERE userid = ? ";
		Object[] obj = {level,userid};
		update(sql, obj);
	}
	
	@Override
	public void updateHeadImgUrlWithUserId(String headImgUrl, String userid) throws SQLException {
		String sql = "UPDATE user SET headimgurl = ? WHERE userid = ? ";
		Object[] obj = {headImgUrl,userid};
		update(sql, obj);
	}
	
	@Override
	public void banUserWithUserId(Integer isBan,String userid) throws SQLException {
		String sql = "UPDATE user SET isBan = COALESCE(?,isban) WHERE userid = ?";
		Object[] obj = {isBan,userid};
		update(sql, obj);
	}
	
	@Override
	public void banUserWithUserName(Integer isBan, String username) throws SQLException {
		String sql = "UPDATE user SET isBan = ? WHERE username = ?";
		Object[] obj = {isBan,username};
		update(sql, obj);
	}
	
	@Override
	public void updateUserWithUserId(User user) throws SQLException {
		String sql = "UPDATE user SET username = ?,pwd = ?,pay_pwd = ?,tel = ? WHERE userid = ?";
		Object[] obj = {user.getUsername(),user.getPwd(),user.getPay_pwd(),user.getTel(),user.getUserid()};
		update(sql, obj);
	}
	
	@Override
	public User getUserWithUserId(String userid) throws SQLException {
		String sql = "SELECT userid, username, pwd,pay_pwd,balance,tel,level,headimgurl,roleid,isBan,registDate,email FROM user WHERE userid = ?";
		return query(sql, userid);
	}

	@Override
	public User getUserWithUserName(String username) throws SQLException {
		String sql = "SELECT userid, username, pwd,pay_pwd,balance,tel,level,headimgurl,roleid,isBan,registDate,email FROM user WHERE username =  ?";
		return query(sql, username);
	}
	

	@Override
	public Set<User> getUsersLikeUserName(String username) throws SQLException {
		String sql = "SELECT userid, username, pwd,pay_pwd,balance,tel,level,headimgurl,roleid,isBan,registDate,email FROM user WHERE username like '%?%' ORDER BY userid DESC";
		return new LinkedHashSet<>(queryList(sql));
	}

	@Override
	public Set<User> getUsers() throws SQLException {
		String sql = "SELECT userid, username, pwd,pay_pwd,balance,tel,level,headimgurl,roleid,isBan,registDate,email FROM user ORDER BY userid DESC";
		return new LinkedHashSet<>(queryList(sql));
	}
	
	@Override
	public Set<User> getUserWithRoleId(Integer roleid) throws SQLException {
		String sql = "SELECT userid, username, pwd,pay_pwd,balance,tel,level,headimgurl,roleid,isBan,registDate,email FROM user WHERE roleid =  ? ORDER BY userid DESC ";
		return new LinkedHashSet<>(queryList(sql,roleid));
	}

	@Override
	public Set<User> getUserWithLevel(int level) throws SQLException {
		String sql = "SELECT userid, username, pwd,pay_pwd,balance,tel,level,headimgurl,roleid,isBan,registDate,email FROM user WHERE level =  ? ORDER BY userid DESC";
		return new LinkedHashSet<>(queryList(sql,level));
	}

	@Override
	public User getUserWithTel(String tel) throws SQLException {
		String sql = "SELECT userid, username, pwd,pay_pwd,balance,tel,level,headimgurl,roleid,isBan,registDate,email FROM user WHERE tel =  ?";
		return query(sql, tel);
	}

	@Override
	public int getIsBanWithUserName(String username) throws Exception {
		String sql = "SELECT isBan FROM user WHERE username = ?";
		Integer i = querySingle(sql, username);
		int isBan = 2;
		if(i!=null){
			isBan = i;
		}
		return isBan;
	}
	
	@Override
	public int getIsBanWithTel(String tel) throws Exception {
		String sql = "SELECT isBan FROM user WHERE tel = ?";
		Integer i = querySingle(sql, tel);
		int isBan = 1;
		if(i!=null){
			isBan = i;
		}
		return isBan;
	}
	
	@Override
	public int getisBanWithUserId(String userid) throws Exception {
		String sql = "SELECT isBan FROM user WHERE userid = ?";
		Integer i = querySingle(sql, userid);
		int isBan = 1;
		if(i!=null){
			isBan = i;
		}
		return isBan;
	}

	@Override
	public float getBalanceWithUserId(String userid) throws Exception {
		String sql = "SELECT balance FROM user WHERE userid = ?";
		BigDecimal bd = querySingle(sql, userid);
		float balance = bd.floatValue();
		return balance;
	}

	@Override
	public float getBalanceWithTel(String tel) throws Exception {
		String sql = "SELECT balance FROM user WHERE tel = ?";
		BigDecimal bd = querySingle(sql, tel);
		float balance = bd.floatValue();
		return balance;
	}

	@Override
	public float getBalanceWithUsername(String username) throws Exception {
		String sql = "SELECT balance FROM user WHERE username = ?";
		BigDecimal bd = querySingle(sql, username);
		float balance = bd.floatValue();
		return balance;
	}

	@Override
	public Page<User> getPageWithIsBan(CriteriaUser cu) throws SQLException {
		Page<User> page = new Page<>(cu.getPageNo());
		page.setPageSize(5);
		page.setTotalItemNumber(getTotalUserNumberWithIsBan(cu));
		//在这里setPageNO是为了避免出现传入的pageNo比查出来的总共的pageNO还大的情况（在page里的getPageNO进行了处理）
		cu.setPageNo(page.getPageNo());
		page.setList(getPageListWithIsBan(cu, page.getPageSize()));
		return page;
	}

	@Override
	public long getTotalUserNumberWithIsBan(CriteriaUser cu)
			throws SQLException {
		String sql = "SELECT count(*) FROM user WHERE isban = COALESCE(?,isban) ";
		return querySingle(sql, cu.getIsBan());
	
	}

	@Override
	public List<User> getPageListWithIsBan(CriteriaUser cu, int pageSize)
			throws SQLException {
		String sql = "SELECT userid,username,pwd,pay_pwd,balance,tel,level,headImgUrl,roleid,isBan,registDate,email FROM "
				+ "(SELECT userid,username,pwd,pay_pwd,balance,tel,level,headImgUrl,roleid,isBan,registDate,email FROM user "
				+ "WHERE isban = COALESCE(?,isban) "
				+ " ORDER BY registDate DESC) a "
				+ "limit ?,?";
		Object[] obj = {cu.getIsBan(),(cu.getPageNo() - 1) * pageSize, pageSize};
		return queryList(sql, obj);	
		
		}

	


}

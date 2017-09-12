package com.taunton.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.taunton.dao.HandleDAO;
import com.taunton.dao.RemarkDAO;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Book;
import com.taunton.javabean.Handle;
import com.taunton.javabean.Remark;
import com.taunton.javabean.Trade;
import com.taunton.utils.ConnectionContext;
import com.taunton.utils.JDBCUtils;
import com.taunton.web.CriteriaHandle;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public class HandleDAOImpl extends BaseDAO<Handle> implements HandleDAO {

	@Override
	public void addHandle(Handle handle) throws SQLException {
		String sql = "INSERT INTO handle(handleid,tradeid,backstageuserid,operate,status_,isValid,createTime,updateTime) VALUES(?,?,?,?,?,?,?,?)";
		Object[] obj = {handle.getHandleid(),handle.getTradeid(),handle.getBackStageUserid(),handle.getOperate(),handle.getStatus_(),handle.getIsValid(),handle.getCreateTime(),handle.getUpdateTime()};
		update(sql, obj);
	}

	@Override
	public void deleteHandle(String handleid) throws SQLException {
		String sql = "DELETE FROM handle WHERE handleid = ?";
		update(sql, handleid);
	}

	@Override
	public void updateIsValidWithOperateAndStatusAndTradeIdTradeId(
			Integer isValid,Integer operate, Integer status,Integer isValid_ ,String tradeid,String updateTime)
			throws SQLException {
		String sql = "UPDATE handle SET isValid = ?,updateTime = ? WHERE operate = COALESCE(?,operate) AND status_ = COALESCE(?,status_) AND isValid = COALESCE(?,isValid) AND tradeid = ?";
		Object[] obj = {isValid,updateTime,operate,status,isValid_,tradeid};
		update(sql, obj);
	}
	
	@Override
	public void updateHandleStatusWithHandleId(Integer status_,String updateTime,String handleid)
			throws SQLException {
		String sql = "UPDATE handle SET status_ = ?,updateTime = ? WHERE handleid = ?";
		Object[] obj = {status_,updateTime,handleid};
		update(sql, obj);
	}

	@Override
	public void updateUpdateTimeWithHandleId(String updateTime, String handleid)
			throws SQLException {
		String sql = "UPDATE handle SET updateTime = ? WHERE handleid = ?";
		Object[] obj = {updateTime,handleid};
		update(sql, obj);
	}
	
	@Override
	public Long getNumberWithOperateAndStatusAndIsValidAndUserId(
			Integer operate, Integer status, Integer isValid, String userid)
			throws SQLException {
		String sql = "SELECT COUNT(*) FROM "
				+ "(SELECT tradeid FROM trade WHERE userid = ?) a JOIN handle ON a.tradeid = handle.tradeid "
				+ "WHERE operate = COALESCE(?,operate) AND status_ = COALESCE(?,status_) AND COALESCE(?,isValid)";
		Object[] params = {userid,operate,status,isValid};
		return querySingle(sql, params);
	}
	
	@Override
	public Long getNumberWithOperateAndStatusAndIsValidAndTradeId(
			Integer operate, Integer status, Integer isValid, String tradeid)
			throws SQLException {
		String sql = "SELECT COUNT(*) FROM handle "
				+ "WHERE operate = COALESCE(?,operate) AND status_ = COALESCE(?,status_) AND COALESCE(?,isValid) AND tradeid = ?";
		Object[] params = {operate,status,isValid,tradeid};
		return querySingle(sql, params);
	}
	
	@Override
	public Handle getHandleWithHandleId(String handleid) throws SQLException {
		String sql = "SELECT handleid,tradeid,backstageuserid,operate,status_,isValid,createTime,updateTime FROM handle WHERE handleid = ?";
		return query(sql, handleid);
	}

	@Override
	public Set<Handle> getHandleListWithOperateAndStatusAndIsValidAndTradeId(
			Integer operate, Integer status, Integer isValid, String tradeid)
			throws SQLException {
		String sql = "SELECT handleid,tradeid,backstageuserid,operate,status_,isValid,createTime,updateTime FROM handle WHERE operate = COALESCE(?,operate) AND "
				+ "status_ = COALESCE(?,status_) AND isValid = COALESCE(?,isValid) AND tradeid = ? ORDER BY createTime ASC";
		Object[] params = {operate,status,isValid,tradeid};
		return new LinkedHashSet<>(queryList(sql, params));
	}
	
	@Override
	public Set<Handle> getHandleListWithOperateAndStatusAndIsValidAndBackStageUserId(Integer operate,
			Integer status_,Integer isValid,String backStageUserid) throws SQLException {
		String sql = "SELECT handleid,tradeid,backstageuserid,operate,status_,isValid,createTime,updateTime FROM handle WHERE operate = COALESCE(?,operate) AND "
				+ "status_ = COALESCE(?,status_) AND isValid = COALESCE(?,isValid) AND backstageuserid = ? ORDER BY createTime DESC";
		Object[] params = {operate,status_,isValid,backStageUserid};
		return new LinkedHashSet<>(queryList(sql, params));
	}
	
	@Override
	public Handle getRecentlyHandleWithOperateAndStatusAndIsValidAndTradeId(
			Integer operate, Integer status, Integer isValid, String tradeid)
			throws SQLException {
		String sql = "SELECT handleid,handle.tradeid tradeid,backstageuserid,operate,status_,isValid,createTime,updateTime FROM "
				+ "(SELECT tradeid,MAX(createTime) maxCTime FROM "
				+ "handle WHERE operate = COALESCE(?,operate) AND status_ = COALESCE(?,status_) AND isValid = COALESCE(?,isValid) GROUP BY tradeid HAVING tradeid = ?) a "
				+ "JOIN handle ON a.tradeid = handle.tradeid "
				+ "WHERE createTime >= maxCTime  AND operate = COALESCE(?,operate) AND status_ = COALESCE(?,status_) AND isValid = COALESCE(?,isValid)";
		Object[] params = {operate,status,isValid,tradeid,operate,status,isValid};
		return query(sql, params);
	}
	
	@Override
	public double getMinRatio(Integer operate) throws SQLException {
//		String sql = "SELECT COALESCE(MIN(a.handling/b.allhandle),0) minratio FROM"
//				+ "(SELECT backstageuserid,COUNT(handleid) handling FROM handle WHERE operate = ? AND status_ = 2 GROUP BY backstageuserid) a JOIN "
//				+ "(SELECT backstageuserid,COUNT(handleid) allhandle FROM handle WHERE operate = ? GROUP BY backstageuserid) b "
//				+ "ON a.backstageuserid = b.backstageuserid";
//		Object[] params = {operate,operate};
//		Number number = querySingle(sql,params);
//		return number.doubleValue();
		
		String sql = "SELECT COALESCE(MIN(a.handling),0) minratio FROM"
				+ "(SELECT backstageuserid,COUNT(handleid) handling FROM handle WHERE operate = ? AND status_ = 2 AND isValid = 1 GROUP BY backstageuserid) a ";
		Number number = querySingle(sql,operate);
		return number.doubleValue();
	}
	@Override
	public double getMinCloseTime(Integer operate) throws SQLException {
//		double minRatio = getMinRatio(operate);
//		String sql = "SELECT COALESCE(MIN(closetime),0) FROM "
//				+ "(SELECT c.backstageuserid backstageuserid,MAX(UNIX_TIMESTAMP(createTime)) closetime FROM "
//				+ "(SELECT a.backstageuserid backstageuserid FROM"
//				+ "(SELECT backstageuserid,COUNT(handleid) handling FROM handle WHERE operate = ? AND status_ = 2 AND isValid = 1 GROUP BY backstageuserid) a JOIN "
//				+ "(SELECT backstageuserid,COUNT(handleid) allhandle FROM handle WHERE operate = ? AND isValid = 1 GROUP BY backstageuserid) b "
//				+ "ON a.backstageuserid = b.backstageuserid WHERE ? >= (a.handling/b.allhandle)"
//				+ ") c JOIN handle ON c.backstageuserid = handle.backstageuserid WHERE operate = ? AND isValid = 1 GROUP BY c.backstageuserid) d ";
//		Object[] params = {operate,operate,minRatio,operate};
//		Number number = querySingle(sql, params);
//		return number.doubleValue();
		
		double minRatio = getMinRatio(operate);
		String sql = "SELECT COALESCE(MIN(closetime),0) FROM "
				+ "(SELECT b.backstageuserid backstageuserid,MAX(UNIX_TIMESTAMP(createTime)) closetime FROM "
				+ "(SELECT a.backstageuserid backstageuserid FROM"
				+ "(SELECT backstageuserid,COUNT(handleid) handling FROM handle WHERE operate = ? AND status_ = 2 AND isValid = 1 GROUP BY backstageuserid) a "
				+ "WHERE a.handling <= ?"
				+ ") b JOIN handle ON b.backstageuserid = handle.backstageuserid WHERE operate = ? AND isValid = 1 GROUP BY b.backstageuserid) c ";
		Object[] params = {operate,minRatio,operate};
		Number number = querySingle(sql, params);
		return number.doubleValue();
	}
	@Override
	public Set<Handle> getAssignedBackStageUserId4Trade(Integer operate) throws SQLException {
//		double minRatio = getMinRatio(operate);
//		double minCloseTime = getMinCloseTime(operate);
//		String sql = "SELECT backstageuserid FROM "
//				+ "(SELECT c.backstageuserid backstageuserid,MAX(UNIX_TIMESTAMP(createTime)) closetime FROM "
//				+ "(SELECT a.backstageuserid backstageuserid FROM"
//				+ "(SELECT backstageuserid,COUNT(handleid) handling FROM handle WHERE operate = ? AND status_ = 2 AND isValid = 1 GROUP BY backstageuserid) a JOIN "
//				+ "(SELECT backstageuserid,COUNT(handleid) allhandle FROM handle WHERE operate = ? AND isValid = 1 GROUP BY backstageuserid) b "
//				+ "ON a.backstageuserid = b.backstageuserid WHERE ? >= (a.handling/b.allhandle)"
//				+ ") c JOIN handle ON c.backstageuserid = handle.backstageuserid WHERE operate = ? AND isValid = 1 GROUP BY c.backstageuserid) d WHERE closetime <= ?";
//		Object[] params = {operate,operate,minRatio,operate,minCloseTime};
//		return new LinkedHashSet<>(queryList(sql, params));
		
		double minRatio = getMinRatio(operate);
		double minCloseTime = getMinCloseTime(operate);
		String sql = "SELECT backstageuserid FROM "
				+ "(SELECT b.backstageuserid backstageuserid,MAX(UNIX_TIMESTAMP(createTime)) closetime FROM "
				+ "(SELECT a.backstageuserid backstageuserid FROM"
				+ "(SELECT backstageuserid,COUNT(handleid) handling FROM handle WHERE operate = ? AND status_ = 2 AND isValid = 1 GROUP BY backstageuserid) a "
				+ "WHERE a.handling <= ?"
				+ ") b JOIN handle ON b.backstageuserid = handle.backstageuserid WHERE operate = ? AND isValid = 1 GROUP BY b.backstageuserid) c WHERE closetime <= ?";
		Object[] params = {operate,minRatio,operate,minCloseTime};
		return new LinkedHashSet<>(queryList(sql, params));
	}

	@Override
	public Page<Handle> getPageWithStatusAndOperate(CriteriaHandle ch)
			throws SQLException {
		Page<Handle> page = new Page<>(ch.getPageNo());
		page.setPageSize(5);
		page.setTotalItemNumber(getTotalHandleNumberWithStatusAndOperate(ch));
		//在这里setPageNO是为了避免出现传入的pageNo比查出来的总共的pageNO还大的情况（在page里的getPageNO进行了处理）
		ch.setPageNo(page.getPageNo());
		page.setList(getPageListWithStatusAndOperate(ch, page.getPageSize()));
		return page;
	}

	@Override
	public long getTotalHandleNumberWithStatusAndOperate(CriteriaHandle ch)
			throws SQLException {
		String sql = "SELECT count(*) FROM handle WHERE isValid = COALESCE(?,isValid) AND status_ = COALESCE(?,status_) AND operate = COALESCE(?,operate) "
				+ "AND backstageuserid = ?";
		Object[] obj = {ch.getIsValid(),ch.getHandleStatus(),ch.getOperate(),ch.getBackStageUserid()};
		return querySingle(sql, obj);
	
	}

	@Override
	public List<Handle> getPageListWithStatusAndOperate(CriteriaHandle ch, int pageSize)
			throws SQLException {
		String sql = "SELECT handleid,tradeid,backStageUserid,operate,status_,isValid,createTime,updateTime FROM "
				+ "(SELECT handleid,tradeid,backStageUserid,operate,status_,isValid,createTime,updateTime FROM handle "
				+ "WHERE isValid = COALESCE(?,isValid) AND status_ = COALESCE(?,status_) AND operate = COALESCE(?,operate) AND backstageuserid = ? "
				+ " ORDER BY createTime DESC) a "
				+ "limit ?,?";
		Object[] obj = {ch.getIsValid(),ch.getHandleStatus(),ch.getOperate(),ch.getBackStageUserid(),(ch.getPageNo() - 1) * pageSize, pageSize};
		return queryList(sql, obj);	
		
		}

}

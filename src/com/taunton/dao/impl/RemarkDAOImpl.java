package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.taunton.dao.RemarkDAO;
import com.taunton.javabean.Book;
import com.taunton.javabean.Remark;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public class RemarkDAOImpl extends BaseDAO<Remark> implements RemarkDAO {

	@Override
	public void addRemark(Remark remark) throws SQLException {
		String sql = "INSERT INTO remark(remarkid,bookid,userid,tradeItemId,remark,remarkImgUrl,remarkDate,remarkLevel,isShow) VALUES(?,?,?,?,?,?,?,?,?)";
		Object[] obj = {remark.getRemarkid(),remark.getBookid(),remark.getUserid(),remark.getTradeItemid(),remark.getRemark(),
				remark.getRemarkImgUrl(),remark.getRemarkDate(),remark.getRemarkLevel(),remark.getIsShow()};
		update(sql, obj);
	}
	
	@Override
	public void addRemarkList(Collection<Remark> coll) throws SQLException {
		String sql = "INSERT INTO remark(remarkid,bookid,userid,tradeItemId,remark,remarkImgUrl,remarkDate,remarkLevel,isShow) VALUES(?,?,?,?,?,?,?,?,?)";
		List<Remark> list = new ArrayList<>(coll);
		Object[][] params = new Object[list.size()][9];
		for(int i = 0;i<list.size();i++){
			params[i][0] = list.get(i).getRemarkid();
			params[i][1] = list.get(i).getBookid();
			params[i][2] = list.get(i).getUserid();
			params[i][3] = list.get(i).getTradeItemid();
			params[i][4] = list.get(i).getRemark();
			params[i][5] = list.get(i).getRemarkImgUrl();
			params[i][6] = list.get(i).getRemarkDate();
			params[i][7] = list.get(i).getRemarkLevel();
			params[i][8] = list.get(i).getIsShow();
		}
		batch(sql, params);
	}

	@Override
	public void deleteRemarkWithRemarkId(String remarkid) throws SQLException {
		String sql = "DELETE FROM remark WHERE remarkid = ?";
		update(sql, remarkid);
	}

	@Override
	public void deleteRemarkWithUserId(String userid) throws SQLException {
		String sql = "DELETE FROM remark WHERE userid = ?";
		update(sql, userid);
	}

	@Override
	public void deleteRemarkWithBookId(String bookid) throws SQLException {
		String sql = "DELETE FROM remark WHERE bookid = ?";
		update(sql, bookid);
	}

	@Override
	public void updateRemarkWithRemarkId(Remark remark) throws SQLException {
		String sql = "UPDATE remark SET remark = ?,remarkLevel = ? WHERE remarkid = ?";
		Object[] obj = {remark.getRemark(),remark.getRemarkLevel(),remark.getRemarkid()};
		update(sql, obj);
	}
	
	@Override
	public void updateIsShowWithRemarkId(int isShow,String remarkid) throws Exception {
		String sql = "UPDATE remark SET isShow = ? WHERE remarkid = ?";
		Object[] obj = {isShow,remarkid};
		update(sql, obj);
	}

	@Override
	public Remark getRemarkWithRemarkId(String remarkid) throws SQLException {
		String sql = "SELECT remarkid,bookid,userid,tradeItemid,remark,remarkImgUrl,remarkDate,remarkLevel,isShow FROM remark WHERE remarkid = ?";
		Remark remark = query(sql, remarkid);
		return remark;
	}

	@Override
	public Set<Remark> getRemarkWithUserId(String userid) throws SQLException {
		String sql = "SELECT remarkid,bookid,userid,tradeItemid,remark,remarkImgUrl,remarkDate,remarkLevel,isShow FROM remark WHERE userid = ? ORDER BY remarkDate DESC";
		return new LinkedHashSet<>(queryList(sql, userid));
	}
	
	@Override
	public Page<Remark> getPageWithBookId(CriteriaRemark cr) throws Exception {
		Page<Remark> page = new Page<>(cr.getPageNo());
		page.setPageSize(10);
		page.setTotalItemNumber(getTotalRemarkNumberWithBookId(cr));
		//此时传入page对象里的pageNo已经被纠正了，这时也应该将cr里的pageNo纠正
		cr.setPageNo(page.getPageNo());
		page.setList(getRemarkListWithBookId(cr, page.getPageSize()));
		return page;
	}

	@Override
	public long getTotalRemarkNumberWithBookId(CriteriaRemark cr)
			throws Exception {
		String sql = "SELECT count(*) FROM remark WHERE remarkLevel>=? AND remarkLevel<? AND bookid=?";
		Object[] obj = {cr.getMinLevel(),cr.getMaxLevel(),cr.getBookid()};
		return querySingle(sql, obj);
	}
	
	@Override
	public List<Remark> getRemarkListWithBookId(CriteriaRemark cr,int pageSize) throws SQLException {
		String sql = "SELECT remarkid,bookid,userid,tradeItemid,remark,remarkImgUrl,remarkDate,remarkLevel,isShow FROM "
				+ "(SELECT remarkid,bookid,userid,tradeItemid,remark,remarkImgUrl,remarkDate,remarkLevel,isShow FROM "
				+ "remark WHERE remarkLevel>=? AND remarkLevel<? AND bookid=? ORDER BY remarkDate DESC)a "
				+ "limit ?,?";
		System.out.println(sql);
		Object[] obj = {cr.getMinLevel(), cr.getMaxLevel(),cr.getBookid(),(cr.getPageNo() - 1) * pageSize, pageSize};
		return queryList(sql, obj);
	}





}

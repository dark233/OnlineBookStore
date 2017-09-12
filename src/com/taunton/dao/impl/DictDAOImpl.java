package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.taunton.dao.DictDAO;
import com.taunton.dao.RemarkDAO;
import com.taunton.javabean.Book;
import com.taunton.javabean.Dict;
import com.taunton.javabean.Remark;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public class DictDAOImpl extends BaseDAO<Dict> implements DictDAO {

	@Override
	public void addDict(Dict dict) throws SQLException {
		String sql = "INSERT INTO dict(dictid,type,'desc',val,pid,createTime,updateTime) VALUES(?,?,?,?,?,?,?)";
		Object[] obj = {dict.getDictid(),dict.getType(),dict.getDesc(),dict.getVal(),dict.getCreateTime(),dict.getUpdateTime()};
		update(sql, obj);
	}

	@Override
	public void deleteDictWithDictId(String dictid) throws SQLException {
		String sql = "DELETE FROM dict WHERE dict = ?";
		update(sql, dictid);
	}

	@Override
	public void updateDictWithDictId(Dict dict) throws SQLException {
		String sql = "UPDATE dict SET type = ?,'desc' = ?,val = ? WHERE dictid = ?";
		Object[] obj = {dict.getType(),dict.getDesc(),dict.getVal(),dict.getDictid()};
		update(sql, obj);
	}

	@Override
	public void updateDescWithType(String desc, Integer type) throws SQLException {
		String sql = "UPDATE dict SET 'desc' = ? WHERE type = ?";
		Object[] obj = {desc,type};
		update(sql, obj);
	}

	@Override
	public void updateUpdateTimeWithDictId(String updateTime,String dictid) throws SQLException {
		String sql = "UPDATE dict SET updateTime = ? WHERE dictid = ?";
		Object[] obj = {updateTime,dictid};
		update(sql, obj);
	}
	
	@Override
	public Dict getDictWithDictId(String dictid) throws SQLException {
		String sql = "SELECT dictid,type,'desc',val,pid,createTime,updateTime FROM dict WHERE dictid = ? ";
		return query(sql, dictid);
	}

	@Override
	public Set<Dict> getDictListWithType(Integer type) throws SQLException {
		String sql = "SELECT dictid,type,'desc',val,pid,createTime,updateTime FROM dict WHERE type = ? ORDER BY createTime";
		return new LinkedHashSet<>(queryList(sql, type));
	}

	public Set<Dict> getDictListWithPid(String pid) throws SQLException {
		String sql = "SELECT dictid,type,'desc',val,pid,createTime,updateTime FROM dict WHERE pid = ? ORDER BY createTime";
		return new LinkedHashSet<>(queryList(sql, pid));
		
	}
	



}

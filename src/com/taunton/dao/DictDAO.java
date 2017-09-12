package com.taunton.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.taunton.javabean.Book;
import com.taunton.javabean.Dict;
import com.taunton.javabean.Remark;
import com.taunton.web.CriteriaBook;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public interface DictDAO {

	/**
	 * 新增一条数据字典项
	 * @param dict
	 * @throws Exception
	 */
	public void addDict(Dict dict) throws Exception;
	/**
	 * 删除一条数据字典项
	 * @param dictid
	 * @throws Exception
	 */
	public void deleteDictWithDictId(String dictid) throws Exception;
	/**
	 * 根据数据字典id更新数据字典项
	 * @param dictid
	 * @throws Exception
	 */
	public void updateDictWithDictId(Dict dict) throws Exception;
	/**
	 * 根据数据字典类型更新数据字典描述
	 * @param type
	 * @throws Exception
	 */
	public void updateDescWithType(String desc,Integer type) throws Exception;
	/**
	 * 根据数据字典id更新数据字典项更新时间字段
	 * @param updateTime
	 * @throws Exception
	 */
	public void updateUpdateTimeWithDictId(String updateTime,String dictid) throws Exception;
	/**
	 * 根据数据字典id获取一条数据字典
	 * @param dictid
	 * @return
	 * @throws Exception
	 */
	public Dict getDictWithDictId(String dictid) throws Exception;
	/**
	 * 根据数据字典类型获取该类型数据字典项的集合
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Set<Dict> getDictListWithType(Integer type) throws Exception;
	/**
	 * 根据上级id获取上级数据字典下的数据字典集合
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Set<Dict> getDictListWithPid(String pid) throws Exception;
}

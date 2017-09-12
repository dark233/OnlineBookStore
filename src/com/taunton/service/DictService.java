package com.taunton.service;

import java.util.Set;

import org.apache.log4j.Logger;

import com.taunton.dao.impl.DictDAOImpl;
import com.taunton.javabean.Dict;
import com.taunton.utils.DomainFactory;

public class DictService {
	private static Logger logger = Logger.getLogger(DictService.class); 
	private DictDAOImpl dd = null;
	public DictService(){
		dd = DomainFactory.createDomainInstance(DictDAOImpl.class);
	}
	
	/**
	 * 获取对应类型的数据字典集合
	 * @param type
	 * @return
	 */
	public Set<Dict> findDictListByType(Integer type){
		Set<Dict> dictList = null;
		try {
			dictList = dd.getDictListWithType(type);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return dictList;
	}
	
	/**
	 * 获取对应数据字典id的数据字典对象
	 * @param dictid
	 * @return
	 */
	public Dict findDictByDictId(String dictid){
		Dict dict = null;
		try {
			dict = dd.getDictWithDictId(dictid);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return dict;
	}
	
}

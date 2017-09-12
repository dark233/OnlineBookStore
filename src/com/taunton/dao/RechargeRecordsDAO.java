package com.taunton.dao;

import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.INEG;
import com.taunton.javabean.RechargeRecords;
import com.taunton.javabean.Trade;

public interface RechargeRecordsDAO {
	/**
	 * 插入一条充值记录
	 * @param rechargeRecords
	 * @return
	 */
	public void add(RechargeRecords rechargeRecords) throws Exception;
	/**
	 * 删除一条充值记录通过充值记录id
	 * @param id
	 * @return
	 */
	public void deleteRechargeRecordsWithId(String id) throws Exception;
	/**
	 * 删除一条记录通过充值用户id
	 * @param userId
	 * @return
	 */
	public void deleteRechargeRecordsWithUserid(String userId) throws Exception;
	/**
	 * 更新本条充值记录是否展示的状态（1表示true展示给用户，2表示false不展示给用户）通过充值记录id，做用户的删除效果使用
	 * @param id
	 * @return
	 */
	public void updateIsShowWithId(Integer isShow,String id) throws Exception;
	/**
	 * 更新本条记录是否展示的状态（1表示true展示给用户，2表示false不展示给用户）通过充值用户id，做用户的删除效果使用
	 * @param userId
	 * @return
	 */
	public void updateIsShowWithUserid(Integer isShow,String userId) throws Exception;
	/**
	 * 查询充值记录集合通过充值用户id
	 * @param userId
	 * @return
	 */
	public Set<RechargeRecords> getRechargeRecordsWithUserid(String userId) throws Exception;
	/**
	 * 查询一段时间内的充值记录的集合
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Set<RechargeRecords> getRechargeRecordsWithSometime(String startDate,String endDate) throws Exception;
	/**
	 * 查询充值记录集合
	 * @return
	 */
	public Set<RechargeRecords> getRechargeRecords() throws Exception;
}

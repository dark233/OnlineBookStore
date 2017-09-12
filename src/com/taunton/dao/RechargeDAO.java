package com.taunton.dao;

import com.taunton.javabean.Recharge;

public interface RechargeDAO {

	/**
	 * 通过充值卡id获取充值面额
	 * @param rechargeid
	 * @return
	 */
	public Recharge getFaceValueWithRechargeid(String rechargeid) throws Exception;
	/**
	 * 通过充值卡id删除充值卡条目
	 * @param rechargeid
	 */
	public void deleteFaceValueWithRechargeid(String rechargeid) throws Exception;
}

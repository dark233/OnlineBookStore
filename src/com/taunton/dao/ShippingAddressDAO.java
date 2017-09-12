package com.taunton.dao;

import java.util.Set;

import com.taunton.javabean.ShippingAddress;

public interface ShippingAddressDAO {
	/**
	 * 添加收货地址
	 * @param shippingAddress
	 * @return
	 */
	public void addShippingAddress(ShippingAddress shippingAddress) throws Exception;
	/**
	 * 根据收货地址id删除收货地址
	 * @param shipAddrId
	 */
	public void deleteShippingAddressWithShipAddrId(String shipAddrId) throws Exception;
	/**
	 * 根据用户id删除收货地址
	 * @param userid
	 */
	public void deleteShippingAddressWithUserId(String userid) throws Exception;
	/**
	 * 根据收货地址id更新收货地址
	 * @param shippingAddress
	 */
	public void updateShippingAddressWithShipAddrid(ShippingAddress shippingAddress) throws Exception;
	/**
	 * 根据用户id更新该用户下的收货地址的是否为默认收货地址状态
	 * @param isDefu
	 * @param userId
	 */
	public void updateisDefuWithUserId(Integer isDefu,String userId) throws Exception;
	/**
	 * 根据收货地址id更新该收货地址的是否为默认收货地址状态
	 * @param isDefu
	 * @param shipAddrId
	 */
	public void updateisDefuWithShipAddrId(Integer isDefu,String shipAddrId) throws Exception;
	/**
	 * 根据收货地址id获取收货地址
	 * @param shipAddrId
	 * @return
	 */
	public ShippingAddress getShippingAddressWithShipAddrId(String shipAddrId) throws Exception;
	/**
	 * 根据用户id获取收货地址集合
	 * @param userid
	 * @return
	 */
	public Set<ShippingAddress> getShippingAddressWithUserId(String userid) throws Exception;
	/**
	 * 根据用户id获取用户下的收货地址数量
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public Integer getNumberWithUserId(String userid) throws Exception;
	
}

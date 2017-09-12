package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.taunton.dao.impl.ShippingAddressDAOImpl;
import com.taunton.javabean.ShippingAddress;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;

public class ShippingAddressService {

	private static Logger logger = Logger.getLogger(ShippingAddressService.class); 
	private ShippingAddressDAOImpl sad = null;
	public  ShippingAddressService() {
		sad = DomainFactory.createDomainInstance(ShippingAddressDAOImpl.class);
	}
	
	/**
	 * 获取当前用户下的收货地址列表
	 * @param userid
	 * @return
	 */
	public Set<ShippingAddress> findShippingAddressList(String userid){
		Set<ShippingAddress> shipAddrList = null;
		try {
			shipAddrList = sad.getShippingAddressWithUserId(userid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return shipAddrList;
		
	}
	/**
	 * 获取一条收货地址根据收货地址id
	 * @param shipAddrid
	 * @return
	 */
	public ShippingAddress findShippingAddress(String shipAddrid){
		ShippingAddress shipAddr = null;
		try {
			shipAddr = sad.getShippingAddressWithShipAddrId(shipAddrid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return shipAddr;
	}
}

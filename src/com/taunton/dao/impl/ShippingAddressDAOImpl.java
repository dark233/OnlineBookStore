package com.taunton.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.taunton.dao.ShippingAddressDAO;
import com.taunton.javabean.ShippingAddress;

public class ShippingAddressDAOImpl extends BaseDAO<ShippingAddress> implements
		ShippingAddressDAO {

	@Override
	public void addShippingAddress(ShippingAddress shippingAddress) throws SQLException {
		String sql = "INSERT INTO shippingaddress(shipaddrid,userid,cnee,tel,shipAddr,isDefu,province,city,district_county,detailaddr) VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {shippingAddress.getShipAddrid(),shippingAddress.getUserid(),shippingAddress.getCnee(),shippingAddress.getTel(),shippingAddress.getShipAddr(),
				shippingAddress.getIsDefu(),shippingAddress.getProvince(),shippingAddress.getCity(),shippingAddress.getDistrict_county(),shippingAddress.getDetailAddr()};
		update(sql, obj);
	}

	@Override
	public void deleteShippingAddressWithShipAddrId(String shipAddrId) throws SQLException {
		String sql = "DELETE FROM shippingaddress WHERE shipaddrid = ?";
		update(sql, shipAddrId);
	}

	@Override
	public void deleteShippingAddressWithUserId(String userid) throws SQLException {
		String sql = "DELETE FROM shippingaddress WHERE userid = ?";
		update(sql, userid);
	}

	@Override
	public void updateShippingAddressWithShipAddrid(
			ShippingAddress shippingAddress) throws SQLException {
		String sql = "UPDATE shippingaddress SET cnee = ?,tel = ?,shipAddr = ?,isdefu = ?,province = ?,city = ?,district_county = ?,detailaddr = ? WHERE shipaddrid = ?";
		Object[] obj = {shippingAddress.getCnee(),shippingAddress.getTel(),shippingAddress.getShipAddr(),shippingAddress.getIsDefu(),shippingAddress.getProvince(),
				shippingAddress.getCity(),shippingAddress.getDistrict_county(),shippingAddress.getDetailAddr(),shippingAddress.getShipAddrid()};
		update(sql, obj);

	}
	
	@Override
	public void updateisDefuWithUserId(Integer isDefu, String userId) throws SQLException {
		String sql = "UPDATE shippingaddress SET isDefu = ? WHERE userid = ?";
		Object[] obj = {isDefu,userId};
		update(sql, obj);
	}

	@Override
	public void updateisDefuWithShipAddrId(Integer isDefu, String shipAddrId) throws SQLException {
		String sql = "UPDATE shippingaddress SET isDefu = ? WHERE shipaddrid = ?";
		Object[] obj = {isDefu,shipAddrId};
		update(sql, obj);
	}

	@Override
	public ShippingAddress getShippingAddressWithShipAddrId(String shipAddrId) throws SQLException {
		String sql = "SELECT shipAddrid,userid,cnee,tel,shipAddr,isDefu,province,city,district_county,detailAddr FROM shippingaddress WHERE shipaddrid = ?";
		ShippingAddress shippingAddress = query(sql, shipAddrId);
		return shippingAddress;
	}

	@Override
	public Set<ShippingAddress> getShippingAddressWithUserId(String userid) throws SQLException {
		String sql = "SELECT shipAddrid,userid,cnee,tel,shipAddr,isDefu,province,city,district_county,detailAddr FROM shippingaddress WHERE userid = ? ORDER BY isDefu ASC,orderby DESC";
		return new LinkedHashSet<>(queryList(sql, userid));
	}

	@Override
	public Integer getNumberWithUserId(String userid) throws Exception {
		String sql = "SELECT COUNT(*) FROM dict WHERE userid = ?";
		Number num = querySingle(sql, userid);
		return num.intValue();
	}

	

}

package com.taunton.javabean;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Trade {
private String tradeid;
private String userid;
private String shipAddrid;
private String tradeTime;
private String payTime;
private String shipTime;
private String transactionTime;
private double payOverDays;
private String payExpirtyTime;
private double confirmReceiptOverDays;
private String confirmReceiptExpirtyTime;
private float tradeMoney;
private int status_;
private String expressOrder;
private String expressComp;
private int isShow;
private String cnee;
private String tel;
private String shipAddr;
private ShippingAddress shippingAddress;
private List<TradeItem> tradeItems = new ArrayList<TradeItem>();
private Handle handle;
private List<Handle> handles = new ArrayList<Handle>();
//以下几个字段仅为页面显示倒计时用在JavaScript里计算时使用
private Date showTradeTime;
private Date showPayExpirtyTime;
private Date showPayTime;
private Date showShipTime;
private Date showConfirmReceiptExpirtyTime;
private Date showTransactionTime;
/**
 * 获取订单id
 * @return
 */
public String getTradeid() {
	return tradeid;
}
/**
 * 设置订单id
 * @param tradeid
 */
public void setTradeid(String tradeid) {
	this.tradeid = tradeid;
}
/**
 * 获取用户id
 * @return
 */
public String getUserid() {
	return userid;
}
/**
 * 设置用户id
 * @param userid
 */
public void setUserid(String userid) {
	this.userid = userid;
}
/**
 * 获取本订单对应的收货地址id
 * @return
 */
public String getShipAddrid() {
	return shipAddrid;
}
/**
 * 设置本订单对应的收货地址id
 * @param shipAddrid
 */
public void setShipAddrid(String shipAddrid) {
	this.shipAddrid = shipAddrid;
}
/**
 * 获取本订单对应的收货地址对象
 * @return
 */
public ShippingAddress getShippingAddress() {
	return shippingAddress;
}
/**
 * 设置本订单对应的收货地址对象
 * @param shippingAddress
 */
public void setShippingAddress(ShippingAddress shippingAddress) {
	this.shippingAddress = shippingAddress;
}
/**
 * 获取订单的生成时间
 * @return
 */
public String getTradeTime() {
	return tradeTime;
}
/**
 * 设置订单的生成时间
 * @param tradeTime
 */
public void setTradeTime(String tradeTime) {
	this.tradeTime = tradeTime;
}
/**
 * 获取订单的支付时间
 * @return
 */
public String getPayTime() {
	return payTime;
}
/**
 * 设置订单的支付时间
 * @param payTime
 */
public void setPayTime(String payTime) {
	this.payTime = payTime;
}
/**
 * 获取订单的发货时间
 * @return
 */
public String getShipTime() {
	return shipTime;
}
/**
 * 设置订单的发货时间
 * @param shipTime
 */
public void setShipTime(String shipTime) {
	this.shipTime = shipTime;
}
/**
 * 获取订单的成交时间
 * @return
 */
public String getTransactionTime() {
	return transactionTime;
}
/**
 * 设置订单的成交时间
 * @param transactionTime
 */
public void setTransactionTime(String transactionTime) {
	this.transactionTime = transactionTime;
}

/**
 * 获取支付超时天数
 * @return
 */
public double getPayOverDays() {
	return payOverDays;
}
/**
 * 设置支付超时天数
 * @param payOverDays
 */
public void setPayOverDays(double payOverDays) {
	this.payOverDays = payOverDays;
}
/**
 * 获取支付超时时间
 * @return
 */
public String getPayExpirtyTime() {
	return payExpirtyTime;
}
/**
 * 设置支付超时时间
 * @param payExpirtyTime
 */
public void setPayExpirtyTime(String payExpirtyTime) {
	this.payExpirtyTime = payExpirtyTime;
}
/**
 * 获取确认收货超时天数
 * @return
 */
public double getConfirmReceiptOverDays() {
	return confirmReceiptOverDays;
}
/**
 * 设置确认收货超时天数
 * @param confirmReceiptOverDays
 */
public void setConfirmReceiptOverDays(double confirmReceiptOverDays) {
	this.confirmReceiptOverDays = confirmReceiptOverDays;
}
/**
 * 获取确认收货超时时间
 * @return
 */
public String getConfirmReceiptExpirtyTime() {
	return confirmReceiptExpirtyTime;
}
/**
 * 设置确认支付超时时间
 * @param confirmReceiptExpirtyTime
 */
public void setConfirmReceiptExpirtyTime(String confirmReceiptExpirtyTime) {
	this.confirmReceiptExpirtyTime = confirmReceiptExpirtyTime;
}
/**
 * 获取订单总金额
 * @return
 */

public float getTradeMoney() {
	return tradeMoney;
}
/**
 * 设置订单总金额
 * @param tradeMoney
 */
public void setTradeMoney(float tradeMoney) {
	this.tradeMoney = tradeMoney;
}
/**
 * 获取订单条目集合
 * @return
 */
public List<TradeItem> getTradeItems() {
	return tradeItems;
}
/**
 * 设置订单条目集合
 * @param tradeItems
 */
public void setTradeItems(List<TradeItem> tradeItems) {
	this.tradeItems = tradeItems;
}
/**
 * 获取订单状态(
1:代付款,
2：已付款，待发货,
3：已发货，等待确认,
4：确认收货，待评价,
5：取消订单 ,
6：退款中,
7：退款完成,
8：已评价，订单完成).
 * @return
 */
public int getStatus_() {
	return status_;
}
/**
 * 设置订单状态(
1:代付款,
2：已付款，待发货,
3：已发货，等待确认,
4：确认收货，待评价,
5：取消订单 ,
6：退款中,
7：退款完成,
8：已评价，订单完成).
 * @param status_
 */
public void setStatus_(int status_) {
	this.status_ = status_;
}
/**
 * 获取快递单号
 * @return
 */
public String getExpressOrder() {
	return expressOrder;
}
/**
 * 设置快递单号
 * @param expressOrder
 */
public void setExpressOrder(String expressOrder) {
	this.expressOrder = expressOrder;
}
/**
 * 获取快递公司
 * @return
 */
public String getExpressComp() {
	return expressComp;
}
/**
 * 设置快递公司
 * @param expressComp
 */
public void setExpressComp(String expressComp) {
	this.expressComp = expressComp;
}
/**
 * 获取是否将订单展示出来的状态 (1表示true将订单展示给用户,2表示false不将订单展示给用户)
 * @return
 */
public int getIsShow() {
	return isShow;
}
/**
 * 设置是否将订单展示出来的状态 (1表示true将订单展示给用户,2表示false不将订单展示给用户)
 * @param isShow
 */
public void setIsShow(int isShow) {
	this.isShow = isShow;
}

/**
 * 获取订单收货人
 * @return
 */
public String getCnee() {
	return cnee;
}
/**
 * 设置订单收货人
 * @param cnee
 */
public void setCnee(String cnee) {
	this.cnee = cnee;
}
/**
 * 获取订单收货人手机号码
 * @return
 */
public String getTel() {
	return tel;
}
/**
 * 设置订单收货人手机号码
 * @param tel
 */
public void setTel(String tel) {
	this.tel = tel;
}
/**
 * 获取订单收货地址
 * @return
 */
public String getShipAddr() {
	return shipAddr;
}
/**
 * 获取订单收货地址
 * @param shipAddr
 */
public void setShipAddr(String shipAddr) {
	this.shipAddr = shipAddr;
}
/**
 * 获取该订单下最近一次退款处理记录
 * @return
 */
public Handle getHandle() {
	return handle;
}
/**
 * 设置该订单下最近一次退款处理记录
 * @param handle
 */
public void setHandle(Handle handle) {
	this.handle = handle;
}
/**
 * 获取该订单下的处理记录集合
 * @return
 */
public List<Handle> getHandles() {
	return handles;
}
/**
 * 设置该订单下的处理记录集合
 * @param handles
 */
public void setHandles(List<Handle> handles) {
	this.handles = handles;
}
/**
 * 获取显示用下单时间
 * @return
 */
public Date getShowTradeTime() {
	return showTradeTime;
}
/**
 * 设置显示用下单时间
 * @param showTradeTime
 */
public void setShowTradeTime(Date showTradeTime) {
	this.showTradeTime = showTradeTime;
}
/**
 * 获取显示用支付失效时间
 * @return
 */
public Date getShowPayExpirtyTime() {
	return showPayExpirtyTime;
}
/**
 * 设置显示用支付失效时间
 * @param showPayExpirtyTime
 */
public void setShowPayExpirtyTime(Date showPayExpirtyTime) {
	this.showPayExpirtyTime = showPayExpirtyTime;
}
/**
 * 获取显示用支付时间
 * @return
 */
public Date getShowPayTime() {
	return showPayTime;
}
/**
 * 设置显示用支付时间
 * @param showPayTime
 */
public void setShowPayTime(Date showPayTime) {
	this.showPayTime = showPayTime;
}
/**
 * 获取显示用发货时间
 * @return
 */
public Date getShowShipTime() {
	return showShipTime;
}
/**
 * 设置显示用发货时间
 * @param showShipTime
 */
public void setShowShipTime(Date showShipTime) {
	this.showShipTime = showShipTime;
}
/**
 * 获取显示用确认收货超时时间
 * @return
 */
public Date getShowConfirmReceiptExpirtyTime() {
	return showConfirmReceiptExpirtyTime;
}
/**
 * 设置显示用确认收货超时时间
 * @param showConfirmReceiptExpirtyTime
 */
public void setShowConfirmReceiptExpirtyTime(Date showConfirmReceiptExpirtyTime) {
	this.showConfirmReceiptExpirtyTime = showConfirmReceiptExpirtyTime;
}
/**
 * 获取显示用确认收货时间
 * @return
 */
public Date getShowTransactionTime() {
	return showTransactionTime;
}
/**
 * 设置显示用确认收货时间
 * @param showTransactionTime
 */
public void setShowTransactionTime(Date showTransactionTime) {
	this.showTransactionTime = showTransactionTime;
}

@Override
public String toString() {
	return "Trade [tradeid=" + tradeid + ", userid=" + userid + ", shipAddrid="
			+ shipAddrid
			+ ", tradeTime=" + tradeTime + ", payTime=" + payTime
			+ ", shipTime=" + shipTime + ", transactionTime=" + transactionTime
			+ ", payOverDays=" + payOverDays + ", payExpirtyTime="
			+ payExpirtyTime + ", confirmReceiptOverDays="
			+ confirmReceiptOverDays + ", confirmReceiptExpirtyTime="
			+ confirmReceiptExpirtyTime + ", tradeMoney=" + tradeMoney
			+ ", status_=" + status_ + ", expressOrder=" + expressOrder
			+ ", expressComp=" + expressComp + ", isShow=" + isShow + ", cnee="
			+ cnee + ", tel=" + tel + ", shipAddr=" + shipAddr
			+ ", shippingAddress=" + shippingAddress + ", tradeItems="
			+ tradeItems + "]";
}
public Trade() {
	super();
}
public Trade(String tradeid, String userid, String shipAddrid,
		String tradeTime, double payOverDays,
		String payExpirtyTime, float tradeMoney, int status_,
		int isShow,String cnee,String tel,String shipAddr) {
	super();
	this.tradeid = tradeid;
	this.userid = userid;
	this.shipAddrid = shipAddrid;
	this.tradeTime = tradeTime;
	this.payOverDays = payOverDays;
	this.payExpirtyTime = payExpirtyTime;
	this.tradeMoney = tradeMoney;
	this.status_ = status_;
	this.isShow = isShow;
	this.cnee = cnee;
	this.tel = tel;
	this.shipAddr = shipAddr;
}




}

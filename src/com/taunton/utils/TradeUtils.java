package com.taunton.utils;

import com.taunton.javabean.Trade;

/**
 * 订单工具类
 * @author taunton
 *
 */
public class TradeUtils {

	/**
	 * 获取真实订单状态
	 * @param trade
	 * @return
	 */
	public static Integer getRealTradeStatus(Trade trade){
		Integer status = trade.getStatus_();
		Integer realStatus = 0;
		switch (status) {
		case 1:
			if(System.currentTimeMillis() < TimeUtils.parseString(trade.getPayExpirtyTime(), TimeUtils.FORMATTER_1).getTime() || 
					System.currentTimeMillis() == TimeUtils.parseString(trade.getPayExpirtyTime(), TimeUtils.FORMATTER_1).getTime()){
				realStatus = 1;
			}else if(System.currentTimeMillis() > TimeUtils.parseString(trade.getPayExpirtyTime(), TimeUtils.FORMATTER_1).getTime()){
				realStatus = 5;
			}
			break;
		case 3:
			if(System.currentTimeMillis() < TimeUtils.parseString(trade.getConfirmReceiptExpirtyTime(), TimeUtils.FORMATTER_1).getTime() || 
					System.currentTimeMillis() == TimeUtils.parseString(trade.getConfirmReceiptExpirtyTime(), TimeUtils.FORMATTER_1).getTime()){
				realStatus = 3;
			}else if(System.currentTimeMillis() > TimeUtils.parseString(trade.getConfirmReceiptExpirtyTime(), TimeUtils.FORMATTER_1).getTime()){
				realStatus = 4;
			}
			break;
		case 2:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			realStatus = status;
			break;
		default:
			realStatus = 0;
			break;
		}
		return realStatus;
	}
}

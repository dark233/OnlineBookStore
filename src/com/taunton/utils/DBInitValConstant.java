package com.taunton.utils;

import java.util.Date;

/**
 * 数据库字段初始化值常量配置类
 * @author taunton
 *
 */

public class DBInitValConstant {
	/**
	 *  支付超时天数（1天）
	 */
	public static final double PAYOVERDAYS_1 = 1;
	/**
	 * 支付超时天数（2天）
	 */
	public static final double PAYOVERDAYS_2 = 2;
	/**
	 * 支付超时天数-测试用-0.0001天（大约八秒）
	 */
	public static final double PAYOVERDAYS_TEST = 0.0001;
	/**
	 * 确认收货超时天数（7天）
	 */
	public static final double CONFIRM_RECEIPT_OVERDAYS_1 = 7;
	/**
	 * 确认收货超时天数（10天）
	 */
	public static final double CONFIRM_RECEIPT_OVERDAYS_2 = 10;
	/**
	 * 确认收货超时天数（1天）
	 */
	public static final double CONFIRM_RECEIPT_OVERDAYS_3 = 1;
	/**
	 * 确认收货超时天数-测试用-0.0001天（大约八秒）
	 */
	public static final double CONFIRM_RECEIPT_OVERDAYS_TEST = 0.0001;
	/**
	 * 用户购物车初始化总金额
	 */
	public static final float INIT_SHOPPINGCART_TOTALMONEY = 0;
	/**
	 * 用户购物车初始化购物车项数目
	 */
	public static final int INIT_SHOPPINGCARTITEMS_NUMBER = 0;
	/**
	 * 用户订单初始退款次数
	 */
	public static final int INIT_REFUNDTIMES = 0;
	/**
	 * 是否展示给用户-是
	 */
	public static final int IS_SHOW_YES = 1;
	/**
	 * 是否展示给用户-否
	 */
	public static final int IS_SHOW_NO = 1;
	/**
	 * 是否已评论-是
	 */
	public static final int IS_REMARKED_YES = 1;
	/**
	 * 是否已评论-是
	 */
	public static final int IS_REMARKED_NO = 2;
	/**
	 * 订单状态-未支付
	 */
	public static final int TRADE_STATUS_NOT_PAID = 1;
	/**
	 * 订单状态-已支付
	 */
	public static final int TRADE_STATUS_PAID = 2;
	/**
	 * 订单状态-已发货
	 */
	public static final int TRADE_STATUS_SHIP = 3;
	/**
	 * 订单状态-订单已完成（收货且评价）
	 */
	public static final int TRADE_STATUS_FINISHED = 8;
	/**
	 * 订单状态-待评价
	 */
	public static final int TRADE_STATUS_NOT_REMARKED = 4;
	/**
	 * 订单状态-取消订单
	 */
	public static final int TRADE_STATUS_CANCEL = 5;
	/**
	 * 订单状态-发起退款
	 */
	public static final int TRADE_STATUS_REFUNDING  = 6;
	/**
	 * 订单状态-退款完成
	 */
	public static final int TRADE_STATUS_REFUND_FINISHED = 7;
	/**
	 * 处理订单功能-发货
	 */
	public static final int HANDLE_OPERATE_SHIP = 1;
	/**
	 * 处理订单功能-退款
	 */
	public static final int HANDLE_OPERATE_REFUND = 2;
	/**
	 * 处理订单状态-完成
	 */
	public static final int HANDLE_STATUS_SUCCESS = 1;
	/**
	 * 处理订单状态-处理中
	 */
	public static final int HANDLE_STATUS_PROCESS = 2;
	/**
	 * 订单处理记录是否有效-有效
	 */
	public static final int HANDLE_ISVALID_VALID = 1;
	/**
	 * 订单处理记录是否有效-无效
	 */
	public static final int HANDLE_ISVALID_UNVALID = 2;
	/**
	 * 退款记录状态-退款中
	 */
	public static final int REFUND_STATUS_PROCESS = 1;
	/**
	 * 退款记录状态-拒绝
	 */
	public static final int REFUND_STATUS_REFUSED = 2;
	/**
	 * 退款记录状态-同意
	 */
	public static final int REFUND_STATUS_SUCCESS = 3;
	/**
	 * 评价默认级别-好评
	 */
	public static final int REMARK_DEFAULT_LEVEL = 1;
	/**
	 * 评价默认评价图片路径
	 */
	public static final String REMARK_DEFAULT_REMARKIMGURL = "remark_img/default_remarkimg.jpg";
	/**
	 * 图书是否展示-是
	 */
	public static final Integer BOOK_ISSHOW_YES = 1;
	/**
	 * 图书是否展示-否
	 */
	public static final Integer BOOK_ISSHOW_NO = 2;
	/**
	 * 上架图书初始库存-0
	 */
	public static final Integer BOOK_STORENUMBER = 0;
	/**
	 * 上架图书初始销量-0
	 */
	public static final Integer BOOK_SALESAMOUNT = 0;
}

package com.taunton.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.taunton.javabean.Trade;
import com.taunton.javabean.TradeItem;
import com.taunton.service.BookService;
import com.taunton.service.TradeService;
import com.taunton.servlet.BookServlet;
import com.taunton.utils.ConnectionContext;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.TimeUtils;

/**
 * 确认收货超时检查-定时任务
 * @author taunton
 *
 */
public class PayExpirtyTimeTask implements Runnable{

	private static Logger logger = Logger.getLogger(PayExpirtyTimeTask.class); 
	private TradeService ts = DomainFactory.createDomainInstance(TradeService.class);
	private BookService bs = DomainFactory.createDomainInstance(BookService.class);
	private static int i = 1;
	
	@Override
	public void run() {
		String startTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		logger.info("支付超时定时任务开始执行，开始时间："+startTime);
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			ConnectionContext.getInstance().bind(conn);
			Set<Trade> tradeList = ts.findTradesWithStatusForTask(DBInitValConstant.TRADE_STATUS_NOT_PAID);
			long payExpiryTimeMills = 0;
			boolean flag = false;
			for (Trade trade : tradeList) {
				flag = false;
				payExpiryTimeMills = TimeUtils.parseString(trade.getPayExpirtyTime(), TimeUtils.FORMATTER_1).getTime();
				if(System.currentTimeMillis() > payExpiryTimeMills){
	//			trade.setStatus(DBInitValConstant.STATUS_CANCEL);
					//修改订单状态为取消订单
//					flag = ts.updateStatusWithTradeid(trade.getTradeid(), DBInitValConstant.TRADE_STATUS_CANCEL);
					//批量更新图书库存
//					Set<TradeItem> tradeItems = ts.findTradeItemsWithTradeid(trade.getTradeid());
//					bs.batchUpdateStoreNumber(tradeItems);
					//订单取消业务
					ts.cancle(trade.getTradeid());
					if(!flag){
						logger.debug("支付超时定时任务出现失败条目。失败订单：tradeid = "+trade.getTradeid());
					}else{
						logger.info("支付超时定时任务执行成功一条。成功订单：tradeid = "+trade.getTradeid());
					}
				}
				conn.commit();
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("支付超时定时任务回滚失败");
				logger.error(e1);
			}
			logger.error("执行支付超时定时任务出现异常，下一次定时任务照常进行。");
			logger.error(e);
		}finally{
			ConnectionContext.getInstance().remove();
			JDBCUtils.release(conn);
			logger.info("支付超时定时任务释放数据库连接:"+conn);
		}
		String endTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		logger.info("支付超时定时任务执行完成，完成时间："+endTime);
		logger.info("这是系统第"+i+"次支付超时定时任务。消耗时间："+TimeUtils.getWasteTime(startTime, TimeUtils.FORMATTER_1, endTime, TimeUtils.FORMATTER_1));
		
	}

}

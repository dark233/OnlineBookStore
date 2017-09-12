package com.taunton.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.slf4j.profiler.TimeInstrument;

import com.taunton.javabean.Trade;
import com.taunton.javabean.TradeItem;
import com.taunton.service.BackStageUserService;
import com.taunton.service.BookService;
import com.taunton.service.TradeService;
import com.taunton.utils.ConnectionContext;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.TimeUtils;

/**
 * 支付超时检查-定时任务
 * @author taunton
 *
 */
public class ConfirmReceiptExpirtyTimeTask implements Runnable{

	private static Logger logger = Logger.getLogger(ConfirmReceiptExpirtyTimeTask.class); 
	private TradeService ts = DomainFactory.createDomainInstance(TradeService.class);
	private BookService bs = DomainFactory.createDomainInstance(BookService.class);
	private BackStageUserService bus = DomainFactory.createDomainInstance(BackStageUserService.class);
	private static int i = 1;
	
	@Override
	public void run() {
		String startTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		logger.info("确认收货超时定时任务开始执行，开始时间："+startTime);
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			ConnectionContext.getInstance().bind(conn);
			Set<Trade> tradeList = ts.findTradesWithStatusForTask(DBInitValConstant.TRADE_STATUS_SHIP);
			long confirmReceiptExpirtyTimeMills = 0;
			boolean flag = false;
			for (Trade trade : tradeList) {
				flag = false;
				confirmReceiptExpirtyTimeMills = TimeUtils.parseString(trade.getConfirmReceiptExpirtyTime(), TimeUtils.FORMATTER_1).getTime();
				if(System.currentTimeMillis() > confirmReceiptExpirtyTimeMills){
//				trade.setStatus(DBInitValConstant.STATUS_CANCEL);
//					flag = ts.updateStatusWithTradeid(trade.getTradeid(), DBInitValConstant.TRADE_STATUS_NOT_REMARKED);
//					Set<TradeItem> tradeItems = ts.findTradeItemsWithTradeid(trade.getTradeid());
//					flag = bs.batchUpdateSalesAmount(tradeItems);
					flag = ts.confirm(trade.getTradeid());
					if(!flag){
						logger.debug("确认收货超时定时任务出现失败项。失败订单：tradeid = "+trade.getTradeid());
					}else{
						logger.info("确认收货超时定时任务执行成功一条。成功订单：tradeid = "+trade.getTradeid());
					}
				}
				conn.commit();
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("确认收货超时定时任务回滚失败");
				logger.error(e1);
			}
			logger.error("执行确认收货超时定时任务出现异常，下一次定时任务照常进行。");
			logger.error(e);
		}finally{
			ConnectionContext.getInstance().remove();
			JDBCUtils.release(conn);
			logger.info("确认收货超时定时任务释放数据库连接:"+conn);
		}
		String endTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		logger.info("确认收货超时定时任务执行完成，完成时间："+endTime);
		logger.info("这是系统第"+i+"次确认收货超时定时任务。消耗时间："+TimeUtils.getWasteTime(startTime, TimeUtils.FORMATTER_1, endTime, TimeUtils.FORMATTER_1));
		i++;
	}

}

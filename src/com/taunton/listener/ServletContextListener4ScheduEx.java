package com.taunton.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.taunton.task.ConfirmReceiptExpirtyTimeTask;
import com.taunton.task.PayExpirtyTimeTask;
import com.taunton.utils.CommonConstant;
import com.taunton.utils.TimeUtils;

/**
 * ServletContext监听器，用于创建定时任务
 * @author taunton
 *
 */
public class ServletContextListener4ScheduEx implements ServletContextListener{

	private static Logger logger = Logger.getLogger(ServletContextListener4ScheduEx.class); 

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
		long oneDayMills = TimeUtils.COUNTMILLS_DAY;
		logger.info("定时任务开启");
		//添加支付超时定时任务，每天凌晨两点执行
		//initDelay是为了能让第一次定时任务能被执行，因为ScheduledExecutorService定时任务是相对时间，每次加时间间隔，所以第一次一般都执行不到，因此要有initDelay
		long initDelay = TimeUtils.getTimeMills(CommonConstant.TIMED_TASK_START_TIME_1)-System.currentTimeMillis();
		initDelay = initDelay>0?initDelay:initDelay+oneDayMills;
		executor.scheduleWithFixedDelay(new PayExpirtyTimeTask(), initDelay, oneDayMills, TimeUnit.MILLISECONDS);
		
		//添加确认收货超时定时任务，每天凌晨三点执行
		long initDelay2 = TimeUtils.getTimeMills(CommonConstant.TIMED_TASK_START_TIME_2)-System.currentTimeMillis();
		initDelay2 = initDelay2>0?initDelay2:initDelay2+oneDayMills;
		executor.scheduleWithFixedDelay(new ConfirmReceiptExpirtyTimeTask(), initDelay2, oneDayMills,TimeUnit.MILLISECONDS);
		logger.info("定时任务开启成功");
	}

}

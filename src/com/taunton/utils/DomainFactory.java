package com.taunton.utils;

import org.apache.log4j.Logger;

/**
 * domain对象生产工厂
 * @author tangdong
 *
 */
public class DomainFactory {
	private static Logger logger = Logger.getLogger(DomainFactory.class); 

	
	public static <V> V createDomainInstance(Class clazz){
		V obj = null;
		try {
			 obj =  (V) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("工厂实例化对象失败");
		} 
		return obj;
	}
}

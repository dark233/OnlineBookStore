package com.taunton.el.elfunctions;

import java.util.Date;
import com.taunton.utils.TimeUtils;

public class GetCurrTime {

	/**
	 * 根据传入日期格式获取对应格式化的当前时间
	 * @param pattern
	 * @return
	 */
	public static String getCurrTime(String pattern){
		String currTime = TimeUtils.formatDate(new Date(), pattern);
		return currTime;
	}
}

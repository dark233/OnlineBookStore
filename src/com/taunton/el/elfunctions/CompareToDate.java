package com.taunton.el.elfunctions;

import com.taunton.utils.TimeUtils;

public class CompareToDate {

	/**
	 * 根据传入的格式化日期字符串比较两个日期的大小，date1大于等于date2则返回true，否则返回false
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Boolean compareDate(String date1,String date2){
		long l1 = TimeUtils.parseString(date1, TimeUtils.FORMATTER_1).getTime();
		long l2 = TimeUtils.parseString(date2, TimeUtils.FORMATTER_1).getTime();
		if(l1>=l2){
			return true;
		}
		return false;
		
	}
}

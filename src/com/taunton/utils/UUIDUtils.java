package com.taunton.utils;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.taunton.sms.SendRegistSMS;

/**
 * 自定义UUID工具类
 * @author Taunton
 *
 */
public class UUIDUtils {
	private static Logger logger = Logger.getLogger(UUIDUtils.class); 
	
	private static String[] chars = new String[]{"0","1","2","3","4","5","6","7","8","9"};
	/**
	 * 通过传入位数来获取指定位数的UUID随机且唯一的数，传入参数只能是2、4、8、16
	 * @param bits
	 * @return
	 */
	public static String getUUID(int bits){
		if(!(bits==2||bits==4||bits==8||bits==16)){
			return null;
		}
    	StringBuffer sb = new StringBuffer();
    	String uuid = UUID.randomUUID().toString().replace("-", "");
    	int bits2 = 32/bits;
    	for(int i = 0;i<bits;i++){
    		String str =null;
    		if(bits==2||bits==4){
    			str = uuid.substring(i*4, i*4+4);
    		}else{
    			str = uuid.substring(i*bits2, i*bits2+bits2);
    		}
    		int a = Integer.parseInt(str,16);
    		int b = a%0x0a;
    		String c = chars[b];
    		sb.append(c);
    	}
    	String result = sb.toString();
    	logger.info("获取UUID码:UUIDresult = "+result);
    	return result;
	}
}

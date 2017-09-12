package com.taunton.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.log4j.Logger;

import com.taunton.exception.CheckException;


public class MD5Encode {
	private static Logger logger = Logger.getLogger(MD5Encode.class); 

	
	
	/**
	 * 进行MD5加密，返回MD5消息摘要
	 * @param str
	 * @return
	 */
	public static String getMD5Encode(String str) {
		String encode = "";
		try {
			if(str == null || str.trim().isEmpty()){
				return null;
			}
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("UTF-8"));
			encode = new BigInteger(1, md5.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MD5加密失败");
		}
		return encode;
	}
	
	/**
	 * 验证MD5消息摘要
	 * @param newStr
	 * @param oldStr
	 * @return
	 */
	public static boolean validateMD5Encode(String newStr,String oldStr){
		boolean flag = false;
		if(newStr == null || oldStr == null){
			flag = false;
		}else if(newStr.equals(oldStr)){
			flag = true;
		}
		return flag;
		
	}
}

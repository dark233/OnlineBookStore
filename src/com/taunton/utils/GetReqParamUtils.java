package com.taunton.utils;

import javax.servlet.http.HttpServletRequest;

import com.taunton.web.CriteriaRemark;
/**
 * 请求参数获取类
 * @author taunton
 *
 */

public class GetReqParamUtils {
	
	/**
	 * 获取指定参数名，符合正则表达式规范的请求参数
	 * @param request
	 * @param paramName
	 * @param reg
	 * @return
	 */
	public static String getParameter(HttpServletRequest request,String paramName,String initVal,String regex){
		String result = initVal;
		String param = request.getParameter(paramName);
		if(regex != null && !regex.trim().isEmpty()){
			if(param != null && !param.trim().isEmpty() && param.matches(regex)){
				result = param;
			}
		}else{
			if(param != null && !param.trim().isEmpty()){
				result = param;
			}
		}
		return result;
	}
	/**
	 * 获取Float类型的请求参数
	 * @param request
	 * @param paraName
	 * @param initVal
	 * @param regex
	 * @return
	 */
	public static Float getFloatParameter(HttpServletRequest request,String paraName,Float initVal,String regex){
		Float result = initVal;
		String param = request.getParameter(paraName);
		if(regex != null && !regex.trim().isEmpty()){
			if(param !=null && !param.trim().isEmpty() && param.matches(regex)){
				result = Float.parseFloat(param);
			}
		}else{
			if(param !=null && !param.trim().isEmpty()){
				result = Float.parseFloat(param);
			}
		}
		return result;
	}
	/**
	 * 获取Integer类型的参数
	 * @param request
	 * @param paraName
	 * @param initVal
	 * @param regex
	 * @return
	 */
	public static Integer getIntegerParameter(HttpServletRequest request,String paraName,Integer initVal,String regex){
		Integer result = initVal;
		String param = request.getParameter(paraName);
/*		String regex = "\\d+";*/
		if(regex != null && !regex.trim().isEmpty()){
			if(param !=null && !param.trim().isEmpty() && param.matches(regex)){
				result = Integer.parseInt(param);
			}
		}else{
			if(param !=null && !param.trim().isEmpty()){
				result = Integer.parseInt(param);
			}
		}
		return result;
	}
	/**
	 * 获取Boolean类型的请求参数
	 * @param request
	 * @param paraName
	 * @param initVal
	 * @return
	 */
	public static Boolean getBooleanParameter(HttpServletRequest request,String paraName,Boolean initVal){
		Boolean result = initVal;
		Object param = request.getParameter(paraName);
		if(param instanceof String && param != null){
			result = Boolean.parseBoolean(String.valueOf(param));
		}else if(param instanceof Boolean && param != null){
			result = (Boolean)param;
		}
		return result;
	}
	/**
	 * 获取请求参数中的quantity参数值
	 * @param request
	 * @return
	 */
	/*public static int getQuantity(HttpServletRequest request){
		int quantity = 1;
		String param = request.getParameter("quantity");
		if(param != null && !param.trim().isEmpty()){
			quantity = Integer.parseInt(param);
		}
		return quantity;
		
	}*/
	
	/**
	 * 获取请求参数中的分页页码
	 * @param request
	 * @return
	 */
	/*public static int getPageNo(HttpServletRequest request) {
		int pageNo = 1;
		String param = request.getParameter("pageNo");
		String reg = "\\d+";
		if(param != null && !param.trim().isEmpty()&&param.matches(reg)) {
			try {
				pageNo = Integer.parseInt(param);
			} catch(RuntimeException e) {}
		}
		return pageNo;
	}*/
	/**
	 * 获取请求参数中的最小价格
	 * @param request
	 * @return
	 */
	/*public static Float getMinPrice(HttpServletRequest request,String use) {
		float minPrice = 0;
		String param = request.getParameter("minPrice");
		if(param != null && !param.trim().isEmpty()) {
			try {
				minPrice = Float.parseFloat(param);;
			} catch(RuntimeException e) {}
		}else if("display".equals(use)){
			return null;
		}
		return minPrice;
	}*/
	/**
	 * 获取请求参数中的最大价格
	 * @param request
	 * @return
	 */
	/*public static Float getMaxPrice(HttpServletRequest request,String use) {
		float maxPrice = Integer.MAX_VALUE;
		String param = request.getParameter("maxPrice");
		if(param != null && !param.trim().isEmpty()) {
			try {
				maxPrice = Float.parseFloat(param);;
			} catch(RuntimeException e) {}
		}else if("display".equals(use)){
				return null;
		}
		return maxPrice;
	}*/
	
	/**
	 * 获取请求参数中的最小评价级别
	 * @param request
	 * @return
	 */
	public static CriteriaRemark getRemarkLevel(HttpServletRequest request,CriteriaRemark cr) {
		int minLevel = 0;
		String param = request.getParameter("minLevel");
		if(param != null && !param.trim().isEmpty()) {
			try {
				minLevel = Integer.parseInt(param);;
			} catch(RuntimeException e) {}
		}
		cr.setMinLevel(minLevel);
		switch (minLevel) {
		case 1:
			cr.setMaxLevel(2);
			break;
		case 2:
			cr.setMaxLevel(3);
			break;
		case 3:
			cr.setMaxLevel(4);
		default:
			cr.setMaxLevel(4);
			break;
		}
		return cr;
	}
	
	
}

package com.taunton.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * HttpSession属性处理工具。可以进行的操作为：获取，添加，移除。
 * @author taunton
 *
 */
public class HandleSessionAttrUtils {

	/**
	 * 获取HttpSession里的属性
	 * @param request
	 * @param attrName
	 * @return
	 */
	public static Object getAttrInSession(HttpServletRequest request,String attrName){
		HttpSession session = request.getSession();
		return session.getAttribute(attrName);
	}
	/**
	 * 向HttpSession里添加属性
	 * @param request
	 * @param attrName
	 * @param attr
	 */
	public static boolean setAttr2Session(HttpServletRequest request,String attrName,Object attr){
		HttpSession session = request.getSession();
		session.setAttribute(attrName, attr);
		return true;
	}
	/**
	 * 从HttpSession里移除属性
	 * @param request
	 * @param attrName
	 * @return
	 */
	public static boolean removeAttrFromSession(HttpServletRequest request,String attrName){
		HttpSession session = request.getSession();
		session.removeAttribute(attrName);
		return true;
	}
	/**
	 *  销毁HttpSession
	 * @param request
	 * @return
	 */
	public static boolean invalidatedSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		return true;
	}
}

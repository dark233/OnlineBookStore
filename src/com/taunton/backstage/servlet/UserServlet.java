package com.taunton.backstage.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taunton.javabean.User;
import com.taunton.service.UserService;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.UserConstant;
import com.taunton.web.CriteriaUser;
import com.taunton.web.Page;

import org.apache.log4j.Logger;;

/**
 * 前台用户Servlet
 * 
 * @author taunton
 * 
 */
public class UserServlet extends BaseServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserServlet.class); 
	private static UserService us = DomainFactory.createDomainInstance(UserService.class);
	
	/**
	 * 获取分页查询的查询路径(去掉pageNo参数之后的路径，用于在附在分页页面的页码上)
	 * @param request
	 * @return
	 */
	private String getPageUrl(HttpServletRequest request){
		String reqUrl = request.getRequestURI() + "?" + request.getQueryString();
		/*
		 * 如果url中存在pageNo参数，截取掉，如果不存在那就不用截取。
		 */
		int index = reqUrl.lastIndexOf("&pageNo=");
		if(index != -1) {
			reqUrl = reqUrl.substring(0, index);
		}
		return reqUrl;
	}
	
	/**
	 * 获取订单处理记录page
	 * @param request
	 * @param response
	 * @return
	 */
	public String findPageWithIsBan(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int pageNo = GetReqParamUtils.getIntegerParameter(request, "page", 1, "\\d+");
		Integer isBan = GetReqParamUtils.getIntegerParameter(request,"isBan", null, null);
		Page<User> pages =null;
		CriteriaUser cu = new CriteriaUser(isBan, pageNo, null);
		pages = us.findPageByIsBan(cu);
		long banNum = us.findUserNumByIsBan(UserConstant.IS_BAN_YES);
		long noBanNum = us.findUserNumByIsBan(UserConstant.IS_BAN_NO);
		String pagrUrl = getPageUrl(request);
		request.setAttribute("banNum", banNum);
		request.setAttribute("noBanNum", noBanNum);
		request.setAttribute("pageUrl", pagrUrl);
		request.setAttribute("pages",pages);
		return "f:/adminjsps/admin/user/list.jsp";
	}
	
	/**
	 * 封禁/解禁用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String banUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer isBan = GetReqParamUtils.getIntegerParameter(request,"isBan", null, null);
		String userid = GetReqParamUtils.getParameter(request, "userid", null, null);
		us.ban(isBan, userid);
		return findPageWithIsBan(request, response);
	}

	
}

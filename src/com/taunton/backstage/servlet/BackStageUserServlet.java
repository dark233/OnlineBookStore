package com.taunton.backstage.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.itcast.commons.CommonUtils;

import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.User;
import com.taunton.service.BackStageUserService;
import com.taunton.service.RemarkService;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.HandleSessionAttrUtils;
import com.taunton.utils.MD5Encode;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;

public class BackStageUserServlet extends BaseServlet{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BackStageUserServlet.class); 
	private BackStageUserService bsus = DomainFactory.createDomainInstance(BackStageUserService.class);
	
	/**
	 * 验证输入的验证码是否正确
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void validateImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text./html;charset=UTF-8");
		// 获取随机生成的验证码
		String text = (String) request.getSession().getAttribute("text");
		String imgText = request.getParameter("img");
		logger.info("用户输入的验证码为:"+imgText+",系统生成验证码的值为:"+text);
		if ((text != null && text.equalsIgnoreCase(imgText))) {
			response.getWriter().print("1");
		} else {
			response.getWriter().print("0");
		}
	}
	
	/**
	 * 用户登录验证
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void validateLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String username = GetReqParamUtils.getParameter(request, "username", null, null);
		String password = GetReqParamUtils.getParameter(request, "password", null, null);
		PrintWriter pw = response.getWriter();
		if(username == null || password == null){
			pw.print("error");
			return;
		}
		BackStageUser user = bsus.findUserByUsernameRegex(username);
		if(user == null){
			pw.print("用户名不存在");
			return;
		}
		password = MD5Encode.getMD5Encode(password);
		if(!user.getPwd().equals(password)){
			pw.print("用户名与密码不吻合");
			return;
		}
		if(user.getIsBan() == 1){
			pw.print("用户已封禁");
			return;
		}
		pw.print("1");
		return;
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = GetReqParamUtils.getParameter(request, "username",null, null);
		String pwd = GetReqParamUtils.getParameter(request, "password", null,null);
		String isRemember = GetReqParamUtils.getParameter(request, "isRemeber", null,null);
		logger.info("请求传送过来的参数。 username = "+username+",password = "+pwd+",isRemeber = "+isRemember);
//		HandleSessionAttrUtils.invalidatedSession(request);
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval((int) TimeUtils.COUNTMILLS_4);
			//登录成功才会记住密码
		BackStageUser user = bsus.findUserByUsernameRegex(username);
		if(isRemember!=null){
//			username = user.getUsername();
			Cookie[] cookies = request.getCookies();
			Cookie c1 = null;
			Cookie c2 = null;
			for (Cookie cookie : cookies) {
				if("backstage_username".equals(cookie.getName()) && username.equals(cookie.getValue())){
					c1 = cookie;
				}
				if("backstage_password".equals(cookie.getName()) && pwd.equals(cookie.getValue())){
					c2 = cookie;
				}
			}
			if(c1 == null || c2 == null){
				c1 =  new Cookie("backstage_username",URLEncoder.encode(username,"utf-8"));
				c2 =  new Cookie("backstage_password",URLEncoder.encode(pwd,"utf-8"));
				c1.setMaxAge((int) TimeUtils.COUNTSECONDS_5);
				c2.setMaxAge((int) TimeUtils.COUNTSECONDS_5);
				response.addCookie(c1);
				response.addCookie(c2);
			}
		}
		HandleSessionAttrUtils.setAttr2Session(request, "backstageuser", user);
		logger.info("用户登录成功。user = "+user+"	logintime="+TimeUtils.getCurrTime(TimeUtils.FORMATTER_1));
		return "r:/adminjsps/admin/enter.jsp";
	}
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 */
	public String  exit(HttpServletRequest request,HttpServletResponse response){
		HandleSessionAttrUtils.removeAttrFromSession(request, "backstageuser");
		logger.info(" 用户退出");
/*		session.invalidate();*/
		return "r:/adminjsps/login.jsp";
		
	}
	
	

	
}

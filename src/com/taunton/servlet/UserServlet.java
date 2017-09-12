package com.taunton.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.itcast.commons.CommonUtils;

import com.taunton.javabean.Handle;
import com.taunton.javabean.RechargeRecords;
import com.taunton.javabean.ShippingAddress;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.User;
import com.taunton.service.RechargeRecordsService;
import com.taunton.service.ShippingAddressService;
import com.taunton.service.ShoppingCartService;
import com.taunton.service.UserService;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.HandleSessionAttrUtils;
import com.taunton.utils.MD5Encode;
import com.taunton.utils.PaymentUtil;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.utils.UserConstant;

public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 3735588810537346759L;
	private static Logger logger = Logger.getLogger(UserServlet.class); 
	private UserService us = DomainFactory.createDomainInstance(UserService.class);
	private ShoppingCartService scs = DomainFactory.createDomainInstance(ShoppingCartService.class);
	private RechargeRecordsService rrs = DomainFactory.createDomainInstance(RechargeRecordsService.class);
	private ShippingAddressService sas = DomainFactory.createDomainInstance(ShippingAddressService.class);
	/**
	 * 注册新用户
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		String username =user.getUsername();
		String pwd = MD5Encode.getMD5Encode(user.getPwd());
		String pay_pwd = MD5Encode.getMD5Encode(user.getPay_pwd());
		logger.info("请求传送过来的参数为 :"+user.toString());
		boolean flag1 = false;
		if (username!=null&&!"".equals(username.trim())) {
			if (pwd!=null&&!"".equals(pwd.trim())) {
				if (pay_pwd!=null&&!"".equals(pay_pwd.trim())) {
					flag1 = true;
				}
			}
		}
		if (!flag1) {
			return "r:/error.jsp";
		}
		user.setPwd(pwd);
		user.setPay_pwd(pay_pwd);
		String userid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
		userid+=UUIDUtils.getUUID(8);
		userid+="cu";
		user.setUserid(userid);
		String shoppingCartid = userid;
		shoppingCartid+=UUIDUtils.getUUID(2);
		ShoppingCart sc = new ShoppingCart(shoppingCartid, userid,DBInitValConstant.INIT_SHOPPINGCART_TOTALMONEY, DBInitValConstant.INIT_SHOPPINGCARTITEMS_NUMBER);
		user.setBalance(UserConstant.INIT_BALANCE);
		user.setLevel(UserConstant.USER_INIT_LEVEL);
		user.setRoleid(UserConstant.ROLEID_1);
		user.setIsBan(UserConstant.IS_BAN_NO);
		user.setRegistDate(TimeUtils.getCurrTime(TimeUtils.FORMATTER_1));
		user.setHeadImgUrl(UserConstant.DEFAULT_HEADIMGURL);
		user.setEmail("tangduns945@gmail.com");
		logger.info("请求传送过来的参数进行组装后:user = "+user.toString());

		boolean flag = us.regist(user,sc);
		if (flag) {
//			user.setShoppingCart(sc);
//			HandleSessionAttrUtils.setAttr2Session(request, "user", user);
			HandleSessionAttrUtils.removeAttrFromSession(request, "registCode");
			HandleSessionAttrUtils.removeAttrFromSession(request, "registCodeTime");
			HandleSessionAttrUtils.removeAttrFromSession(request, "registTel");
			logger.info("普通用户注册成功:user = "+user+",shoppingCart = "+sc);
			request.setAttribute("code", "success");
			request.setAttribute("msg", "注册成功");
			return "r:/jsps/msg.jsp";
		}else{
			logger.info("普通用户注册失败!");
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "注册失败");
			return "r:/jsps/msg.jsp";
//			response.setContentType("text/html;charset=utf-8");
//			response.getWriter().print("试图入侵，注册失败");
			
		}

	}

	/**
	 * 验证用户是否已经存在(通过用户名)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void validateUsername(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//加上一个参数userType来进行后台用户与普通用户的代码共用!!!!!!
		String username = request.getParameter("username"); 
		logger.info("请求传送过来的参数为:username = "+username);
		User user =  us.getUserByUsername(username);
		logger.info("通过username和usertype查出的用户:user = "+user);
		PrintWriter pw = response.getWriter();
//		若发送验证码手机号码与当前手机号码不一致，注册失败
//		if (!(tel.equals(tel2))) {
//			pw.print("3");
//			return;
//		}else 
		if (user == null) {
//			session.removeAttribute("registCode");
//			session.removeAttribute("registCodeTime");
//			session.removeAttribute("registTel");
			pw.print("1");
			return;
		}else {
			pw.print("0");
			return;
		}
			
	}
	/**
	 * 验证用户是否存在（通过手机号码）
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void validateTel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//加上一个参数userType来进行后台用户与普通用户的代码共用!!!!!!
		String tel = request.getParameter("tel");
		logger.info("请求传送过来的参数为: tel = "+tel);
		User user = us.getUserByTel(tel);
		logger.info("通过tel和usertype查出的用户:user = "+user);
		PrintWriter pw = response.getWriter();
//		若发送验证码手机号码与当前手机号码不一致，注册失败
//		if (!(tel.equals(tel2))) {
//			pw.print("3");
//			return;
//		}else 
		if (user == null) {
//			session.removeAttribute("registCode");
//			session.removeAttribute("registCodeTime");
//			session.removeAttribute("registTel");
			pw.print("1");
			return;
		} else{
			pw.print("0");
			return;
		}
			
	}
	
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
	 * 验证输入的手机注册验证码是否正确,验证码有效时间十分钟
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void validateSMSCode(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		 获取随机生成的验证码
		String telCode = request.getParameter("telCode");
		String tel = request.getParameter("tel");
		logger.info("校验手机验证码请求参数：code = "+telCode+",tel = "+tel);
		String registCode = (String) HandleSessionAttrUtils.getAttrInSession(request, "registCode");
		String registCodeTime_ = (String) HandleSessionAttrUtils.getAttrInSession(request, "registCodeTime");
		String registTel = (String) HandleSessionAttrUtils.getAttrInSession(request, "registTel");
		PrintWriter pw = response.getWriter();
		if(registCode != null && registCodeTime_ != null && registTel != null){
			Date registCodeTime = null;
			long overTime = TimeUtils.COUNTMILLS_1;
			Date d1 = new Date();
		    registCodeTime = TimeUtils.parseString(registCodeTime_,TimeUtils.FORMATTER_1);
			long remainTime = d1.getTime()-registCodeTime.getTime();
			if (tel != null && !(tel.equals(registTel))) {
				pw.print("手机号码与发送验证码手机号码不符");
				return;
			}else if(registCodeTime_ != null && remainTime>overTime){
				pw.print("验证码失效");;
				return;
			}else if ((registCode != null && telCode.equals(registCode))) {
				pw.print("1");
				return;
			} 
			
		}else {
			pw.print("验证码有误");
			return;
		}
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = GetReqParamUtils.getParameter(request, "username",null, null);
		String pwd = GetReqParamUtils.getParameter(request, "password",null, null);
		String password = MD5Encode.getMD5Encode(pwd);
		String isRemember = GetReqParamUtils.getParameter(request, "isRemeber", null,null);
		logger.info("请求传送过来的参数。 username = "+username+",password = "+password+",isRemeber = "+isRemember);
		boolean isBan = us.getUserIsBanedByUsername(username);
		if(isBan){
			response.getWriter().print("2");
			logger.info("黑名单用户"+username+"尝试登录");
			return;
		}
		boolean flag = us.login(username, password);
		if(flag){
//			HandleSessionAttrUtils.invalidatedSession(request);
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval((int) TimeUtils.COUNTMILLS_4);
			User user = us.getUserByUsername(username);
			//登录成功才会记住密码
			if(isRemember!=null){
//				username = user.getUsername();
				Cookie[] cookies = request.getCookies();
				Cookie c1 = null;
				Cookie c2 = null;
				for (Cookie cookie : cookies) {
					if("username".equals(cookie.getName()) && username.equals(cookie.getValue())){
						c1 = cookie;
					}
					if("pwd".equals(cookie.getName()) && pwd.equals(cookie.getValue())){
						c2 = cookie;
					}
//						else if("pwd".equals(cookie.getName()) && pwd.equals(cookie.getValue())){
//						c2 = cookie;
//					}
				}
				if(c1 == null || c2 == null){
					c1 =  new Cookie("username",URLEncoder.encode(username,"utf-8"));
					c2 =  new Cookie("pwd",URLEncoder.encode(pwd,"utf-8"));
					c1.setMaxAge((int) TimeUtils.COUNTSECONDS_5);
					c2.setMaxAge((int) TimeUtils.COUNTSECONDS_5);
					response.addCookie(c1);
					response.addCookie(c2);
				}
			}
			ShoppingCart sc = scs.findShoppingCartWithUserid(user.getUserid());
			if(sc==null){
					return;
				}
			user.setShoppingCart(sc);
			HandleSessionAttrUtils.setAttr2Session(request, "user", user);
			logger.info("用户登录成功。user = "+user+"	logintime="+TimeUtils.getCurrTime(TimeUtils.FORMATTER_1));
			response.getWriter().print("1");
			return;
		}
		response.getWriter().print("0");
	}
	
	/**
	 * 获取用户基本信息
	 * @param request
	 * @param response
	 * @return
	 */
	public String info(HttpServletRequest request,HttpServletResponse response) {
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		if(user  == null){
			return "r:/jsps/user/login.jsp";
		}
		user = us.getUserByTel(user.getTel());
		StringBuffer tel = new StringBuffer();
		tel.append(user.getTel());
		String str = "****";
		tel.replace(3, 7, str);
		user.setTel(tel.toString());
		request.setAttribute("user", user);
		return "f:/jsps/user/info.jsp";
	}
	
	/**
	 * 修改密码验证
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void validateUpdatePwd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		user = us.getUserByUsername(user.getUsername());
		String oldPwd = GetReqParamUtils.getParameter(request, "loginpass", null, null);
		oldPwd = MD5Encode.getMD5Encode(oldPwd);
		logger.info("请求传送过来的参数为:oldPwd = "+oldPwd);
		PrintWriter pw = response.getWriter();
		if(oldPwd == null){
			pw.print("旧密码不能为空");
			return;
		}
		if(!MD5Encode.validateMD5Encode(oldPwd, user.getPwd())){
			pw.print("旧密码错误");
			return ;
		}else{
			pw.print("1");
			return;
		}
		
			
	}
	/**
	 * 修改登录密码
	 * @param request
	 * @param response
	 * @return
	 */
	public String updateUserPwd(HttpServletRequest request,HttpServletResponse response){
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		String userid = user.getUserid();
		String newPwd = GetReqParamUtils.getParameter(request, "newpass", null, null);
		if(newPwd == null){
			return "f:/jsps/user/pwd.jsp";
		}
		newPwd = MD5Encode.getMD5Encode(newPwd);
		boolean flag = us.changePwd(userid, newPwd);
		//更新session域里的值user
		HandleSessionAttrUtils.removeAttrFromSession(request, "user");
		HandleSessionAttrUtils.setAttr2Session(request, "user", us.getUserByUsername(user.getUsername()));
		if(flag){
			request.setAttribute("code", "success");
			request.setAttribute("msg", "修改登录密码成功");
			return "f:/jsps/msg.jsp";
		}else{
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "修改登录密码失败");
			return "f:/jsps/msg.jsp";
		}
		
	}
	/**
	 * 用户更新支付密码校验
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void validateUpdatePaypwd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		user = us.getUserByUsername(user.getUsername());
		String oldPwd = GetReqParamUtils.getParameter(request, "paypass", null, null);
		oldPwd = MD5Encode.getMD5Encode(oldPwd);
		logger.info("请求传送过来的参数为:oldPwd = "+oldPwd);
		PrintWriter pw = response.getWriter();
		if(oldPwd == null){
			pw.print("旧密码皆不能为空");
			return;
		}
		if(!MD5Encode.validateMD5Encode(oldPwd, user.getPay_pwd())){
			pw.print("旧密码错误");
			return ;
		}else{
			pw.print("1");
			return;
		}
		
		
			
	}
	/**
	 * 修改用户支付密码
	 * @param request
	 * @param response
	 * @return
	 */
	public String updateUserPaypwd(HttpServletRequest request,HttpServletResponse response){		
	User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
	String userid = user.getUserid();
	String newPwd = GetReqParamUtils.getParameter(request, "newpass", null, null);
	if(newPwd == null){
		return "f:/jsps/user/paypwd.jsp";
	}
	boolean flag = us.changePay_pwd(userid, newPwd);
	//更新session域里的值user
	HandleSessionAttrUtils.removeAttrFromSession(request, "user");
	HandleSessionAttrUtils.setAttr2Session(request, "user", us.getUserByUsername(user.getUsername()));
	if(flag){
		request.setAttribute("code", "success");
		request.setAttribute("msg", "修改支付密码成功");
		return "f:/jsps/msg.jsp";
	}else{
		request.setAttribute("code", "failed");
		request.setAttribute("msg", "修改支付密码失败");
		return "f:/jsps/msg.jsp";
	}}
	
	/**
	 * 修改用户名
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void updateUsername(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userid = GetReqParamUtils.getParameter(request, "userid", null, null);
		String username = GetReqParamUtils.getParameter(request, "username", null, null);
		PrintWriter pw = response.getWriter();
		JSONObject jo = new JSONObject();
		if(userid == null || username == null){
			jo.put("errorCode", "error");
			pw.print(jo.toString());
			return;
		}
		us.changeUsername(userid, username);
		User user = us.getUserByUsername(username);
		HandleSessionAttrUtils.setAttr2Session(request, "user", user);
		jo.put("errorCode", "1");
		pw.print(jo.toString());
		return;
	}

	
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 */
	public String  exit(HttpServletRequest request,HttpServletResponse response){
		HandleSessionAttrUtils.removeAttrFromSession(request, "user");
		HandleSessionAttrUtils.invalidatedSession(request);
		logger.info(" 用户退出");
		return "r:/enter.jsp";
		
	}
	
	

	/**
	 * 充值方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String recharge(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		float rechargeVal = GetReqParamUtils.getFloatParameter(request, "rechargeVal", (float) 0, null);
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		/*
		 * 1. 准备13个参数
		 */
		String p0_Cmd = "Buy";//业务类型，固定值Buy
		String p1_MerId = props.getProperty("p1_MerId");//商号编码，在易宝的唯一标识
		String p2_Order = "";//订单编码
		String p3_Amt = ""+rechargeVal;//支付金额 
		String p4_Cur = "CNY";//交易币种，固定值CNY
		String p5_Pid = "";//商品名称
//		p5_Pid = new String(p5_Pid.getBytes("iso-8859-1"),"UTF-8");
		String p6_Pcat = "";//商品种类
		String p7_Pdesc = "";//商品描述F
		String p8_Url = props.getProperty("rechargeBack_Url");//在支付成功后，易宝会访问这个地址。
		String p9_SAF = "";//送货地址
		String pa_MP = "";//扩展信息
		String pd_FrpId = GetReqParamUtils.getParameter(request, "yh", null, null);//支付通道
		String pr_NeedResponse = "1";//应答机制，固定值1
		
		/*
		 * 2. 计算hmac
		 * 需要13个参数
		 * 需要keyValue
		 * 需要加密算法
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		
		/*
		 * 3. 重定向到易宝的支付网关
		 */
		StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");
		sb.append("?").append("p0_Cmd=").append(p0_Cmd);
		sb.append("&").append("p1_MerId=").append(p1_MerId);
		sb.append("&").append("p2_Order=").append(p2_Order);
		sb.append("&").append("p3_Amt=").append(p3_Amt);
		sb.append("&").append("p4_Cur=").append(p4_Cur);
		sb.append("&").append("p5_Pid=").append(p5_Pid);
		sb.append("&").append("p6_Pcat=").append(p6_Pcat);
		sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
		sb.append("&").append("p8_Url=").append(p8_Url);
		sb.append("&").append("p9_SAF=").append(p9_SAF);
		sb.append("&").append("pa_MP=").append(pa_MP);
		sb.append("&").append("pd_FrpId=").append(pd_FrpId);
		sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
		sb.append("&").append("hmac=").append(hmac);
		
		response.sendRedirect(sb.toString());
		return null;
	}
	
	/**
	 * 回馈方法
	 * 当支付成功时，易宝会访问这里
	 * 用两种方法访问：
	 * 1. 引导用户的浏览器重定向(如果用户关闭了浏览器，就不能访问这里了)
	 * 2. 易宝的服务器会使用点对点通讯的方法访问这个方法。（必须回馈success，不然易宝服务器会一直调用这个方法）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String back(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1. 获取12个参数
		 */
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String  rb_BankId = request.getParameter(" rb_BankId");
		String hmac = request.getParameter("hmac");
		/*
		 * 2. 获取keyValue
		 */
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		String keyValue = props.getProperty("keyValue");
		/*
		 * 3. 调用PaymentUtil的校验方法来校验调用者的身份
		 *   >如果校验失败：保存错误信息，转发到msg.jsp
		 *   >如果校验通过：
		 *     * 判断访问的方法是重定向还是点对点，如果要是重定向
		 *     修改订单状态，保存成功信息，转发到msg.jsp
		 *     * 如果是点对点：修改订单状态，返回success
		 */
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId,
				r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
				keyValue);
		if(!bool) {
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "无效的签名，支付失败!");
			return "f:/jsps/msg.jsp";
		}
		if(r1_Code.equals("1")) {
			String currTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
			float rechargeVal = Float.parseFloat(r3_Amt);
			User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
			String id = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
			id+=UUIDUtils.getUUID(8);
			id+=UUIDUtils.getUUID(4);
			RechargeRecords rechargeRecords = new RechargeRecords(id, user.getUserid(), rechargeVal, currTime,  rb_BankId, DBInitValConstant.IS_SHOW_YES);
			rrs.createRechargeRecords(rechargeRecords);
			us.changeBalance(user.getUserid(), rechargeVal);
			if(r9_BType.equals("1")) {
				request.setAttribute("code", "success");
				request.setAttribute("msg", "支付成功！");
				return "f:/jsps/msg.jsp";				
			} else if(r9_BType.equals("2")) {
				response.getWriter().print("success");
			}
		}
		return null;
	}
	
	/**
	 * 收货地址管理准备
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String shippingAddrPre(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer isLoad = GetReqParamUtils.getIntegerParameter(request, "isLoad", null, null);
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		Set<ShippingAddress> shippingAddrList = sas.findShippingAddressList(user.getUserid());
		if(isLoad != null && isLoad == 1){
			String shipAddrid = GetReqParamUtils.getParameter(request, "shipAddrid", null, null);
			if(shipAddrid == null){
				request.setAttribute("code", "failed");
				request.setAttribute("msg", "error");
				return "f:/jsps/msg.jsp";
			}
			ShippingAddress shippingAddr = sas.findShippingAddress(shipAddrid);
			request.setAttribute("btn", 2);
			request.setAttribute("shippingAddr", shippingAddr);
		}else{
			request.setAttribute("btn", 1);
		}
		request.setAttribute("shippingAddrList", shippingAddrList);
		return "f:/jsps/user/shippingAddr.jsp";
		
	}

}

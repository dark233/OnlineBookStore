package com.taunton.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.enterprise.inject.ResolutionException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import cn.itcast.commons.CommonUtils;

import com.taunton.javabean.Handle;
import com.taunton.javabean.RefundRecords;
import com.taunton.javabean.Remark;
import com.taunton.javabean.ShippingAddress;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.ShoppingCartItem;
import com.taunton.javabean.Trade;
import com.taunton.javabean.TradeItem;
import com.taunton.javabean.User;
import com.taunton.service.BookService;
import com.taunton.service.HandleService;
import com.taunton.service.RefundRecordsService;
import com.taunton.service.RemarkService;
import com.taunton.service.ShippingAddressService;
import com.taunton.service.ShoppingCartService;
import com.taunton.service.TradeService;
import com.taunton.service.UserService;
import com.taunton.utils.CommonConstant;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.ErrorCodeConstant;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.HandleSessionAttrUtils;
import com.taunton.utils.MD5Encode;
import com.taunton.utils.PaymentUtil;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.TradeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.web.CriteriaTrade;
import com.taunton.web.Page;

public class TradeServlet extends BaseServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TradeServlet.class); 
	private TradeService ts = DomainFactory.createDomainInstance(TradeService.class);
	private ShoppingCartService scs = DomainFactory.createDomainInstance(ShoppingCartService.class);
	private ShippingAddressService sas = DomainFactory.createDomainInstance(ShippingAddressService.class);
	private BookService bs = DomainFactory.createDomainInstance(BookService.class);
	private UserService us = DomainFactory.createDomainInstance(UserService.class);
	private HandleService hs = DomainFactory.createDomainInstance(HandleService.class);
	private RefundRecordsService rrs = DomainFactory.createDomainInstance(RefundRecordsService.class);
	private RemarkService rs = DomainFactory.createDomainInstance(RemarkService.class);
	
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
	 * 支付准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String paymentPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		if(tradeid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "支付跳转失败");
			return "f:/jsps/msg.jsp";
		}
		Trade trade = ts.findTradeWithTradeid(tradeid);
		request.setAttribute("trade", trade);
		return "f:/jsps/trade/pay.jsp";
	}
	
	/**
	 * 选择支付方式分流
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public String checkPayWay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String payway = GetReqParamUtils.getParameter(request, "pay_way", null, null);
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		int tradeStatus = TradeUtils.getRealTradeStatus(ts.findTradeWithTradeid(tradeid));
		if(payway == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "支付跳转失败");
			return "f:/jsps/msg.jsp";
		}
		//防止用户会退到历史页面对订单再次进行支付
		if(tradeStatus != 1){
			return "r:/enter.jsp";
		}
		if(CommonConstant.PAY_WAY_1.equals(payway)){
			return myPayment(request, response);
		}else if(CommonConstant.PAY_WAY_2.equals(payway)){
			return payment(request, response);
		}
		return null;
	}
	/**
	 * 余额支付验证，验证支付密码及账户余额
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void vilidatePaypwd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		String pay_pwd = GetReqParamUtils.getParameter(request, "pay_pwd", null, null);
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		User currUser = us.getUserByTel(user.getTel());
		Trade trade = ts.findTradeWithTradeid(tradeid);
		PrintWriter pw = response.getWriter();
		pay_pwd=MD5Encode.getMD5Encode(pay_pwd);
		if(pay_pwd == null){
			pw.print(ErrorCodeConstant.PAY_ERROR_CODE_4);
			return;
		}else if(!MD5Encode.validateMD5Encode(pay_pwd, currUser.getPay_pwd())){
			pw.print(ErrorCodeConstant.PAY_ERROR_CODE_2);
			return;
		}else if(trade.getTradeMoney()>currUser.getBalance()){
			pw.print(ErrorCodeConstant.PAY_ERROR_CODE_3);
			return;
		}else{
			pw.print(ErrorCodeConstant.PAY_ERROR_CODE_1);
			return;
		}
	}
	/**
	 * NBSpay（余额支付）
	 * @return
	 */
	public String myPayment(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		if(tradeid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "支付跳转失败");
			return "f:/jsps/msg.jsp";
		}
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		User currUser = us.getUserByTel(user.getTel());
		Trade trade = ts.findTradeWithTradeid(tradeid);
		//支付业务
		ts.pay(tradeid);
		//分派后台处理人员
		String currTime = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_1);
		String handleid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
		handleid+=UUIDUtils.getUUID(8);
		Handle handle = new Handle(handleid, tradeid, null, DBInitValConstant.HANDLE_OPERATE_SHIP, DBInitValConstant.HANDLE_STATUS_PROCESS,
				DBInitValConstant.HANDLE_ISVALID_VALID, currTime, currTime);
		hs.assignTrade(handle);
		//更新用户余额
		us.changeBalance(currUser.getUserid(),-trade.getTradeMoney());
		request.setAttribute("code", "success");
		request.setAttribute("msg", "支付成功");
		return "f:/jsps/msg.jsp";
	}
	/**
	 * 支付方法
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String payment(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		/*
		 * 1. 准备13个参数
		 */
		String p0_Cmd = "Buy";//业务类型，固定值Buy
		String p1_MerId = props.getProperty("p1_MerId");//商号编码，在易宝的唯一标识
		String p2_Order = GetReqParamUtils.getParameter(request, "tradeid", null, null);//订单编码
		String p3_Amt = "0.01";//支付金额 这里作测试用，就不使用trademoney了
		String p4_Cur = "CNY";//交易币种，固定值CNY
		String p5_Pid = "";//商品名称
//		p5_Pid = new String(p5_Pid.getBytes("iso-8859-1"),"UTF-8");
		String p6_Pcat = "";//商品种类
		String p7_Pdesc = "";//商品描述F
		String p8_Url = props.getProperty("p8_Url");//在支付成功后，易宝会访问这个地址。
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
	 * @throws ServletException
	 * @throws IOException
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
			//支付业务
			ts.pay(r6_Order);
			//分派后台处理人员
			String currTime = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_1);
			String handleid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
			handleid+=UUIDUtils.getUUID(8);
			Handle handle = new Handle(handleid, r6_Order, null, DBInitValConstant.HANDLE_OPERATE_SHIP, DBInitValConstant.HANDLE_STATUS_PROCESS,
					DBInitValConstant.HANDLE_ISVALID_VALID, currTime, currTime);
			hs.assignTrade(handle);
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
	 * 取消订单验证
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public void vilidateCancel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		JSONObject jo = new JSONObject();
		if(tradeid == null){
			jo.put("errorCode", "error");
			pw.print(jo.toString());
			return;
		}
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Integer status = TradeUtils.getRealTradeStatus(trade);
		if(status == DBInitValConstant.TRADE_STATUS_NOT_PAID){
			jo.put("errorCode", "1");
			pw.print(jo.toString());
			return;
		}else{
			jo.put("errorCode", "订单状态有误");
			pw.print(jo.toString());
			return;
		}
		
	}
	
	/**
	 * 取消订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void cancel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		JSONObject jo = null;
		if(tradeid == null){
			jo = new JSONObject();
			jo.put("errorCode", "error");
			pw.print(jo.toString());
			return;
		}
		ts.cancle(tradeid);
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Integer tradeStatus = TradeUtils.getRealTradeStatus(trade);
		jo = new JSONObject();
		jo.put("errorCode", "1");
		jo.put("tradeStatus", tradeStatus);
		pw.print(jo.toString());
		return;
	}

	/**
	 * 支付倒计时到自动取消订单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String cancelAuto(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		if(tradeid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/jsps/msg.jsp";
		}
		ts.cancle(tradeid);
		return load(request, response);
	}

	
	/**
	 * 确认收货验证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void vilidateConfirm(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		String pay_pwd = GetReqParamUtils.getParameter(request, "pay_pwd", null, null);
		pay_pwd = MD5Encode.getMD5Encode(pay_pwd);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		JSONObject jo = new JSONObject();
		if(tradeid == null){
			jo.put("errorCode", "error");
			pw.print(jo.toString());
			return;
		}
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Integer status = TradeUtils.getRealTradeStatus(trade);
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		if(status == DBInitValConstant.TRADE_STATUS_SHIP && MD5Encode.validateMD5Encode(pay_pwd, user.getPay_pwd())){
			jo.put("errorCode", "1");
			pw.print(jo.toString());
			return;
		}else if(!MD5Encode.validateMD5Encode(pay_pwd, user.getPay_pwd())){
			jo.put("errorCode", "支付密码有误");
			pw.print(jo.toString());
			return;
		}else{
			jo.put("errorCode", "订单状态有误");
			pw.print(jo.toString());
			return;
		}
	}
	
	/**
	 * 确认收货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void confirm(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		JSONObject jo = null;
		if(tradeid == null){
			jo = new JSONObject();
			jo.put("errorCode", "error");
			pw.print(jo.toString());
			return;
		}
		ts.confirm(tradeid);
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Integer tradeStatus = TradeUtils.getRealTradeStatus(trade);
		jo = new JSONObject();
		jo.put("errorCode", "1");
		jo.put("tradeStatus", tradeStatus);
		pw.print(jo.toString());
		return;
	}
	
	/**
	 * 倒计时到自动确认收货
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String confirmAuto(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		if(tradeid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/jsps/msg.jsp";
		}
		//确认收货业务
		ts.confirm(tradeid);
		return load(request, response);
	}
	
	
	/**
	 * 退款准备
	 * @param request
	 * @param response
	 * @return
	 */
	public String refundPre(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		if(tradeid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/jsps/msg.jsp";
		}
		Integer isForm = 2;
		Trade trade_ = ts.findTradeWithTradeid(tradeid);
		Integer tradeStatus = TradeUtils.getRealTradeStatus(trade_);
		Long refundTimes = hs.findHandleNumberWithOperateAndStatusAndIsValidAndTradeId(DBInitValConstant.HANDLE_OPERATE_REFUND,null,null, tradeid);
		if(refundTimes < 4){
			if(tradeStatus == 2){
				isForm = 1;
			}else if(tradeStatus == 6){
				Handle handle = hs.findRecentlyHandle(DBInitValConstant.HANDLE_OPERATE_REFUND, null, null, tradeid);
				RefundRecords refundRecords = rrs.findRefundRecordsWithHandleId(handle.getHandleid());
				if(handle.getIsValid() == 2 || (handle.getStatus_() == 1 && refundRecords.getStatus_() == 2)){
					isForm = 1;
				}else{
					isForm = 2;
				}
			}
		}else{
			isForm = 2;
		}
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Collection<Handle> coll = hs.findHandleListWithOperateAndStatusAndIsValidAndTradeId(DBInitValConstant.HANDLE_OPERATE_REFUND, null, null, tradeid);
		List<Handle> list = new ArrayList<>(coll);
		trade.setHandles(list);
		for (Handle handle : trade.getHandles()) {
			handle.setRefundRecords(rrs.findRefundRecordsWithHandleId(handle.getHandleid()));
		}
		request.setAttribute("trade", trade);
		request.setAttribute("isForm", isForm);
		return "f:/jsps/trade/refund.jsp";
		
	}
	
	/**
	 * 退款验证
	 * @param request
	 * @param response
	 * @return
	 */
	public String validateRefund(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//订单状态为待发货、退款中且退款次数小于等于三的
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		if(tradeid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/jsps/msg.jsp";
		}
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Integer tradeStatus = TradeUtils.getRealTradeStatus(trade);
		Long refundTimes = hs.findHandleNumberWithOperateAndStatusAndIsValidAndTradeId(DBInitValConstant.HANDLE_OPERATE_REFUND,null,null, tradeid);
		if(refundTimes < 4){
			if(tradeStatus == 2){
				return refund(request, response);
			}else if(tradeStatus == 6){
				Handle handle = hs.findRecentlyHandle(DBInitValConstant.HANDLE_OPERATE_REFUND, null, null, tradeid);
				RefundRecords refundRecords = rrs.findRefundRecordsWithHandleId(handle.getHandleid());
				if(handle.getIsValid() == 2){
					return this.refund(request, response);
				}else if(handle.getStatus_() == 1 && refundRecords.getStatus_() == 2){
					return this.refund(request, response);
				}else if(handle.getStatus_() == 1 && refundRecords.getStatus_() == 3){
					request.setAttribute("code", "failed");
					request.setAttribute("msg", "已退款");
					return "f:/jsps/msg.jsp";
				}else{
					request.setAttribute("code", "failed");
					request.setAttribute("msg", "正在退款中");
					return "f:/jsps/msg.jsp";
				}
			}
		}else{
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "退款次数已达上限！");
			return "f:/jsps/msg.jsp";
		}
		
		return null;
		
	}
	
	/**
	 * 退款
	 * @param request
	 * @param response
	 * @return
	 */
	public String refund(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		String refundReason = GetReqParamUtils.getParameter(request, "refundReason", null, null);
		if(tradeid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/jsps/msg.jsp";
		}
		ts.refund(tradeid);
		//为退款订单分配后台处理人员
		String currTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		String handleid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
		handleid+=UUIDUtils.getUUID(8);
		Handle handle = new Handle(handleid, tradeid, null, DBInitValConstant.HANDLE_OPERATE_REFUND, DBInitValConstant.HANDLE_STATUS_PROCESS,
				DBInitValConstant.HANDLE_ISVALID_VALID, currTime, currTime);
		hs.assignTrade(handle);
		//新建一条退款记录与订单退款处理记录关联
		String refundRecordsid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
		refundRecordsid+=UUIDUtils.getUUID(8);
		RefundRecords refundRecords = new RefundRecords(refundRecordsid, handleid, DBInitValConstant.REFUND_STATUS_PROCESS, refundReason, null, currTime, currTime);
		rrs.createRefundRecords(refundRecords);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "等待退款结果");
		return "f:/jsps/msg.jsp";
		
	}
	/**
	 * 取消退款验证
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void vilidateCancelRefund(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter(); 
		JSONObject jo = new JSONObject();
		if(tradeid == null){
			jo.put("errorCode", "error");
			pw.print(jo.toString());
			return;
		}
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Integer status = TradeUtils.getRealTradeStatus(trade);
		if(status == 6){
			jo.put("errorCode", "1");
			pw.print(jo.toString());
			return;
		}else{
			jo.put("errorCode", "订单状态有误");
			pw.print(jo.toString());
			return;
		}
		
	}
	/**
	 * 取消退款
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void cancelRefund(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		JSONObject jo = null;
		if(tradeid == null){
			jo = new JSONObject();
			jo.put("errorCode", "error");
			pw.print(jo.toString());
			return;
		}
		ts.cancleRefund(tradeid);
		//重新为发货订单分配后台处理人员
		String currTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		String handleid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
		handleid+=UUIDUtils.getUUID(8);
		Handle handle = new Handle(handleid, tradeid, null, DBInitValConstant.HANDLE_OPERATE_SHIP, DBInitValConstant.HANDLE_STATUS_PROCESS,
				DBInitValConstant.HANDLE_ISVALID_VALID, currTime, currTime);
		hs.assignTrade(handle);
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Integer tradeStatus = TradeUtils.getRealTradeStatus(trade);
		Handle handle2 = hs.findRecentlyHandle(DBInitValConstant.HANDLE_OPERATE_REFUND,null, null, tradeid);
		handle2.setRefundRecords(rrs.findRefundRecordsWithHandleId(handle2.getHandleid()));
		jo = new JSONObject();
		jo.put("handle", handle2);
		jo.put("errorCode", "1");
		jo.put("tradeStatus", tradeStatus);
		pw.print(jo.toString());
		return;
	}
	
	/**
	 * 评价准备
	 * @param request
	 * @param response
	 * @return
	 */
	public String validateRemark(HttpServletRequest request, HttpServletResponse response)throws Exception{
		return null;
		
	}
	/**
	 * 评价准备
	 * @param request
	 * @param response
	 * @return
	 */
	public String remarkPre(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		if(tradeid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/jsps/msg.jsp";
		}
		Trade trade = ts.findTradeWithTradeid(tradeid);
		trade.setStatus_(TradeUtils.getRealTradeStatus(trade));
		Collection<TradeItem> coll = ts.findTradeItemsWithTradeid(tradeid);
		List<TradeItem> list = new ArrayList<>(coll);
		trade.setTradeItems(list);
		for (TradeItem tradeItem : trade.getTradeItems()) {
			tradeItem.setBook(bs.selectBook(tradeItem.getBookid()));
		}
		request.setAttribute("trade", trade);
		return "f:/jsps/trade/remark.jsp";
		
	}
	/**
	 * 评价
	 * @param request
	 * @param response
	 * @return
	 */
	public String remark(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		Set<TradeItem> tradeItems = ts.findTradeItemsWithTradeid(tradeid);
		Set<Remark> remarks = new LinkedHashSet<>();
		User user = (User)HandleSessionAttrUtils.getAttrInSession(request, "user");
		String remarkText = null;
		Integer remarkLevel = null;
		String currTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		for (TradeItem tradeItem : tradeItems) {
			remarkText = GetReqParamUtils.getParameter(request, tradeItem.getItemid()+"Text", null, null);
			remarkLevel = GetReqParamUtils.getIntegerParameter(request, tradeItem.getItemid()+"Level", DBInitValConstant.REMARK_DEFAULT_LEVEL, null);
			remarks.add(new Remark(tradeItem.getItemid(), tradeItem.getBookid(),user.getUserid(), tradeItem.getItemid(), remarkText, 
					DBInitValConstant.REMARK_DEFAULT_REMARKIMGURL,currTime, remarkLevel, DBInitValConstant.IS_SHOW_YES));
		}
		//评论业务
		ts.remark(tradeid, remarks);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "评论成功!");
		return "f:/jsps/msg.jsp";
		
	}

	/**
	 * 加载订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Collection<TradeItem> coll = ts.findTradeItemsWithTradeid(tradeid);
		List<TradeItem> list = new ArrayList<>(coll);
		trade.setTradeItems(list);
		for (TradeItem tradeItem : trade.getTradeItems()) {
			tradeItem.setBook(bs.selectBook(tradeItem.getBookid()));
		}
		Integer status = TradeUtils.getRealTradeStatus(trade);
		if(status == 6){
			Handle handle = hs.findRecentlyHandle(DBInitValConstant.HANDLE_OPERATE_REFUND, null, null, trade.getTradeid());
			handle.setRefundRecords(rrs.findRefundRecordsWithHandleId(handle.getHandleid()));
			trade.setHandle(handle);
		}
		trade.setStatus_(status);
		request.setAttribute("trade", trade);
		//根据不同的订单状态订单详情页面的展示效果也不同
		request.setAttribute("status", status);
		return "/jsps/trade/desc.jsp";
	}

	/**
	 * 我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findTradePage(HttpServletRequest request, HttpServletResponse response)throws Exception {
		int pageNo = GetReqParamUtils.getIntegerParameter(request, "pageNo", 1, null);
		Integer status = GetReqParamUtils.getIntegerParameter(request, "status",0, null);
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		//根据时间来获取不同状态的订单
		CriteriaTrade ct = new CriteriaTrade(status, user.getUserid(), pageNo, null);
		//获取page
		Page<Trade> pages = ts.findTradePage(ct, 1);
		for (Trade trade : pages.getList()) {
			trade.setStatus_(TradeUtils.getRealTradeStatus(trade));
			Collection<TradeItem> tradeItemColl = ts.findTradeItemsWithTradeid(trade.getTradeid());
			List<TradeItem> tradeItems = new ArrayList<>(tradeItemColl);
			if(status == 6){
				Handle handle = hs.findRecentlyHandle(DBInitValConstant.HANDLE_OPERATE_REFUND, null, null, trade.getTradeid());
				handle.setRefundRecords(rrs.findRefundRecordsWithHandleId(handle.getHandleid()));
				trade.setHandle(handle);
			}
			for (TradeItem tradeItem : tradeItems) {
				tradeItem.setBook(bs.selectBook(tradeItem.getBookid()));
			}
			trade.setTradeItems(tradeItems);
		}
		String pageUrl = this.getPageUrl(request);
		request.setAttribute("pageUrl", pageUrl);;
		request.setAttribute("pages", pages);
		return "f:/jsps/trade/list.jsp";
	}
	
	
	/**
	 * 生成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String createTrade(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String shipAddrid = GetReqParamUtils.getParameter(request, "shipAddrid", null, null);
		User user = (User) HandleSessionAttrUtils.getAttrInSession(request, "user");
		String shoppingCartid = user.getShoppingCart().getShoppingCartid();
		ShoppingCart sc = scs.findShoppingCartWithUserid(user.getUserid());
		Set<ShoppingCartItem> sci = scs.findShoppingCartItemsByShoppingCartidAndIsChecked(shoppingCartid, 1);
		for (ShoppingCartItem shoppingCartItem : sci) {
			shoppingCartItem.setBook(bs.selectBook(shoppingCartItem.getBookid()));
		}
		sc.setShopppingCartItems(sci);
		ShippingAddress shippingAddress = sas.findShippingAddress(shipAddrid);
		if(sci.size() == 0){
			return "f:/enter.jsp";
		}
		//组装订单与订单项
		String currTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		String payExpirtyTime = TimeUtils.getFutureTime(TimeUtils.FORMATTER_1, DBInitValConstant.PAYOVERDAYS_1);
		String tradeid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "")+UUIDUtils.getUUID(8)+UUIDUtils.getUUID(4)+UUIDUtils.getUUID(2);
		Trade trade = new Trade(tradeid, sc.getUserid(), shippingAddress.getShipAddrid(), currTime, DBInitValConstant.PAYOVERDAYS_1, payExpirtyTime,
				sc.getShoppingCartMoney(),DBInitValConstant.TRADE_STATUS_NOT_PAID, DBInitValConstant.IS_SHOW_YES,
				shippingAddress.getCnee(),shippingAddress.getTel(),shippingAddress.getShipAddr());
		Set<TradeItem> tradeItems = new LinkedHashSet<TradeItem>();
		int i = 1;
		for (ShoppingCartItem shoppingCartItem : sc.getShopppingCartItems()) {
			String tradeItemid = tradeid;
			if(i<10){
				tradeItemid+="0"+i;
			}else{
				tradeItemid+=i;
			}
			tradeItems.add(new TradeItem(tradeItemid, shoppingCartItem.getBookid(), shoppingCartItem.getQuantity(), tradeid, shoppingCartItem.getBook().getCurrPrice()));
			i++;
		}
		//创建订单
		boolean flag = ts.createTrade(trade,tradeItems);
		//批量删除购物车项
		boolean flag2 = scs.deleteShoppingCartItems(shoppingCartid);
		//批量修改图书库存
		for (TradeItem tradeItem : tradeItems) {
			tradeItem.setQuantity(0-tradeItem.getQuantity());
		}
		bs.batchUpdateStoreNumber(tradeItems);
		request.setAttribute("trade", trade);
		return "f:/jsps/trade/tradesucc.jsp";
	}
	
	
	
	
	
	
	/**
	 * 根据订单id删除订单
	 * @param request
	 * @param response
	 */
//	public void deleteTradeWithTradeid(HttpServletRequest request,HttpServletResponse response){
//		String tradeid_ = request.getParameter("tradeid");
//		int tradeid = Integer.parseInt(tradeid_);
//		bs.deleteTradeWithTradeid(tradeid);
//		try {
//			response.getWriter().print("删除成功");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	/**
	 * 根据用户id删除订单
	 * @param request
	 * @param response
	 */
//	public void deleteTradesWithUserid(HttpServletRequest request,HttpServletResponse response){
//		
//	}
}

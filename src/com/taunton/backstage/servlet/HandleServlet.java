package com.taunton.backstage.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Category;
import com.taunton.javabean.Dict;
import com.taunton.javabean.Handle;
import com.taunton.javabean.Trade;
import com.taunton.javabean.TradeItem;
import com.taunton.service.BookService;
import com.taunton.service.CategoryService;
import com.taunton.service.DictService;
import com.taunton.service.HandleService;
import com.taunton.service.RefundRecordsService;
import com.taunton.service.TradeService;
import com.taunton.service.UserService;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DictConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.HandleSessionAttrUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.TradeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.web.CriteriaHandle;
import com.taunton.web.Page;

import org.apache.log4j.Logger;;

/**
 * 处理记录Servlet
 * 
 * @author taunton
 * 
 */
public class HandleServlet extends BaseServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(HandleServlet.class); 
	private static TradeService ts = DomainFactory.createDomainInstance(TradeService.class);
	private static HandleService hs = DomainFactory.createDomainInstance(HandleService.class);
	private static DictService ds = DomainFactory.createDomainInstance(DictService.class);
	private static RefundRecordsService rrs = DomainFactory.createDomainInstance(RefundRecordsService.class);
	private static UserService us = DomainFactory.createDomainInstance(UserService.class);
	private static BookService bs = DomainFactory.createDomainInstance(BookService.class);
	
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
	public String findPageWithOperateAndStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int pageNo = GetReqParamUtils.getIntegerParameter(request, "page", 1, "\\d+");
		Integer handleOperate = GetReqParamUtils.getIntegerParameter(request, "operate", DBInitValConstant.HANDLE_OPERATE_SHIP, null);
		Integer handleStatus = GetReqParamUtils.getIntegerParameter(request, "status", null, null);
		Integer handleIsValid = GetReqParamUtils.getIntegerParameter(request, "isValid", DBInitValConstant.HANDLE_ISVALID_VALID, null);
		
		Integer handleOperate_ = (Integer) request.getAttribute("operate");
		Integer handleStatus_ = (Integer) request.getAttribute("status");
		if(handleOperate != null && handleOperate_ != null){
			handleOperate = handleOperate_;
			handleStatus = handleStatus_;
		}
		BackStageUser user = (BackStageUser)HandleSessionAttrUtils.getAttrInSession(request, "backstageuser");
		Page<Handle> pages =null;
		CriteriaHandle ch = new CriteriaHandle(handleStatus, handleOperate, handleIsValid, user.getUserid(), pageNo, null);
		pages = hs.findHandleWithOperateAndStatusAndBackStageUserId(ch);
		
		//对pages里的handlelist处理
		for (Handle handle : pages.getList()) {
			handle.setTrade(ts.findTradeWithTradeid(handle.getTradeid()));
		}
		long handlingNum = hs.findHandleNumberWithOperateAndStatusAndIsValidAndBackStageUserId(handleOperate, 
				DBInitValConstant.HANDLE_STATUS_PROCESS, DBInitValConstant.HANDLE_ISVALID_VALID, user.getUserid());
		long handledNum = hs.findHandleNumberWithOperateAndStatusAndIsValidAndBackStageUserId(handleOperate, 
				DBInitValConstant.HANDLE_STATUS_SUCCESS, DBInitValConstant.HANDLE_ISVALID_VALID, user.getUserid());
		String pageUrl = this.getPageUrl(request);
		request.setAttribute("pageUrl", pageUrl);
		request.setAttribute("operate", handleOperate);
		request.setAttribute("handlingNum", handlingNum);
		request.setAttribute("handledNum", handledNum);
		request.setAttribute("pages",pages);
		return "f:/adminjsps/admin/trade/list.jsp";
	}
	
	/**
	 * 发货准备
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String shipPre(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		String handleid = GetReqParamUtils.getParameter(request, "handleid", null, null);
		if(tradeid == null || handleid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/adminjsps/msg.jsp";
		}
		Set<Dict> expressCompList = ds.findDictListByType(DictConstant.DICT_TYPE_EXPRESSCOMP);
		request.setAttribute("tradeid", tradeid);
		request.setAttribute("handleid", handleid);
		request.setAttribute("expressCompList", expressCompList);
		return "f:/adminjsps/admin/trade/ship.jsp";
	}
	
	/**
	 * 发货
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String ship(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		String handleid = GetReqParamUtils.getParameter(request, "handleid", null, null);
		String expressOrder = GetReqParamUtils.getParameter(request, "expressOrder", null, null);
		String dictid = GetReqParamUtils.getParameter(request, "dictid", null, null);
		
		if(tradeid == null || handleid == null){
			request.setAttribute("msg", "error");
			return "f:/adminjsps/admin/trade/ship.jsp";
		}
		
		Dict dict = ds.findDictByDictId(dictid);
		Trade trade = new Trade();
		String currTime = TimeUtils.getCurrTime(TimeUtils.FORMATTER_1);
		trade.setTradeid(tradeid);
		trade.setShipTime(currTime);
		String confirmReceiptExpirtyTime = TimeUtils.getFutureTime(TimeUtils.FORMATTER_1, DBInitValConstant.CONFIRM_RECEIPT_OVERDAYS_3);
		trade.setConfirmReceiptExpirtyTime(confirmReceiptExpirtyTime);
		trade.setConfirmReceiptOverDays(DBInitValConstant.CONFIRM_RECEIPT_OVERDAYS_3);
		trade.setStatus_(DBInitValConstant.TRADE_STATUS_SHIP);
		trade.setExpressComp(dict.getVal());
		trade.setExpressOrder(expressOrder);
		
		//更新订单发货时间、确认收货超时天数及时间、订单状态、快递单号及公司
		ts.ship(trade);
		
		//更新订单处理记录状态为已完成
		hs.updateHandleStatusByHandleId(DBInitValConstant.HANDLE_STATUS_SUCCESS, handleid);

		request.setAttribute("operate", DBInitValConstant.HANDLE_OPERATE_SHIP);
		request.setAttribute("status", DBInitValConstant.HANDLE_STATUS_PROCESS);
		return findPageWithOperateAndStatus(request, response);
	}
	
	/**
	 * 拒绝退款准备
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String refuseRefundPre(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		String handleid = GetReqParamUtils.getParameter(request, "handleid", null, null);
		if(tradeid == null || handleid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/adminjsps/msg.jsp";
		}
		Trade trade = ts.findTradeWithTradeid(tradeid);
		Collection<Handle> coll = hs.findHandleListWithOperateAndStatusAndIsValidAndTradeId(DBInitValConstant.HANDLE_OPERATE_REFUND, null, null, tradeid);
		List<Handle> list = new ArrayList<>(coll);
		trade.setHandles(list);
		for (Handle handle : trade.getHandles()) {
			handle.setRefundRecords(rrs.findRefundRecordsWithHandleId(handle.getHandleid()));
		}
		request.setAttribute("trade", trade);
		request.setAttribute("handleid", handleid);
		request.setAttribute("isView", 2);
		return "f:/adminjsps/admin/trade/refund.jsp";
	}
	
	/**
	 * 拒绝退款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String refuseRefund(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String refuseRefundReason = GetReqParamUtils.getParameter(request, "refuseRefundReason", null, null);
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		String handleid = GetReqParamUtils.getParameter(request, "handleid", null, null);
		if(tradeid == null || handleid == null || refuseRefundReason == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/adminjsps/msg.jsp";
		}
		String currTime = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_1);
		
		//更新订单处理记录为完成
		hs.updateHandleStatusByHandleId(DBInitValConstant.HANDLE_STATUS_SUCCESS, handleid);
		//更新退款记录
		rrs.handleRefund(DBInitValConstant.REFUND_STATUS_REFUSED, refuseRefundReason,currTime , handleid);
		
		//当此订单退款次数大于三次时
		Long refundTimes = hs.findHandleNumberWithOperateAndStatusAndIsValidAndTradeId(DBInitValConstant.HANDLE_OPERATE_REFUND,null,null, tradeid);
		if(refundTimes > 3){
			ts.updateStatusWithTradeid(tradeid, DBInitValConstant.TRADE_STATUS_PAID);
			String handleid2 = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
			handleid2+=UUIDUtils.getUUID(8);
			Handle handle = new Handle(handleid2, tradeid, null, DBInitValConstant.HANDLE_OPERATE_SHIP, DBInitValConstant.HANDLE_STATUS_PROCESS,
					DBInitValConstant.HANDLE_ISVALID_VALID, currTime, currTime);
			hs.assignTrade(handle);
		}
		
		request.setAttribute("operate", DBInitValConstant.HANDLE_OPERATE_REFUND);
		request.setAttribute("status", DBInitValConstant.HANDLE_STATUS_PROCESS);
		return findPageWithOperateAndStatus(request, response);
		
	}
	
	/**
	 * 同意退款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String agreeRefund(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		String handleid = GetReqParamUtils.getParameter(request, "handleid", null, null);
		if(tradeid == null || handleid == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/adminjsps/msg.jsp";
		}
		String currTime = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_1);
		
		//更新订单处理记录
		hs.updateHandleStatusByHandleId(DBInitValConstant.HANDLE_STATUS_SUCCESS, handleid);
		//更新退款记录
		rrs.handleRefund(DBInitValConstant.REFUND_STATUS_SUCCESS, null,currTime , handleid);
		//更新订单状态为退款完成
		ts.updateStatusWithTradeid(tradeid, DBInitValConstant.TRADE_STATUS_REFUND_FINISHED);
		Trade trade = ts.findTradeWithTradeid(tradeid);
		//钱款退回账户
		us.changeBalance(trade.getUserid(), trade.getTradeMoney());
		
		request.setAttribute("operate", DBInitValConstant.HANDLE_OPERATE_REFUND);
		request.setAttribute("status", DBInitValConstant.HANDLE_STATUS_PROCESS);
		return findPageWithOperateAndStatus(request, response);
	}

	/**
	 * 查看处理记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String load(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String tradeid = GetReqParamUtils.getParameter(request, "tradeid", null, null);
		Integer operate = GetReqParamUtils.getIntegerParameter(request, "operate", null, null);
		if(tradeid == null || operate == null){
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "error");
			return "f:/adminjsps/msg.jsp";
		}
		if(operate == 1){
			Trade trade = ts.findTradeWithTradeid(tradeid);
			Collection<TradeItem> coll = ts.findTradeItemsWithTradeid(tradeid);
			List<TradeItem> list = new ArrayList<>(coll);
			trade.setTradeItems(list);
			for (TradeItem tradeItem : trade.getTradeItems()) {
				tradeItem.setBook(bs.selectBook(tradeItem.getBookid()));
			}
			Integer status = TradeUtils.getRealTradeStatus(trade);
			trade.setStatus_(status);
			request.setAttribute("trade", trade);
			//根据不同的订单状态订单详情页面的展示效果也不同
			request.setAttribute("status", status);
			return "f:/adminjsps/admin/trade/desc.jsp";
		}else if(operate == 2){
			Trade trade = ts.findTradeWithTradeid(tradeid);
			Collection<Handle> coll = hs.findHandleListWithOperateAndStatusAndIsValidAndTradeId(DBInitValConstant.HANDLE_OPERATE_REFUND, null, null, tradeid);
			List<Handle> list = new ArrayList<>(coll);
			trade.setHandles(list);
			for (Handle handle : trade.getHandles()) {
				handle.setRefundRecords(rrs.findRefundRecordsWithHandleId(handle.getHandleid()));
			}
			request.setAttribute("trade", trade);
			request.setAttribute("isView", 1);
			return "f:/adminjsps/admin/trade/refund.jsp";
		}
		request.setAttribute("code", "failed");
		request.setAttribute("msg", "error");
		return "f:/adminjsps/msg.jsp";
	}
	
}

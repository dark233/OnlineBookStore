package com.taunton.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.taunton.javabean.Remark;
import com.taunton.service.RemarkService;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public class RemarkServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RemarkServlet.class); 
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
	 * 获取评价等级url路径，附在图书详情页中的评价选项单选按钮上
	 * @param request
	 * @return
	 */
	private String getRemarkLevelUrl(HttpServletRequest request){
		String reqUrl = request.getRequestURI() + "?" + request.getQueryString();
		/*
		 * 如果url中存在remarklevel参数，截取掉，如果不存在那就不用截取。
		 */
		int index = reqUrl.lastIndexOf("&minLevel=");
		int index2 = reqUrl.lastIndexOf("&pageNo=");
		if(index != -1) {
			reqUrl = reqUrl.substring(0, index);
		}else if(index2 != -1){
			reqUrl = reqUrl.substring(0, index2);
		}
		return reqUrl;
	}
	
	/**
	 * 通过评论级别查找图书评论集合
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByBookId(HttpServletRequest request,
			HttpServletResponse response) {
		Page<Remark> pages = null;
		int pageNo = GetReqParamUtils.getIntegerParameter(request, "pageNo", 1, null);
		String bookid = GetReqParamUtils.getParameter(request, "bookid",null, null);
		CriteriaRemark cr = new CriteriaRemark();
		cr.setPageNo(pageNo);
		cr.setBookid(bookid);
		cr = GetReqParamUtils.getRemarkLevel(request, cr);
		logger.info("请求发送过来的参数:	"+"minLevel = "+cr.getMinLevel()+",maxLevel	= "+cr.getMinLevel()+",bookid = "+cr.getBookid()+",pageNo = "+cr.getPageNo());
		pages = rs.findRemarkPageByBookId(cr);
		logger.info("查询到的图书bookpage信息为: pageList = "+pages.getList()+",pageSize = "+pages.getPageSize()+
				",pageNo = "+pages.getPageNo()+",totalItemNumber = "+pages.getTotalItemNumber()+",totalPageNumber = "+pages.getTotalPageNumber());
		String pageUrl = this.getPageUrl(request);
		String remarkLevelUrl = this.getRemarkLevelUrl(request);
		int minLevel = cr.getMinLevel();
		request.setAttribute("pages", pages);
		request.setAttribute("remarkLevel", minLevel);
		request.setAttribute("pageUrl", pageUrl);
		request.setAttribute("remarkLevelUrl", remarkLevelUrl);
		return "f:/jsps/remark/remark.jsp";
	}
}

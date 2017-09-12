package com.taunton.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;

import com.taunton.javabean.Book;
import com.taunton.service.BookService;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.web.CriteriaBook;
import com.taunton.web.Page;

import org.apache.log4j.Logger;;

/**
 * 用于图书的servet
 * 
 * @author taunton
 * 
 */
public class BookServlet extends BaseServlet {

	
	private static final long serialVersionUID = -713390674049348842L;
	private static Logger logger = Logger.getLogger(BookServlet.class); 
	private BookService bs = DomainFactory.createDomainInstance(BookService.class);
	
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
	 * 获取排序的查询路径(去掉orderField和pageNo参数之后的路径，用于在附在排序按键上)
	 * @param request
	 * @return
	 */
	private String getOrderUrl(HttpServletRequest request){
		String reqUrl = request.getRequestURI() + "?" + request.getQueryString();
		/*
		 * 如果url中存在pageNo参数，截取掉，如果不存在那就不用截取。
		 */
		int index = reqUrl.lastIndexOf("&pageNo=");
		int index2 = reqUrl.lastIndexOf("&orderField=");
		if(index2 != -1) {
			reqUrl = reqUrl.substring(0, index2);
		}else if(index != -1){
			reqUrl = reqUrl.substring(0, index);
		}
		return reqUrl;
	}
	private String getPriceUrl(HttpServletRequest request){
		String reqUrl = request.getRequestURI() + "?" + request.getQueryString();
		/*
		 * 如果url中存在pageNo参数，截取掉，如果不存在那就不用截取。
		 */
		int index = reqUrl.lastIndexOf("&pageNo=");
		int index2 = reqUrl.lastIndexOf("&orderField=");
		int index3 = reqUrl.lastIndexOf("&maxPrice=");
		int index4 = reqUrl.lastIndexOf("&minPrice=");
		if(index4 != -1){
			reqUrl = reqUrl.substring(0,index4);
		}else if(index3 != -1){
			reqUrl = reqUrl.substring(0, index3);
		}else if(index2 != -1) {
			reqUrl = reqUrl.substring(0, index2);
		}else if(index != -1){
			reqUrl = reqUrl.substring(0, index);
		}
		return reqUrl;
	}
	
	/**
	 * 通过图书类型查找图书集合
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByCategory(HttpServletRequest request,
			HttpServletResponse response) {
		Page<Book> pages = null;
		//二级条件
		int pageNo = GetReqParamUtils.getIntegerParameter(request, "pageNo", 1, "\\d+");
		float initVal1 = 0;
		float initVal2 = Integer.MAX_VALUE;
		float minPrice = GetReqParamUtils.getFloatParameter(request, "minPrice",initVal1, null);
		float maxPrice = GetReqParamUtils.getFloatParameter(request, "maxPrice", initVal2, null);
		String orderField = GetReqParamUtils.getParameter(request, "orderField", null,null);
		//一级条件，查询入口
		String cid = GetReqParamUtils.getParameter(request, "cid",null, null);
		logger.info("请求发送过来的参数:	"+"minPrice = "+minPrice+",maxPrice	= "+maxPrice+",orderField = "+orderField+",pageNo = "+pageNo+",cid = "+cid);
		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo,orderField,cid);
		pages = bs.findBookListByCategory(cb,1);
		logger.info("查询到的图书bookpage信息为: pageList = "+pages.getList()+",pageSize = "+pages.getPageSize()+
				",pageNo = "+pages.getPageNo()+",totalItemNumber = "+pages.getTotalItemNumber()+",totalPageNumber = "+pages.getTotalPageNumber());
		String pageUrl = this.getPageUrl(request);
		String orderUrl = this.getOrderUrl(request);
		String priceUrl = this.getPriceUrl(request);
		request.setAttribute("minPrice", GetReqParamUtils.getFloatParameter(request, "minPrice", null, null));
		request.setAttribute("maxPrice",GetReqParamUtils.getFloatParameter(request, "maxPrice", null, null));
		request.setAttribute("orderField",orderField);
		request.setAttribute("pages", pages);
		request.setAttribute("pageUrl", pageUrl);
		request.setAttribute("orderUrl", orderUrl);
		request.setAttribute("priceUrl", priceUrl);
		return "f:/jsps/book/list.jsp";
	}
	
	
	/**
	 * 多条件组合查询
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCombination(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		Page<Book> pages = null;
		//二级条件
		int pageNo = GetReqParamUtils.getIntegerParameter(request, "pageNo", 1, null);
		float initVal1 = 0;
		float initVal2 = Integer.MAX_VALUE;
		float minPrice = GetReqParamUtils.getFloatParameter(request, "minPrice",initVal1, null);
		float maxPrice = GetReqParamUtils.getFloatParameter(request, "maxPrice", initVal2, null);
		String orderField = GetReqParamUtils.getParameter(request, "orderField",null, null);
		//一级条件，查询入口
//		Book book =  CommonUtils.toBean(request.getParameterMap(), Book.class);
		Book book = new Book();
		book.setTitle(GetReqParamUtils.getParameter(request, "title",null, null));
		book.setAuthor(GetReqParamUtils.getParameter(request, "author", null,null));
		book.setPress(GetReqParamUtils.getParameter(request, "press",null, null));
		logger.info("请求发送过来的参数:	"+"minPrice = "+minPrice+",maxPrice	= "+maxPrice+",orderField = "+orderField+",pageNo = "+pageNo+",book = "+book.toString());
		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo,orderField);
		pages = bs.findBookListByCombination(cb, book,1);
		logger.info("查询到的图书pages信息为: pageList = "+pages.getList()+",pageSize = "+pages.getPageSize()+
				",pageNo = "+pages.getPageNo()+",totalItemNumber = "+pages.getTotalItemNumber()+",totalPageNumber = "+pages.getTotalPageNumber());
		String pageUrl = this.getPageUrl(request);
		String orderUrl = this.getOrderUrl(request);
		String priceUrl = this.getPriceUrl(request);
		request.setAttribute("minPrice",GetReqParamUtils.getFloatParameter(request, "minPrice", null, null));
		request.setAttribute("maxPrice",GetReqParamUtils.getFloatParameter(request, "maxPrice", null, null));
		request.setAttribute("orderField",orderField);
		request.setAttribute("pages", pages);
		request.setAttribute("pageUrl", pageUrl);
		request.setAttribute("orderUrl", orderUrl);
		request.setAttribute("priceUrl", priceUrl);
		return "f:/jsps/book/list.jsp";
		
		
	}
	
	public String findBySearch(HttpServletRequest request,
			HttpServletResponse response) {
		Page<Book> pages = null;
		//二级条件
		int pageNo = GetReqParamUtils.getIntegerParameter(request, "pageNo", 1, null);
		float initVal1 = 0;
		float initVal2 = Integer.MAX_VALUE;
		float minPrice = GetReqParamUtils.getFloatParameter(request, "minPrice",initVal1, null);
		float maxPrice = GetReqParamUtils.getFloatParameter(request, "maxPrice", initVal2, null);
		String orderField = GetReqParamUtils.getParameter(request, "orderField",null, null);
		//一级条件，查询入口
		String search = GetReqParamUtils.getParameter(request, "search",null, null);
		logger.info("请求发送过来的参数:	"+"minPrice = "+minPrice+",maxPrice	= "+maxPrice+",orderField = "+orderField+",pageNo = "+pageNo+",search = "+search);
		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo,orderField);
		cb.setSearch(search);
		pages = bs.findBookListBySearch(cb,1);
		logger.info("查询到的图书pages信息为: pageList = "+pages.getList()+",pageSize = "+pages.getPageSize()+
				",pageNo = "+pages.getPageNo()+",totalItemNumber = "+pages.getTotalItemNumber()+",totalPageNumber = "+pages.getTotalPageNumber());
		String pageUrl = this.getPageUrl(request);
		String orderUrl = this.getOrderUrl(request);
		String priceUrl = this.getPriceUrl(request);
		request.setAttribute("minPrice", GetReqParamUtils.getFloatParameter(request, "minPrice", null, null));
		request.setAttribute("maxPrice",GetReqParamUtils.getFloatParameter(request, "maxPrice", null, null));
		request.setAttribute("search",search);
		request.setAttribute("orderField",orderField);
		request.setAttribute("pages", pages);
		request.setAttribute("pageUrl", pageUrl);
		request.setAttribute("orderUrl", orderUrl);
		request.setAttribute("priceUrl", priceUrl);
		return "f:/jsps/book/list.jsp";
	}
	
	
	/**
	 * 查找一本图书
	 * @param request
	 * @param response
	 */
	public String findOneBook(HttpServletRequest request,HttpServletResponse response){
		String bookid = GetReqParamUtils.getParameter(request, "bookid",null, null);
		logger.info("请求传送过来的参数: bookid = "+bookid);
		Book book = bs.selectBook(bookid);
		logger.info("查询到的一本图书信息: book = "+book);
		request.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
		
//		return "/WEB-INF/bookInfo.jsp";
		
	}
	
	
}

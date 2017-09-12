package com.taunton.backstage.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import cn.itcast.commons.CommonUtils;

import com.taunton.javabean.Book;
import com.taunton.javabean.Category;
import com.taunton.service.BookService;
import com.taunton.service.CategoryService;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.web.CriteriaBook;
import com.taunton.web.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;;

/**
 * 图书Servlet
 * 
 * @author taunton
 * 
 */
public class BookServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BookServlet.class); 
	private CategoryService cs = DomainFactory.createDomainInstance(CategoryService.class);
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
		pages = bs.findBookListByCategory(cb,null);
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
		return "f:/adminjsps/admin/book/list.jsp";
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
		pages = bs.findBookListByCombination(cb, book,null);
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
		return "f:/adminjsps/admin/book/list.jsp";
		
		
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
		pages = bs.findBookListBySearch(cb,null);
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
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	
	/**
	 * 查找一本图书
	 * @param request
	 * @param response
	 */
	public String findOneBook(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String bookid = GetReqParamUtils.getParameter(request, "bookid",null, null);
		logger.info("请求传送过来的参数: bookid = "+bookid);
		Book book = bs.selectBook(bookid);
		book.setCategory(cs.findOneCategory(book.getCid()));
		logger.info("查询到的一本图书信息: book = "+book);
		Set<Category> parents = cs.findParents();
		logger.info("查找根父类图书分类结果 : "+parents.toString());
		Set<Category> children = cs.findChildren(book.getCategory().getPid());
		logger.info("查找对应图书所属分类的父分类下的所有子分类结果 : "+parents.toString());
		request.setAttribute("book", book);
		request.setAttribute("parents", parents);
		request.setAttribute("children", children);
		return "f:/adminjsps/admin/book/desc.jsp";
		
//		return "/WEB-INF/bookInfo.jsp";
		
	}
	
	/**
	 * 查询所有图书分类
	 */
	public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp)
			throws Exception,ServletException, IOException {
		Set<Category> parents = cs.findAll();
		logger.info("查找所有图书分类结果 : "+parents.toString());
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/book/left.jsp";
	}
	
	/**
	 * 根据图书父类id获取相应的子图书分类集合，通过json写回
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void findChildrenByParent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("application/json;charset=utf-8");
		String pid = GetReqParamUtils.getParameter(request, "pid", null, null);
		PrintWriter pw = response.getWriter();
		
		Set<Category> children = cs.findChildren(pid);
		Collection<Category> coll = children;
		List<Category> list = new ArrayList<>(coll);
		JSONArray ja = JSONArray.fromObject(list); 
		pw.print(ja.toString());
	}
	
	/**
	 * 图书重新上架
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void putAwayAgain(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String bookid = GetReqParamUtils.getParameter(request, "id", null, null);
		response.setContentType("application/json;charset=utf-8");
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		if(bookid == null){
			jo.put("flag", "failed");
			pw.print(jo.toString());
			return;
		}
		bs.putAwayAgain(bookid);
		jo.put("flag", "success");
		pw.print(jo.toString());
		return;
	
		
	}
	
	/**
	 * 图书上架准备
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String putAwayPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. 获取所有一级分类，保存到reques域
		 * 2. 转发到add.jsp，该页面会在下拉列表中显示所有一级分类
		 */
		Set<Category> parents = cs.findParents();
		request.setAttribute("parents", parents);
		return "f:/adminjsps/admin/book/add.jsp";
	}
	
	/**
	 * 图书下架
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void soldOut(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String bookid = GetReqParamUtils.getParameter(request, "id", null, null);
		response.setContentType("application/json;charset=utf-8");
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		if(bookid == null){
			jo.put("flag", "failed");
			pw.print(jo.toString());
			return;
		}
		bs.soldOut(bookid);
		jo.put("flag", "success");
		pw.print(jo.toString());
		return;
	}
	
	/**
	 * 修改图书基本信息（不包含封面）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Map map = request.getParameterMap();
		logger.info(map.get("title"));
		Book book = CommonUtils.toBean(map, Book.class);
		String title = GetReqParamUtils.getParameter(request, "title", null, null);
		String author = GetReqParamUtils.getParameter(request, "author", null, null);
		String press = GetReqParamUtils.getParameter(request, "press", null, null);
		String paper = GetReqParamUtils.getParameter(request, "paper", null, null);
		book.setTitle(title);
		book.setAuthor(author);
		book.setPress(press);;
		book.setPaper(paper);;
		boolean flag = bs.edit(book);
		if(flag){
			request.setAttribute("code", "success");
			request.setAttribute("msg", "修改图书基本信息成功！");
			return "f:/adminjsps/msg.jsp";
		}else{
			request.setAttribute("code", "failed");
			request.setAttribute("msg", "修改图书基本信息失败！");
			return "f:/adminjsps/msg.jsp";		
		}
		
	}
	
	

}

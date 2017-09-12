package com.taunton.backstage.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import cn.itcast.commons.CommonUtils;

import com.taunton.javabean.Book;
import com.taunton.javabean.Category;
import com.taunton.service.BookService;
import com.taunton.service.CategoryService;
import com.taunton.utils.ConnectionContext;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.GetReqParamUtils;
import com.taunton.utils.JDBCUtils;
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
 * 图书上架Servlet
 * 
 * @author taunton
 * 
 */
public class PutAwayBookServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PutAwayBookServlet.class); 
	private CategoryService cs = DomainFactory.createDomainInstance(CategoryService.class);
	private BookService bs = DomainFactory.createDomainInstance(BookService.class);
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			logger.error(e1);
		}
		ConnectionContext.getInstance().bind(conn);
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		/*
		 * 1. commons-fileupload的上传三步
		 */
		// 创建工厂
		FileItemFactory factory = new DiskFileItemFactory();
		/*
		 * 2. 创建解析器对象
		 */
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(1024 * 1024);//设置单个上传的文件上限为1024KB
		/*
		 * 3. 解析request得到List<FileItem>
		 */
		List<FileItem> fileItemList = null;
		try {
			fileItemList = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			error("上传的文件超出了1mb", request, response);
			return;
		}
		
		/*
		 * 4. 把List<FileItem>封装到Book对象中
		 * 4.1 首先把“普通表单字段”放到一个Map中，再把Map转换成Book和Category对象，再建立两者的关系
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		for(FileItem fileItem : fileItemList) {
			if(fileItem.isFormField()) {//如果是普通表单字段
				map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
			}
		}
		Book book = CommonUtils.toBean(map, Book.class);//把Map中大部分数据封装到Book对象中
//		Category category = CommonUtils.toBean(map, Category.class);//把Map中cid封装到Category中
		Book book2 = bs.findBookByTitle(book.getTitle());
		if(book2 != null){
			error("图书名称已存在", request, response);
			return;
		}
		/*
		 * 4.2 把上传的图片保存起来
		 *   > 获取文件名：截取之
		 *   > 给文件添加前缀：使用uuid前缀，为也避免文件同名现象
		 *   > 校验文件的扩展名：只能是jpg
		 *   > 校验图片的尺寸
		 *   > 指定图片的保存路径，这需要使用ServletContext#getRealPath()
		 *   > 保存之
		 *   > 把图片的路径设置给Book对象
		 */
		// 获取文件名
		FileItem fileItem = fileItemList.get(1);//获取大图
		String filename = fileItem.getName();
		// 截取文件名，因为部分浏览器上传的绝对路径
		int index = filename.lastIndexOf("\\");
		if(index != -1) {
			filename = filename.substring(index + 1);
		}
		String bookid = TimeUtils.formatDate(new Date(), TimeUtils.FORMATTER_2).replace("-", "");
		bookid+=UUIDUtils.getUUID(8)+UUIDUtils.getUUID(2);
		// 给文件名添加uuid前缀，避免文件同名现象
		// 校验文件名称的扩展名
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("上传的图片扩展名必须是JPG", request, response);
			return;
		}
		filename = bookid+".jpg";
		
		// 校验图片的尺寸
		// 保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
		/*
		 * 保存图片：
		 * 1. 获取真实路径
		 */
		String savepath = this.getServletContext().getRealPath("/book_img");
		/*
		 * 2. 创建目标文件
		 */
		File destFile = new File(savepath, filename);
		/*
		 * 3. 保存文件
		 */
		try {
			fileItem.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
		} catch (Exception e) {
			destFile.delete();
			throw new RuntimeException(e);
		}
		// 校验尺寸
		// 1. 使用文件路径创建ImageIcon
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
		// 2. 通过ImageIcon得到Image对象
		Image image = icon.getImage();
		// 3. 获取宽高来进行校验
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
			destFile.delete();//删除图片
			error("您上传的图片尺寸超出了350*350！", request, response);
		}
		
		book.setId(bookid);
		book.setSalesAmount(DBInitValConstant.BOOK_SALESAMOUNT);
		book.setPutAwayTime(TimeUtils.getCurrTime(TimeUtils.FORMATTER_1));
		book.setImgCacheUrl("book_img/" + filename);
		book.setIsShow(DBInitValConstant.BOOK_ISSHOW_YES);
		//调用图书上架业务
		try {
			bs.putAway(book);
			conn.commit();
		} catch (Exception e) {
			destFile.delete();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("图书上架回滚失败！");
			}
			logger.error("图书上架回滚"+e);
			this.error("error", request, response);
			return;
		}finally{
			ConnectionContext.getInstance().remove();
			JDBCUtils.release(conn);
			logger.info("图书上架释放数据库连接:"+conn);
		}
		
		// 保存成功信息转发到msg.jsp
		request.setAttribute("code", "success");
		request.setAttribute("msg", "添加图书成功！");
		request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);	
		}
	/*
	 * 保存错误信息，转发到add.jsp
	 */
	private void error(String msg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.setAttribute("parents", cs.findParents());//所有一级分类
		request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").
				forward(request, response);
	}
}


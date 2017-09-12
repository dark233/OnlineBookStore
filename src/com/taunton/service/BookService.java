package com.taunton.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jms.Session;
import javax.management.RuntimeErrorException;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.apache.log4j.Logger;

import com.taunton.dao.impl.BackStageUserDAOImpl;
import com.taunton.dao.impl.BookDAOImpl;
import com.taunton.dao.impl.RemarkDAOImpl;
import com.taunton.dao.impl.ShippingAddressDAOImpl;
import com.taunton.dao.impl.ShoppingCartDAOImpl;
import com.taunton.dao.impl.ShoppingCartItemDAOImpl;
import com.taunton.dao.impl.TradeDAOImpl;
import com.taunton.dao.impl.TradeItemDAOImpl;
import com.taunton.dao.impl.UserDAOImpl;
import com.taunton.exception.CheckException;
import com.taunton.exception.DBException;
import com.taunton.javabean.BackStageUser;
import com.taunton.javabean.Book;
import com.taunton.javabean.Remark;
import com.taunton.javabean.ShoppingCart;
import com.taunton.javabean.ShoppingCartItem;
import com.taunton.javabean.Trade;
import com.taunton.javabean.TradeItem;
import com.taunton.javabean.User;
import com.taunton.servlet.BookServlet;
import com.taunton.utils.DBInitValConstant;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.utils.ReflectionUtils;
import com.taunton.utils.TimeUtils;
import com.taunton.utils.UUIDUtils;
import com.taunton.web.CriteriaBook;
import com.taunton.web.Page;

public class BookService {
	
	private static Logger logger = Logger.getLogger(BookService.class); 
	private BackStageUserDAOImpl bsud = null;
	private RemarkDAOImpl rd = null;
	private BookDAOImpl bd = null;
	public BookService(){
		bsud = DomainFactory.createDomainInstance(BackStageUserDAOImpl.class);
		rd = DomainFactory.createDomainInstance(RemarkDAOImpl.class);
		bd = DomainFactory.createDomainInstance(BookDAOImpl.class);
	}
	
	/**
	 * 根据图书类型id获取相应类型下的图书数量
	 * @param cid
	 * @return
	 */
	public long findBookCountByCategory(String cid){
		long bookNum = 0;
		try {
			bookNum = bd.getBookNumWithCid(cid);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return bookNum;
	}
	
	/**
	 * 通过图书类型查询得到Page
	 * @param cb
	 * @return
	 */
	public Page<Book> findBookListByCategory(CriteriaBook cb,Integer isShow)  {
		Page<Book> bookpage = null;
		try {
			bookpage = bd.getPageWithCategory(cb,isShow);
		} catch (Exception e) {
			logger.info("通过图书类型查询图书集合出现异常"+e);
			throw new RuntimeException("通过图书类型查询图书集合出现异常"+e);
		}
		return bookpage;

	}
	/**
	 * 组合查询得到Page
	 * @param cb
	 * @param book
	 * @return
	 */
	public Page<Book> findBookListByCombination(CriteriaBook cb,Book book,Integer isShow){
		Page<Book> bookpage = null;
		try {
			bookpage = bd.getPageWithCombination(cb, book,isShow);
		} catch (Exception e) {
			logger.info("通过组合查询图书集合出现异常"+e);
			throw new RuntimeException("通过组合查询图书集合出现异常"+e);
		}
		return bookpage;
	}
	/**
	 * 通过搜索查询得到Page
	 * @param cb
	 * @return
	 */
	public Page<Book> findBookListBySearch(CriteriaBook cb,Integer isShow){
		Page<Book> bookpage = null;
		try {
			bookpage = bd.getPageWithSearch(cb,isShow);
		} catch (Exception e) {
			logger.info("通过搜索图书集合出现异常"+e);
			throw new RuntimeException("通过搜索图书集合出现异常"+e);
		}
		return bookpage;
	}
	/**
	 * 查询得到一本书
	 * @param id
	 * @return
	 */
	public Book selectBook(String id){
		Book book = null;
		try {
			book = bd.getBook(id);
		} catch (Exception e) {
			logger.info("通过图书id查询图书出现异常"+e);
			throw new RuntimeException(e);
		}
		return book;
	}
	
	/**
	 * 通过图书名称获取相应图书
	 * @param title
	 * @return
	 */
	public Book findBookByTitle(String title){
		Book book = null;
		try {
			book = bd.getBookWithTitle(title);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return book;
	}
	
	/**
	 * 图书上架
	 * @param book
	 * @return
	 */
	public boolean putAway(Book book){
		boolean flag = false;
		try {
			bd.addBook(book);
			flag = true;
		} catch (SQLException e) {
			logger.error("图书上架失败"+e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	/**
	 * 图书重新上架
	 * @param bookid
	 * @return
	 */
	public boolean putAwayAgain(String bookid){
		boolean flag = false;
		try {
			bd.updateIsShowWithBookId(DBInitValConstant.BOOK_ISSHOW_YES, bookid);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * 编辑图书
	 * @param bookid
	 * @return
	 */
	public boolean edit(Book book){
		boolean flag = false;
		try {
			bd.updateBook(book);
			flag = true;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	/**
	 * 图书下架（更新isShow字段为2）
	 * @param bookid
	 * @return
	 */
	public boolean soldOut(String bookid){
		boolean flag = false;
		try {
			bd.updateIsShowWithBookId(DBInitValConstant.BOOK_ISSHOW_NO, bookid);
			bd.updateSoldTimeWithBookId(TimeUtils.getCurrTime(TimeUtils.FORMATTER_1), bookid);
			flag = true;
		} catch (SQLException e) {
			logger.error("图书下架失败"+e);
			throw new RuntimeException(e);
		}
		
		return flag;
		
	}
	
	/**
	 * 批量修改图书库存
	 * @param shoppingCartItems
	 * @return
	 */
	public boolean batchUpdateStoreNumber(Set<TradeItem> tradeItems){
		try {
			bd.batchUpdateStoreNumber(tradeItems);;
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
	}
	/**
	 * 批量修改图书销量
	 * @param tradeItems
	 * @return
	 */
	public boolean batchUpdateSalesAmount(Set<TradeItem> tradeItems){
		try {
			bd.batchUpdateSalesAmount(tradeItems);
		} catch (SQLException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return true;
		
	}
	
	
	
}

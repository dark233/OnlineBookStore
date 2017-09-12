package com.taunton.service;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.taunton.dao.impl.BackStageUserDAOImpl;
import com.taunton.dao.impl.BookDAOImpl;
import com.taunton.dao.impl.RemarkDAOImpl;
import com.taunton.dao.impl.UserDAOImpl;
import com.taunton.javabean.Remark;
import com.taunton.utils.DomainFactory;
import com.taunton.utils.JDBCUtils;
import com.taunton.web.CriteriaRemark;
import com.taunton.web.Page;

public class RemarkService {
	private static Logger logger = Logger.getLogger(RemarkService.class); 
	private RemarkDAOImpl rd = null;
	private UserDAOImpl ud = null;
	private BookDAOImpl bd = null;
	public RemarkService(){
		rd = DomainFactory.createDomainInstance(RemarkDAOImpl.class);
		ud = DomainFactory.createDomainInstance(UserDAOImpl.class);
		bd = DomainFactory.createDomainInstance(BookDAOImpl.class);
	}
	
	/**
	 * 通过图书id获取该图书的remarkpage
	 * @param cr
	 * @return
	 */
	public Page<Remark> findRemarkPageByBookId(CriteriaRemark cr){
		Page<Remark> remarkpage = null;
		try {
			remarkpage = rd.getPageWithBookId(cr);
			List<Remark> list = remarkpage.getList();
			for (Remark remark : list) {
				remark.setUser(ud.getUserWithUserId(remark.getUserid()));
				remark.setBook(bd.getBook(remark.getBookid()));
			}
		} catch (Exception e) {
			logger.info(e);
			throw new RuntimeException(e);
		}
		return remarkpage;
	}
	
}

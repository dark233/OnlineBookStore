package com.taunton.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.taunton.dao.impl.BookDAOImpl;
import com.taunton.javabean.Book;
import com.taunton.web.CriteriaBook;
import com.taunton.web.Page;

public class BookDAOImplTest {
	BookDAOImpl bd = new BookDAOImpl();
	@Test
	public void testGetBook() {
		System.out.println(bd.getBook(10));
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(0, Integer.MAX_VALUE, 80);
		Page<Book> p = bd.getPage(cb);
		System.out.println(cb.getPageNo());
		System.out.println(p.getPageSize());
		System.out.println(p.getPageNo());
		System.out.println(p.getTotalItemNumber());
		System.out.println(p.getTotalPageNumber());
		List<Book> list = bd.getPageList(cb, p.getPageSize());
		System.out.println(list);
//		p.setList(list);
//		System.out.println(p.getList());
		System.out.println(p.getNextPage());
		System.out.println(p.getPrevPage());
		
	}

	@Test
	public void testGetTotalBookNumber() {
		CriteriaBook cb = new CriteriaBook(0, Integer.MAX_VALUE, 10);
		System.out.println(bd.getTotalBookNumber(cb));
	}

	@Test
	public void testGetPageList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStoreNumber() {
		fail("Not yet implemented");
	}

}

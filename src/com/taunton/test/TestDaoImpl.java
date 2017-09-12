package com.taunton.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.taunton.dao.impl.BaseDAO;
import com.taunton.javabean.User;

public class TestDaoImpl {
	BaseDAO<User> dao = new BaseDAO<User>();
	@Test
	public void testDaoImpl() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		String sql = "insert into user(userid,username) values(?,?)";
		Object[] args = {9,"xiaohong"};
		long id = dao.insert(sql, args);
		System.out.println(id);
	}

	@Test
	public void testUpdate() {
		String sql = "insert into user(username) values(?)";
		Object[] args = {"xiaoming"};
		dao.update(sql, args);
	}

	@Test
	public void testQuery() {
		String sql = "select * from user where userid = ?";
		User u = dao.query(sql, 1);
		System.out.println(u);
//		System.out.println(dao.query(sql, 1));
	}

	@Test
	public void testQueryList() {
		String sql = "select * from user";
		List<User> list = dao.queryList(sql, null);
		System.out.println(list);
	}

	@Test
	public void testQuerySingle() {
		String sql = "select count(*) from user";
		long count = dao.querySingle(sql, null);
		System.out.println(count);
	}

	@Test
	public void testBatch() {
		fail("Not yet implemented");
	}

	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

}

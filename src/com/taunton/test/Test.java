package com.taunton.test;

import com.taunton.utils.TimeUtils;

public class Test {
	@org.junit.Test
	public void test(){
		System.out.println(TimeUtils.wasteTime("2017-4-6 16:00:00",TimeUtils.FORMATTER_1, "2017-4-7 17:00:50",TimeUtils.FORMATTER_1));
		
	}
}
class Test2 {
	public int a = 1;
	
}
class Test3 extends Test2{
	public int a = 2;
}


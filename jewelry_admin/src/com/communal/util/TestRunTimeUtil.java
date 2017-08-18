package com.communal.util;

public class TestRunTimeUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis(); // 获取开始时间
		int count=0;
		for (int i = 0; i < 10; i++) {
			count+=i;
			System.out.println(i+"======"+count);
		}
		long endTime = System.currentTimeMillis(); // 获取结束时间
		System.out.println("方法执行行时间： " + (endTime - startTime) + "ms");
	}

}

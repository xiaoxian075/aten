package com.aten.function;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @function 功能 加载spring的配置文件至内存
 * @author 林俊钦
 * @date 创建日期: Jul 25, 2013 时间:2:50:59 PM
 */
public class SpringContextFuc {
	
	private static ApplicationContext ctx;
	
	static{
		if(ctx ==null){
			//ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			ctx = new ClassPathXmlApplicationContext("classpath:/spring-*.xml");
			System.out.println(ctx+"=====");
		}
	}
	
	public static ApplicationContext getContext(){
		return ctx;
	}
	
	
	
}

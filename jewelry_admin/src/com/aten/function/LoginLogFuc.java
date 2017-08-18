package com.aten.function;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import com.aten.model.orm.LoginLog;
import com.aten.service.LoginLogService;
import com.communal.util.IpUtil;


public class LoginLogFuc extends SpringContextFuc{
	
	private static LoginLogService loginlogService = (LoginLogService) getContext().getBean("loginlogService");

	
	/**
	 * @author linjunqin
	 * @Description app插入日志记录
	 * @param
	 * @date 2017-2-16 下午12:07:43
	 */
	public static void addAppLoginLog(String back_id,String back_name,HttpServletRequest request){
		addLoginLog(back_id,back_name,request,"memb");
	}
	
	/**
	 * @author linjunqin
	 * @Description 后台插入日志
	 * @param
	 * @date 2017-2-16 下午12:06:25
	 */
	public static void addBackLoginLog(String back_id,String back_name,HttpServletRequest request){
		addLoginLog(back_id,back_name,request,"mana");
	}
	
	/**
	 * @author linjunqin
	 * @Description 插入登录日志
	 * @param
	 * @date 2017-2-16 上午11:54:51
	 */
	public static void addLoginLog(String back_id,String back_name,HttpServletRequest request,String type){
		LoginLog  llog = new LoginLog();
		llog.setBack_id(back_id);
		llog.setBack_name(back_name);
		llog.setLogin_ip(IpUtil.getIpAddress(request));
		llog.setBack_type(type);
		loginlogService.insert(llog);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Area areaList  =areaService.get("1111111111");
		areaList.setLevel_area("9999999999,1111111111");
		areaService.update(areaList);*/
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		//getAreaList();
	}
	
}

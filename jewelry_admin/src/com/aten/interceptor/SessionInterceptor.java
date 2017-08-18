package com.aten.interceptor;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.communal.constants.Constant;

/**
 * @author linjunqin
 * @Description 会话拦截器
 * @param
 * @date 2017-2-3 上午10:59:59
 */
public class SessionInterceptor  extends HandlerInterceptorAdapter{

	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);
	
	private List<String> allowUrls;
	
	//请求地址前检验用户是否登录
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//获取请求的地址
		String requestUrl = request.getRequestURI();
		//session对象
		String user_name = (String)request.getSession().getAttribute(Constant.USER_NAME);
		logger.info("session拦截器");
		//允许访问的地址不过滤
		for(String url : allowUrls) {
			if(requestUrl.endsWith(url)) {
				return true;
			}
		}
		//校验用户是否登录
		if(user_name==null){
			logger.info("超时,自动退出登录！");
			response.sendRedirect(Constant.LOGIN_URL); 
		}else{
			return true;
		}
		return false;
		
	}
	
	public List<String> getAllowUrls() {
		return allowUrls;
	}

	public void setAllowUrls(List<String> allowUrls) {
		this.allowUrls = allowUrls;
	}
}

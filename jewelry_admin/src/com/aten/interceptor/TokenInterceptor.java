package com.aten.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.util.TokenProcessor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aten.annotation.TokenAnnotation;
import com.communal.constants.Constant;

/**
 * @author linjunqin
 * @Description 表单重复提交拦截器
 * @param
 * @date 2016-12-29 上午11:09:24
 */
public class TokenInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = Logger.getLogger(TokenInterceptor.class);
	
	/**
	 * @author linjunqin
	 * @Description 在进入方法验证是否重复提交URL
	 * @param
	 * @date 2017-6-9 下午5:46:10
	 */
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	 //获取请求的方法
		 HandlerMethod handlerMethod = (HandlerMethod) handler;
         Method method = handlerMethod.getMethod();
         //反射token注解类
         TokenAnnotation annotation = method.getAnnotation(TokenAnnotation.class);
         if (annotation != null) {
        	 //判断是否需要判断重复提交
             boolean needSaveSession = annotation.needSaveToken();
             if (needSaveSession) {
                 request.getSession(false).setAttribute("token", TokenProcessor.getInstance().generateToken(request));
             }
             //判断是否需要移除重复提交的标识
             boolean needRemoveSession = annotation.needRemoveToken();
             if (needRemoveSession) {
                 if (isRepeatSubmit(request)) {
                	 logger.warn("请不要重复提交！");
                	 response.sendRedirect(Constant.RECOMMIT_URL); 
                 }
                 request.getSession(false).removeAttribute("token");
             }
         }
        return true;
    }
	
	/**
	 * @author linjunqin
	 * @Description 判断是否重复提交表单
	 * @param
	 * @date 2017-6-9 下午5:08:38
	 */
	private boolean isRepeatSubmit(HttpServletRequest request) {
		//获取服务端session中的token
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        //获取客户端传输的token
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        //如果不相等返回真
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }	
	
	
	
	//有机会修改ModelAndView 
	public void postHandle(    
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)    
	
            throws Exception {    
    }    
	
	//可以根据ex是否为null判断是否发生了异常，进行日志记录。 
    public void afterCompletion(    
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {    
    }    
	
}

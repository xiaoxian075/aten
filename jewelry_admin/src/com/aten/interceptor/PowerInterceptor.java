package com.aten.interceptor;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.aten.function.PowerFuc;
import com.communal.constants.Constant;

/**
 * @author linjunqin
 * @Description 权限拦截器
 * @param
 * @date 2017-2-3 上午10:59:59
 */
public class PowerInterceptor  extends HandlerInterceptorAdapter{
	
	private static final Logger logger = Logger.getLogger(PowerInterceptor.class);

	private List<String> allowUrls;
	
	//请求地址前检验用户是否登录
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//获取请求的地址
		String requestUrl = request.getRequestURI();
		//允许访问的地址不进行权限验证
		for(String url : allowUrls) {
			if(requestUrl.endsWith(url)) {
				return true;
			}
		}
		//session对象
		String mana_type = (String)request.getSession().getAttribute(Constant.MANA_TYPE);
		if(mana_type.equals("0")){
			return true;
		}
		String power_right = (String)request.getSession().getAttribute(Constant.POWER_RIGHT);
		//通过请求地址获取权限ID
		String power_id = PowerFuc.getPowerIdByUrl(requestUrl);
		if("0".equals(power_id)){
			return true;
		}
		//校验权限
		if(power_right!=null &&power_right.indexOf(power_id)>-1){
			return true;
		}else{
			logger.info("您没有该操作权限！");
			response.sendRedirect(Constant.NOT_POWER_PAGE); 
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

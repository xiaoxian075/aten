package com.aten.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aten.function.SysconfigFuc;
import com.aten.function.SyslogFuc;
import com.aten.model.bean.PageListBean;
import com.aten.model.bean.SearchHiddenFieldBean;
import com.communal.constants.Constant;
import com.communal.util.StringUtil;


public class BaseController {
	
	protected Boolean isPassValidate=true;
	
	/**
	 * @author linjunqin
	 * @Description 根据规则获取隐藏域中的值
	 * @param
	 * @date 2017-1-5 上午8:53:09
	 */
	@SuppressWarnings("rawtypes")
	protected void initialHiddenVal(HttpServletRequest request,Model model) {
		//String requestUrl =request.getRequestURI();
		Enumeration ParaNames = request.getParameterNames();
		List<SearchHiddenFieldBean> shfbList = new ArrayList<SearchHiddenFieldBean>();
		while (ParaNames.hasMoreElements()) {
			String fieldName = (String) ParaNames.nextElement();
			//带_s搜索参数的处理
			if (!fieldName.endsWith("_s")) {
				continue;
			}
			String fieldValue = "";
			if (request.getParameter(fieldName) != null) {
				fieldValue = request.getParameter(fieldName);
			}
			SearchHiddenFieldBean shfb = new SearchHiddenFieldBean();
			shfb.setFieldName(fieldName);
			shfb.setFieldValue(fieldValue);
			shfbList.add(shfb);
		}
		model.addAttribute("shfbList", shfbList); 
		//oss地址前缀
		model.addAttribute("ossImgServerUrl",SysconfigFuc.getSysValue("oss_imgurl")/*Constants.OSS_IMG_URL*/);
		//无图图片
		model.addAttribute("noPicture", SysconfigFuc.getSysValue("oss_imgurl")+Constant.NO_PICTURE);
		//顶级地区
		model.addAttribute("cfg_top_area", SysconfigFuc.getSysValue("cfg_top_area"));
		//插入操作日志
		SyslogFuc.operLog(request);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description  
	 * @param
	 * @date 2016-12-30 下午5:29:07
	 */
	protected void outPrint(HttpServletResponse response, String data) {
		PrintWriter printWriter = null;
		try {
			System.out.println("JSON格式串==="+data+"===");
	        response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");//设置页面不缓存
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
			printWriter = response.getWriter();
			printWriter.print(data);
		} catch (IOException ex) {
			//
		} finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	/**
	 * @author linjunqin
	 * @Description 将字符串传入后处理转成mapList,排序处理
	 * @param
	 * @date 2017-1-3 下午2:20:29
	 */
	protected List<Map<String, String>> sortStrToMapList(String  ids,String vals){
		// 转成数组
		String[] id = ids.split(",");
		String[] val = vals.split(",");
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < id.length; i++) {
			if (!id[i].equals("") && !val[i].equals("")) {
				HashMap<String, String> hMap = new HashMap<String, String>();
				hMap.put("id", id[i]);
				hMap.put("sort_no", val[i]);
				mapList.add(hMap);
			}
		}
		return mapList;
		
	}
	
	/**
	 * @author linjunqin
	 * @Description 分页插件
	 * @param
	 * @date 2017-1-5 上午9:49:25
	 */
	public HashMap<String,Object> pageTool(HttpServletRequest request,int count, Model model ,HashMap<String,Object> pageMap) {
		PageListBean page = new PageListBean();
		//当前页
		String current_s = request.getParameter(Constant.CURRENT);
		if(current_s==null ||current_s.equals("")) current_s="1";//默认第一页
		//显示页条数
		String pagesize_s = request.getParameter(Constant.PAGESIZE);
		if(pagesize_s==null ||pagesize_s.equals("")) pagesize_s="10";//默认10条数据
		//设置分页参数
		page.setCurrent_s(Integer.parseInt(current_s));
		page.setPagesize_s(Integer.parseInt(pagesize_s));
		page.setTotalCount(count);	
		//设置分页插件
		model.addAttribute("page", page);
		pageMap.put("start", page.getPageStart()-1);//分页显示从第几条开始记录
		pageMap.put("top", page.getPageTop());//显示条数
		pageMap.put("limit", pagesize_s);
		return pageMap;
	}
	
	/**
	 * @author linjunqin
	 * @Description 对符合条件的数据加入搜索
	 * @param
	 * @date 2017-1-12 下午2:06:01
	 */
	protected HashMap<String, Object> searchMap(HttpServletRequest request,HashMap<String,Object> paraMap,Model model){
		Enumeration<?> ParaNames = request.getParameterNames();
		while (ParaNames.hasMoreElements()) {
			String fieldName = (String) ParaNames.nextElement();
			//移除不符合条件的搜索
			if (!fieldName.endsWith("_s") || fieldName.equals(Constant.CURRENT)
					||fieldName.equals(Constant.PAGESIZE) ) {
				continue;
			}
			String fieldValue = request.getParameter(fieldName);
			//不为空时加入条件
			if (!StringUtil.isEmpty(fieldValue)){
				model.addAttribute(fieldName, fieldValue);//符合条件的重新赋值
				//fieldName 去掉_s加入搜索名称
				fieldName = fieldName.substring(0, fieldName.length()-2);
				paraMap.put(fieldName, fieldValue);
			}
		}
		return paraMap;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 获取request对象
	 * @param
	 * @date 2017-1-11 下午2:45:38
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取session对象
	 * @param
	 * @date 2017-2-3 上午10:37:14
	 */
	public HttpSession getSession(){
		HttpSession session = this.getRequest().getSession();
		return session;
	}
	
	public String getSessionValue(HttpServletRequest request,String sessionCode){
		String sessionValue = (String) this.getRequest().getSession().getAttribute(sessionCode);
		return sessionValue;
	}
	
	/**
	 * @author linjunqin
	 * @Description 重定向
	 * @param
	 * @date 2017-1-13 上午11:05:17
	 */
	protected String goUrl(String url) {
		return "redirect:/admin/"+url;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
	}
	
	
}

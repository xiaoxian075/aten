/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-17 09:06:18  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: DataCountController.java 
 * Author:linjunqin
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.communal.util.StringUtil;
import com.aten.model.orm.DataCount;
import com.aten.service.DataCountService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author linjunqin
 * @Function 平台数据统计功能  controller
 * @date 2017-08-17 09:06:18
 */
@Controller
@RequestMapping("admin/dataCount")
public class DataCountController extends BaseController{

	private static final Logger logger = Logger.getLogger(DataCountController.class);
	
	@Autowired
	private DataCountService dataCountService;
	
	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-08-17 09:06:18
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author linjunqin
	 * @Description 平台数据统计列表页方法
	 * @param
	 * @date 2017-08-17 09:06:18
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.dataCountService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<DataCount> dataCountList = this.dataCountService.getList(paraMap);
		model.addAttribute("dataCountList", dataCountList);
		return "dataCount/list";
	}
	
	
	
	
	 /**
	 * @author linjunqin
	 * @Description 跳转到添加平台数据统计页面方法
	 * @param
	 * @date 2017-08-17 09:06:18
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "dataCount/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加平台数据统计方法
	 * @param
	 * @date 2017-08-17 09:06:18
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(DataCount dataCount,Model model){
		this.dataCountService.insert(dataCount);
		model.addAttribute("promptmsg","平台数据统计添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author linjunqin
	 * @Description 跳转到修改平台数据统计页面方法
	 * @param
	 * @date 2017-08-17 09:06:18
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		DataCount dataCount = this.dataCountService.get(parameter_id);
		model.addAttribute("dataCount", dataCount);
		return "dataCount/update";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 修改平台数据统计方法
	 * @param
	 * @date 2017-08-17 09:06:18
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,DataCount dataCount,Model model){
		this.dataCountService.update(dataCount);
		model.addAttribute("promptmsg","平台数据统计修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 删除平台数据统计方法
	 * @param
	 * @date 2017-08-17 09:06:18
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.dataCountService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","平台数据统计删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 平台数据统计排序方法
	 * @param
	 * @date 2017-08-17 09:06:18
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.dataCountService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
		

	
	
}


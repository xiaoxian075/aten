/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-17 09:09:06  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: GoodsCatCountController.java 
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
import com.aten.model.orm.GoodsCatCount;
import com.aten.service.GoodsCatCountService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author linjunqin
 * @Function 商品分类统计功能  controller
 * @date 2017-08-17 09:09:06
 */
@Controller
@RequestMapping("admin/goodsCatCount")
public class GoodsCatCountController extends BaseController{

	private static final Logger logger = Logger.getLogger(GoodsCatCountController.class);
	
	@Autowired
	private GoodsCatCountService goodsCatCountService;
	
	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-08-17 09:09:06
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author linjunqin
	 * @Description 商品分类统计列表页方法
	 * @param
	 * @date 2017-08-17 09:09:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.goodsCatCountService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<GoodsCatCount> goodsCatCountList = this.goodsCatCountService.getList(paraMap);
		model.addAttribute("goodsCatCountList", goodsCatCountList);
		return "goodsCatCount/list";
	}
	
	
	
	
	 /**
	 * @author linjunqin
	 * @Description 跳转到添加商品分类统计页面方法
	 * @param
	 * @date 2017-08-17 09:09:06
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "goodsCatCount/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加商品分类统计方法
	 * @param
	 * @date 2017-08-17 09:09:06
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(GoodsCatCount goodsCatCount,Model model){
		this.goodsCatCountService.insert(goodsCatCount);
		model.addAttribute("promptmsg","商品分类统计添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author linjunqin
	 * @Description 跳转到修改商品分类统计页面方法
	 * @param
	 * @date 2017-08-17 09:09:06
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		GoodsCatCount goodsCatCount = this.goodsCatCountService.get(parameter_id);
		model.addAttribute("goodsCatCount", goodsCatCount);
		return "goodsCatCount/update";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 修改商品分类统计方法
	 * @param
	 * @date 2017-08-17 09:09:06
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,GoodsCatCount goodsCatCount,Model model){
		this.goodsCatCountService.update(goodsCatCount);
		model.addAttribute("promptmsg","商品分类统计修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 删除商品分类统计方法
	 * @param
	 * @date 2017-08-17 09:09:06
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.goodsCatCountService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","商品分类统计删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 商品分类统计排序方法
	 * @param
	 * @date 2017-08-17 09:09:06
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.goodsCatCountService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
}


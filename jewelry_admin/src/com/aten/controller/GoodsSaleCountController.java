/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-17 09:17:20  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: GoodsSaleCountController.java 
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
import com.aten.model.orm.GoodsSaleCount;
import com.aten.service.GoodsSaleCountService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author linjunqin
 * @Function 商品销售统计功能  controller
 * @date 2017-08-17 09:17:20
 */
@Controller
@RequestMapping("admin/goodsSaleCount")
public class GoodsSaleCountController extends BaseController{

	private static final Logger logger = Logger.getLogger(GoodsSaleCountController.class);
	
	@Autowired
	private GoodsSaleCountService goodsSaleCountService;
	
	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-08-17 09:17:20
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author linjunqin
	 * @Description 商品销售统计列表页方法
	 * @param
	 * @date 2017-08-17 09:17:20
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.goodsSaleCountService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<GoodsSaleCount> goodsSaleCountList = this.goodsSaleCountService.getList(paraMap);
		model.addAttribute("goodsSaleCountList", goodsSaleCountList);
		return "goodsSaleCount/list";
	}
	
	
	
	
	 /**
	 * @author linjunqin
	 * @Description 跳转到添加商品销售统计页面方法
	 * @param
	 * @date 2017-08-17 09:17:20
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "goodsSaleCount/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加商品销售统计方法
	 * @param
	 * @date 2017-08-17 09:17:20
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(GoodsSaleCount goodsSaleCount,Model model){
		this.goodsSaleCountService.insert(goodsSaleCount);
		model.addAttribute("promptmsg","商品销售统计添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author linjunqin
	 * @Description 跳转到修改商品销售统计页面方法
	 * @param
	 * @date 2017-08-17 09:17:20
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		GoodsSaleCount goodsSaleCount = this.goodsSaleCountService.get(parameter_id);
		model.addAttribute("goodsSaleCount", goodsSaleCount);
		return "goodsSaleCount/update";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 修改商品销售统计方法
	 * @param
	 * @date 2017-08-17 09:17:20
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,GoodsSaleCount goodsSaleCount,Model model){
		this.goodsSaleCountService.update(goodsSaleCount);
		model.addAttribute("promptmsg","商品销售统计修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 删除商品销售统计方法
	 * @param
	 * @date 2017-08-17 09:17:20
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.goodsSaleCountService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","商品销售统计删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 商品销售统计排序方法
	 * @param
	 * @date 2017-08-17 09:17:20
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.goodsSaleCountService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
}


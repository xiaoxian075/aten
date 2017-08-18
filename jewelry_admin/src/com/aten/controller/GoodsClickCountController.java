/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-17 09:12:22  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: GoodsClickCountController.java 
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
import com.aten.model.orm.GoodsClickCount;
import com.aten.service.GoodsClickCountService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author linjunqin
 * @Function 商品点击统计功能  controller
 * @date 2017-08-17 09:12:22
 */
@Controller
@RequestMapping("admin/goodsClickCount")
public class GoodsClickCountController extends BaseController{

	private static final Logger logger = Logger.getLogger(GoodsClickCountController.class);
	
	@Autowired
	private GoodsClickCountService goodsClickCountService;
	
	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-08-17 09:12:22
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author linjunqin
	 * @Description 商品点击统计列表页方法
	 * @param
	 * @date 2017-08-17 09:12:22
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.goodsClickCountService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<GoodsClickCount> goodsClickCountList = this.goodsClickCountService.getList(paraMap);
		model.addAttribute("goodsClickCountList", goodsClickCountList);
		return "goodsClickCount/list";
	}
	
	
	
	
	 /**
	 * @author linjunqin
	 * @Description 跳转到添加商品点击统计页面方法
	 * @param
	 * @date 2017-08-17 09:12:22
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "goodsClickCount/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加商品点击统计方法
	 * @param
	 * @date 2017-08-17 09:12:22
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(GoodsClickCount goodsClickCount,Model model){
		this.goodsClickCountService.insert(goodsClickCount);
		model.addAttribute("promptmsg","商品点击统计添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author linjunqin
	 * @Description 跳转到修改商品点击统计页面方法
	 * @param
	 * @date 2017-08-17 09:12:22
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		GoodsClickCount goodsClickCount = this.goodsClickCountService.get(parameter_id);
		model.addAttribute("goodsClickCount", goodsClickCount);
		return "goodsClickCount/update";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 修改商品点击统计方法
	 * @param
	 * @date 2017-08-17 09:12:22
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,GoodsClickCount goodsClickCount,Model model){
		this.goodsClickCountService.update(goodsClickCount);
		model.addAttribute("promptmsg","商品点击统计修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 删除商品点击统计方法
	 * @param
	 * @date 2017-08-17 09:12:22
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.goodsClickCountService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","商品点击统计删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 商品点击统计排序方法
	 * @param
	 * @date 2017-08-17 09:12:22
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.goodsClickCountService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
}


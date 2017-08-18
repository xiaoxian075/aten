/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-17 09:49:13  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: GoodsActivityMapController.java 
 * Author:chenyi
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
import com.aten.model.orm.GoodsActivityMap;
import com.aten.service.GoodsActivityMapService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 活动与商品关联表  controller
 * @date 2017-07-17 09:49:13
 */
@Controller
@RequestMapping("admin/goodsactivitymap")
public class GoodsActivityMapController extends BaseController{

	private static final Logger logger = Logger.getLogger(GoodsActivityMapController.class);
	
	@Autowired
	private GoodsActivityMapService goodsActivityMapService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 活动与商品关联表列表页方法
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.goodsActivityMapService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<GoodsActivityMap> goodsActivityMapList = this.goodsActivityMapService.getList(paraMap);
		model.addAttribute("goodsActivityMapList", goodsActivityMapList);
		return "goodsActivityMap/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加活动与商品关联表页面方法
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "goodsActivityMap/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加活动与商品关联表方法
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(GoodsActivityMap goodsActivityMap,Model model){
		this.goodsActivityMapService.insert(goodsActivityMap);
		model.addAttribute("promptmsg","活动与商品关联表添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改活动与商品关联表页面方法
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		GoodsActivityMap goodsActivityMap = this.goodsActivityMapService.get(parameter_id);
		model.addAttribute("goodsActivityMap", goodsActivityMap);
		return "goodsActivityMap/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改活动与商品关联表方法
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,GoodsActivityMap goodsActivityMap,Model model){
		this.goodsActivityMapService.update(goodsActivityMap);
		model.addAttribute("promptmsg","活动与商品关联表修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除活动与商品关联表方法
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.goodsActivityMapService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","活动与商品关联表删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 活动与商品关联表排序方法
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.goodsActivityMapService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 活动与商品关联表批量删除成功
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		boolean flag=false;
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			if(!StringUtil.isEmpty(id)){
				if(checkBrand(id)){
					flag = true;
				}else{
					this.goodsActivityMapService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分活动与商品关联表已被引用,被引用的活动与商品关联表删除失败！");
		}else{
			model.addAttribute("promptmsg","活动与商品关联表删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用活动与商品关联表
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		GoodsActivityMap goodsActivityMap  = this.goodsActivityMapService.get(parameter_id);
		//goodsActivityMap.setState("1");
		this.goodsActivityMapService.update(goodsActivityMap);
		model.addAttribute("promptmsg","活动与商品关联表启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用活动与商品关联表
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		GoodsActivityMap goodsActivityMap  = this.goodsActivityMapService.get(parameter_id);
		//goodsActivityMap.setState("0");
		this.goodsActivityMapService.update(goodsActivityMap);
		model.addAttribute("promptmsg","活动与商品关联表禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用活动与商品关联表
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			GoodsActivityMap goodsActivityMap  = this.goodsActivityMapService.get(id);
			//goodsActivityMap.setState("1");
			this.goodsActivityMapService.update(goodsActivityMap);
		}
		model.addAttribute("promptmsg","活动与商品关联表批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用活动与商品关联表
	 * @param
	 * @date 2017-07-17 09:49:13
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			GoodsActivityMap goodsActivityMap  = this.goodsActivityMapService.get(id);
			//goodsActivityMap.setState("0");
			this.goodsActivityMapService.update(goodsActivityMap);
		}
		model.addAttribute("promptmsg","活动与商品关联表批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除活动与商品关联表时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String gam_id){
		return false;
	}
	
	
	
}


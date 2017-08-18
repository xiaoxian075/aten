/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-10 10:11:58  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: PreGoodscatController.java 
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
import com.aten.model.orm.PreGoodscat;
import com.aten.service.PreGoodscatService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 前台分类与商品分类映射表  controller
 * @date 2017-07-10 10:11:58
 */
@Controller
@RequestMapping("admin/pregoodscat")
public class PreGoodscatController extends BaseController{

	private static final Logger logger = Logger.getLogger(PreGoodscatController.class);
	
	@Autowired
	private PreGoodscatService preGoodscatService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 前台分类与商品分类映射表列表页方法
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.preGoodscatService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<PreGoodscat> preGoodscatList = this.preGoodscatService.getList(paraMap);
		model.addAttribute("preGoodscatList", preGoodscatList);
		return "preGoodscat/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加前台分类与商品分类映射表页面方法
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "preGoodscat/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加前台分类与商品分类映射表方法
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(PreGoodscat preGoodscat,Model model){
		this.preGoodscatService.insert(preGoodscat);
		model.addAttribute("promptmsg","前台分类与商品分类映射表添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改前台分类与商品分类映射表页面方法
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		PreGoodscat preGoodscat = this.preGoodscatService.get(parameter_id);
		model.addAttribute("preGoodscat", preGoodscat);
		return "preGoodscat/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改前台分类与商品分类映射表方法
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,PreGoodscat preGoodscat,Model model){
		this.preGoodscatService.update(preGoodscat);
		model.addAttribute("promptmsg","前台分类与商品分类映射表修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除前台分类与商品分类映射表方法
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.preGoodscatService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","前台分类与商品分类映射表删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 前台分类与商品分类映射表排序方法
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.preGoodscatService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 前台分类与商品分类映射表批量删除成功
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
					this.preGoodscatService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分前台分类与商品分类映射表已被引用,被引用的前台分类与商品分类映射表删除失败！");
		}else{
			model.addAttribute("promptmsg","前台分类与商品分类映射表删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用前台分类与商品分类映射表
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		PreGoodscat preGoodscat  = this.preGoodscatService.get(parameter_id);
		//preGoodscat.setState("1");
		this.preGoodscatService.update(preGoodscat);
		model.addAttribute("promptmsg","前台分类与商品分类映射表启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用前台分类与商品分类映射表
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		PreGoodscat preGoodscat  = this.preGoodscatService.get(parameter_id);
		//preGoodscat.setState("0");
		this.preGoodscatService.update(preGoodscat);
		model.addAttribute("promptmsg","前台分类与商品分类映射表禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用前台分类与商品分类映射表
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			PreGoodscat preGoodscat  = this.preGoodscatService.get(id);
			//preGoodscat.setState("1");
			this.preGoodscatService.update(preGoodscat);
		}
		model.addAttribute("promptmsg","前台分类与商品分类映射表批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用前台分类与商品分类映射表
	 * @param
	 * @date 2017-07-10 10:11:58
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			PreGoodscat preGoodscat  = this.preGoodscatService.get(id);
		//	preGoodscat.setState("0");
			this.preGoodscatService.update(preGoodscat);
		}
		model.addAttribute("promptmsg","前台分类与商品分类映射表批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除前台分类与商品分类映射表时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String pg_id){
		return false;
	}
	
	
	
}


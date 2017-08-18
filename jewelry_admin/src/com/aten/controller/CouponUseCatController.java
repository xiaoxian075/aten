/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-11 17:17:18  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: CouponUseCatController.java 
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
import com.aten.model.orm.CouponUseCat;
import com.aten.service.CouponUseCatService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 优惠券使用区域  controller
 * @date 2017-07-11 17:17:18
 */
@Controller
@RequestMapping("admin/couponusecat")
public class CouponUseCatController extends BaseController{

	private static final Logger logger = Logger.getLogger(CouponUseCatController.class);
	
	@Autowired
	private CouponUseCatService couponUseCatService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 优惠券使用区域列表页方法
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.couponUseCatService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<CouponUseCat> couponUseCatList = this.couponUseCatService.getList(paraMap);
		model.addAttribute("couponUseCatList", couponUseCatList);
		return "couponUseCat/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加优惠券使用区域页面方法
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "couponUseCat/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加优惠券使用区域方法
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(CouponUseCat couponUseCat,Model model){
		this.couponUseCatService.insert(couponUseCat);
		model.addAttribute("promptmsg","优惠券使用区域添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改优惠券使用区域页面方法
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		CouponUseCat couponUseCat = this.couponUseCatService.get(parameter_id);
		model.addAttribute("couponUseCat", couponUseCat);
		return "couponUseCat/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改优惠券使用区域方法
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,CouponUseCat couponUseCat,Model model){
		this.couponUseCatService.update(couponUseCat);
		model.addAttribute("promptmsg","优惠券使用区域修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除优惠券使用区域方法
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.couponUseCatService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","优惠券使用区域删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 优惠券使用区域排序方法
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.couponUseCatService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 优惠券使用区域批量删除成功
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
					this.couponUseCatService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分优惠券使用区域已被引用,被引用的优惠券使用区域删除失败！");
		}else{
			model.addAttribute("promptmsg","优惠券使用区域删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用优惠券使用区域
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		CouponUseCat couponUseCat  = this.couponUseCatService.get(parameter_id);
		//couponUseCat.setState("1");
		this.couponUseCatService.update(couponUseCat);
		model.addAttribute("promptmsg","优惠券使用区域启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用优惠券使用区域
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		CouponUseCat couponUseCat  = this.couponUseCatService.get(parameter_id);
		//couponUseCat.setState("0");
		this.couponUseCatService.update(couponUseCat);
		model.addAttribute("promptmsg","优惠券使用区域禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用优惠券使用区域
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			CouponUseCat couponUseCat  = this.couponUseCatService.get(id);
			//couponUseCat.setState("1");
			this.couponUseCatService.update(couponUseCat);
		}
		model.addAttribute("promptmsg","优惠券使用区域批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用优惠券使用区域
	 * @param
	 * @date 2017-07-11 17:17:18
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			CouponUseCat couponUseCat  = this.couponUseCatService.get(id);
			//couponUseCat.setState("0");
			this.couponUseCatService.update(couponUseCat);
		}
		model.addAttribute("promptmsg","优惠券使用区域批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除优惠券使用区域时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String id){
		return false;
	}
	
	
	
}


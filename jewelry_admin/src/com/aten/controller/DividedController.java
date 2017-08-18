/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-28 15:17:44  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: DividedController.java 
 * Author:chenyi
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;

import com.communal.util.AmountUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.communal.util.StringUtil;
import com.aten.model.orm.Divided;
import com.aten.service.DividedService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 分成列表  controller
 * @date 2017-07-28 15:17:44
 */
@Controller
@RequestMapping("admin/divided")
public class DividedController extends BaseController{

	private static final Logger logger = Logger.getLogger(DividedController.class);
	
	@Autowired
	private DividedService dividedService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-28 15:17:44
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 分成列表列表页方法
	 * @param
	 * @date 2017-07-28 15:17:44
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.dividedService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Divided> dividedList = this.dividedService.getList(paraMap);
		for (Divided divided:dividedList){
			divided.setDivided_amount(AmountUtil.formatMoney(divided.getDivided_amount()));
			divided.setAccount_level("1".equals(divided.getAccount_level())?"普通会员":"vip会员");
		}
		model.addAttribute("dividedList", dividedList);
		return "divided/list";
	}
	

	/**
	 * @author chenyi
	 * @Description 跳转到修改分成列表页面方法
	 * @param
	 * @date 2017-07-28 15:17:44
	 */
	@RequestMapping("detail")
	@TokenAnnotation(needSaveToken = true)
	public String detail(HttpServletRequest request,Model model){
		String login_name = request.getParameter("parameter_id");
		if(login_name==null ||login_name.equals("")) return detail(request,model);
		//获取对象
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("login_name",login_name);
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.dividedService.getDetailCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Divided> dividedList = this.dividedService.findByAccountId(paraMap);
		for (Divided divided:dividedList){
			divided.setOrder_amount(AmountUtil.formatMoney(divided.getOrder_amount()));
			divided.setDivided_amount(AmountUtil.formatMoney(divided.getDivided_amount()));
			divided.setAccount_level("1".equals(divided.getAccount_level())?"普通会员":"vip会员");
		}
		model.addAttribute("dividedList",dividedList);
		model.addAttribute("parameter_id",login_name);
		return "divided/detail";
	}



	
}


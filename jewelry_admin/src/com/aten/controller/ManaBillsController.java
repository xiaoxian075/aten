/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-28 18:34:06  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: ManaBillsController.java 
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
import com.aten.model.orm.ManaBills;
import com.aten.service.ManaBillsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 记录运营商资金记录管理  controller
 * @date 2017-07-28 18:34:06
 */
@Controller
@RequestMapping("admin/manabills")
public class ManaBillsController extends BaseController{

	private static final Logger logger = Logger.getLogger(ManaBillsController.class);
	
	@Autowired
	private ManaBillsService manaBillsService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 账单列表列表页方法
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.manaBillsService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<ManaBills> manaBillsList = this.manaBillsService.getList(paraMap);
		for(ManaBills manaBills:manaBillsList){
			manaBills.setBill_amount(AmountUtil.formatMoney(manaBills.getBill_amount()));
			manaBills.setTrade_amount(AmountUtil.formatMoney(manaBills.getTrade_amount()));
			manaBills.setDivided_amount(AmountUtil.formatMoney(manaBills.getDivided_amount()));
			String date=manaBills.getSettle_date();
			manaBills.setSettle_date(date.substring(0,10));
		}
		model.addAttribute("manaBillsList", manaBillsList);
		return "manaBills/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加账单列表页面方法
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "manaBills/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加账单列表方法
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(ManaBills manaBills,Model model){
		this.manaBillsService.insert(manaBills);
		model.addAttribute("promptmsg","账单列表添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改账单列表页面方法
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		ManaBills manaBills = this.manaBillsService.get(parameter_id);
		model.addAttribute("manaBills", manaBills);
		return "manaBills/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改账单列表方法
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,ManaBills manaBills,Model model){
		this.manaBillsService.update(manaBills);
		model.addAttribute("promptmsg","账单列表修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除账单列表方法
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.manaBillsService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","账单列表删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 账单列表排序方法
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.manaBillsService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 账单列表批量删除成功
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
					this.manaBillsService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分账单列表已被引用,被引用的账单列表删除失败！");
		}else{
			model.addAttribute("promptmsg","账单列表删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用账单列表
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		ManaBills manaBills  = this.manaBillsService.get(parameter_id);
		//manaBills.setState("1");
		this.manaBillsService.update(manaBills);
		model.addAttribute("promptmsg","账单列表启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用账单列表
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		ManaBills manaBills  = this.manaBillsService.get(parameter_id);
		//manaBills.setState("0");
		this.manaBillsService.update(manaBills);
		model.addAttribute("promptmsg","账单列表禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用账单列表
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			ManaBills manaBills  = this.manaBillsService.get(id);
			//manaBills.setState("1");
			this.manaBillsService.update(manaBills);
		}
		model.addAttribute("promptmsg","账单列表批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用账单列表
	 * @param
	 * @date 2017-07-28 18:34:06
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			ManaBills manaBills  = this.manaBillsService.get(id);
			//manaBills.setState("0");
			this.manaBillsService.update(manaBills);
		}
		model.addAttribute("promptmsg","账单列表批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除账单列表时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String mb_id){
		return false;
	}
	
	
}


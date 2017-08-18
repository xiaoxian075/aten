/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-03 20:23:02  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: FastmailController.java 
 * Author:chenjx
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.aten.annotation.TokenAnnotation;
import com.communal.node.ReqMsg;
import com.communal.util.JsonUtil;
import com.communal.util.StringUtil;
import com.aten.model.orm.Fastmail;
import com.aten.service.FastmailService;
import com.aten.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenjx
 * @Function 物流承运商功能管理  controller
 * @date 2017-07-03 20:23:02
 */
@Controller
@RequestMapping("admin/fastmail")
public class FastmailController extends BaseController{

	//private static final Logger logger = Logger.getLogger(FastmailController.class);
	
	@Autowired
	private FastmailService fastmailService;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * @author chenjx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }
	
	@RequestMapping("getall")
	@ResponseBody
	public String getall(){
		List<Fastmail> fastmailList = this.fastmailService.getAllList();
		if (fastmailList==null) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"异常",null));
		}
		return JsonUtil.jsonToStr(new ReqMsg<Object>(0,"succ",fastmailList));
	}
	
	
	/**
	 * @author chenjx
	 * @Description 物流承运商列表页方法
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		String fast_name = (String)paraMap.get("fast_name");
		if (fast_name!=null) {
			fast_name = "%"+fast_name+"%";
			paraMap.put("fast_name", fast_name);
		}
		//int count = this.fastmailService.getCount(paraMap);
		int count = this.fastmailService.getcountByPage(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		//List<Fastmail> fastmailList = this.fastmailService.getList(paraMap);
		List<Fastmail> fastmailList = this.fastmailService.getlistByPage(paraMap);
		model.addAttribute("fastmailList", fastmailList);
		return "fastmail/list";
	}
	
	
	
	
	 /**
	 * @author chenjx
	 * @Description 跳转到添加物流承运商页面方法
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "fastmail/insert";
	}
	
	
	/**
	 * @author chenjx
	 * @Description 添加物流承运商方法
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Fastmail fastmail,Model model,RedirectAttributesModelMap modelMap){
		if (fastmail.getIs_insured()==null) {
			fastmail.setIs_insured("0");
		}
		if (fastmail.getFast_desc()==null) {
			fastmail.setFast_desc("");
		}
		if (fastmail.getRate()==null) {
			fastmail.setRate("0");
		}
		if (fastmail.getMix_insured()==null) {
			fastmail.setMix_insured("0");
		}
		if (fastmail.getMax_insured()==null) {
			fastmail.setMax_insured("0");
		}
		if (fastmail.getIs_reach_pay()==null) {
			fastmail.setIs_reach_pay("0");
		}
		if (fastmail.getDefault_temp()==null) {
			fastmail.setDefault_temp("0");
		}
		if (fastmail.getWaybill_rule()==null) {
			fastmail.setWaybill_rule("0");
		}
		this.fastmailService.insert(fastmail);
		//model.addAttribute("promptmsg","物流承运商添加成功！");
		modelMap.addFlashAttribute("promptmsg","物流承运商添加成功！");
		//return add(model);
		return goUrl("fastmail/add");
	}
	
	
		
	/**
	 * @author chenjx
	 * @Description 跳转到修改物流承运商页面方法
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Fastmail fastmail = this.fastmailService.get(parameter_id);
		model.addAttribute("fastmail", fastmail);
		return "fastmail/update";
	}
	
	
	/**
	 * @author chenjx
	 * @Description 修改物流承运商方法
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,Fastmail fastmail,Model model){
		this.fastmailService.update(fastmail);
		model.addAttribute("promptmsg","物流承运商修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenjx
	 * @Description 删除物流承运商方法
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) {
			return list(request,model);
		}
		Fastmail fastmail = this.fastmailService.get(parameter_id);
		if (fastmail==null) {
			return list(request,model);
		}
		if(checkBrand(fastmail.getFast_code())){
			model.addAttribute("promptmsg","物流承运商已被引用,删除失败！");
		} else {
			this.fastmailService.deleteOne(parameter_id);
			model.addAttribute("promptmsg","物流承运商删除成功！");
		}
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenjx
	 * @Description 物流承运商排序方法
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.fastmailService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenjx
	 * @Description 物流承运商批量删除成功
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
				Fastmail fastmail = this.fastmailService.get(id);
				if (fastmail!=null) {
					if(checkBrand(fastmail.getFast_code())){
						flag = true;
					}else{
						this.fastmailService.deleteOne(id);
					}
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分物流承运商已被引用,被引用的物流承运商删除失败！");
		}else{
			model.addAttribute("promptmsg","物流承运商删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenjx
	 * @Description 启用物流承运商
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Fastmail fastmail  = this.fastmailService.get(parameter_id);
		fastmail.setState("1");
		this.fastmailService.update(fastmail);
		model.addAttribute("promptmsg","物流承运商启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenjx
	 * @Description 禁用物流承运商
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Fastmail fastmail  = this.fastmailService.get(parameter_id);
		fastmail.setState("0");
		this.fastmailService.update(fastmail);
		model.addAttribute("promptmsg","物流承运商禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenjx
	 * @Description 批量启用物流承运商
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Fastmail fastmail  = this.fastmailService.get(id);
			fastmail.setState("1");
			this.fastmailService.update(fastmail);
		}
		model.addAttribute("promptmsg","物流承运商批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenjx
	 * @Description 批量禁用物流承运商
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Fastmail fastmail  = this.fastmailService.get(id);
			fastmail.setState("0");
			this.fastmailService.update(fastmail);
		}
		model.addAttribute("promptmsg","物流承运商批量禁用成功！");
		return list(request, model);
	}
	
	/**
	 * @author chenjx
	 * @Description 验证code是否重复
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("checkcode")
	@ResponseBody
	public String checkcode(HttpServletRequest request,Model model){	
		String fast_code = request.getParameter("fast_code");
		if (fast_code==null || fast_code.length()==0) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
		}
		
		Fastmail fastmain = this.fastmailService.selectoneByFastcode(fast_code);
		if (fastmain==null) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(0,"succ",null));
		} else {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(2,"简称不能重复，请重新输入",null));
		}
	}
	
	/**
	 * @author chenjx
	 * @Description 验证用户名是否重复
	 * @param
	 * @date 2017-07-03 20:23:02
	 */
	@RequestMapping("checkname")
	@ResponseBody
	public String checkname(HttpServletRequest request,Model model){	
		String fast_name = request.getParameter("fast_name");
		if (fast_name==null || fast_name.length()==0) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
		}
		
		Fastmail fastmain = this.fastmailService.selectoneByFastname(fast_name);
		if (fastmain==null) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(0,"succ",null));
		} else {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(2,"名称不能重复，请重新输入",null));
		}
	}
	
	
	/**
	 * @author chenjx
	 * @Description 验证删除物流承运商时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String fast_id){
		if (orderService.getcountOrderExpressByFastcode(fast_id)>0)
			return true;
		return false;
	}
	
	

	
}


/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-13 11:06:45  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: AccountCouponController.java 
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
import com.aten.model.orm.AccountCoupon;
import com.aten.service.AccountCouponService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 测试类  controller
 * @date 2017-07-13 11:06:45
 */
@Controller
@RequestMapping("admin/accountcoupon")
public class AccountCouponController extends BaseController{

	private static final Logger logger = Logger.getLogger(AccountCouponController.class);
	
	@Autowired
	private AccountCouponService accountCouponService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 测试列表页方法
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.accountCouponService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<AccountCoupon> accountCouponList = this.accountCouponService.getList(paraMap);
		model.addAttribute("accountCouponList", accountCouponList);
		return "accountCoupon/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加测试页面方法
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "accountCoupon/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加测试方法
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(AccountCoupon accountCoupon,Model model){
		this.accountCouponService.insert(accountCoupon);
		model.addAttribute("promptmsg","测试添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改测试页面方法
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		AccountCoupon accountCoupon = this.accountCouponService.get(parameter_id);
		model.addAttribute("accountCoupon", accountCoupon);
		return "accountCoupon/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改测试方法
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,AccountCoupon accountCoupon,Model model){
		this.accountCouponService.update(accountCoupon);
		model.addAttribute("promptmsg","测试修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除测试方法
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.accountCouponService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","测试删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 测试排序方法
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.accountCouponService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 测试批量删除成功
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
					this.accountCouponService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分测试已被引用,被引用的测试删除失败！");
		}else{
			model.addAttribute("promptmsg","测试删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用测试
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		AccountCoupon accountCoupon  = this.accountCouponService.get(parameter_id);
		accountCoupon.setState("1");
		this.accountCouponService.update(accountCoupon);
		model.addAttribute("promptmsg","测试启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用测试
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		AccountCoupon accountCoupon  = this.accountCouponService.get(parameter_id);
		accountCoupon.setState("0");
		this.accountCouponService.update(accountCoupon);
		model.addAttribute("promptmsg","测试禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用测试
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			AccountCoupon accountCoupon  = this.accountCouponService.get(id);
			accountCoupon.setState("1");
			this.accountCouponService.update(accountCoupon);
		}
		model.addAttribute("promptmsg","测试批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用测试
	 * @param
	 * @date 2017-07-13 11:06:45
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			AccountCoupon accountCoupon  = this.accountCouponService.get(id);
			accountCoupon.setState("0");
			this.accountCouponService.update(accountCoupon);
		}
		model.addAttribute("promptmsg","测试批量禁用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除测试时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String id){
		return false;
	}
	
}


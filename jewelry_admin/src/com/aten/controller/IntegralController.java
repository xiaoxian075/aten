/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-19 19:56:18  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: IntegralController.java 
 * Author:hx
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
import com.aten.function.AreaFuc;
import com.communal.util.StringUtil;
import com.aten.model.orm.Integral;
import com.aten.model.orm.Supply;
import com.aten.service.IntegralService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hx
 * @Function 积分商品类  controller
 * @date 2017-07-19 19:56:18
 */
@Controller
@RequestMapping("admin/integral")
public class IntegralController extends BaseController{

	private static final Logger logger = Logger.getLogger(IntegralController.class);
	
	@Autowired
	private IntegralService integralService;
	
	/**
	 * @author hx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author hx
	 * @Description 积分商品列表页方法
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		paraMap.put("is_del", "0");
		int count = this.integralService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Integral> integralList = this.integralService.getList(paraMap);
		model.addAttribute("integralList", integralList);
		return "integral/list";
	}
	
	
	
	
	 /**
	 * @author hx
	 * @Description 跳转到添加积分商品页面方法
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "integral/insert";
	}
	
	
	/**
	 * @author hx
	 * @Description 添加积分商品方法
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Integral integral,Model model){
		integral.setIs_up("0");
		integral.setIs_del("0");
		this.integralService.insert(integral);
		model.addAttribute("promptmsg","积分商品添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author hx
	 * @Description 跳转到修改积分商品页面方法
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Integral integral = this.integralService.get(parameter_id);
		model.addAttribute("integral", integral);
		return "integral/update";
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Integral integral = this.integralService.get(parameter_id);
		model.addAttribute("integral", integral);
		return "integral/view";
	}
	
	
	/**
	 * @author hx
	 * @Description 修改积分商品方法
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,Integral integral,Model model){
		integral.setIs_up("0");
		integral.setIs_del("0");
		this.integralService.update(integral);
		model.addAttribute("promptmsg","积分商品修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author hx
	 * @Description 删除积分商品方法
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Integral integral  = this.integralService.get(parameter_id);
		integral.setIs_del("1");
		this.integralService.update(integral);
		model.addAttribute("promptmsg","积分商品删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author hx
	 * @Description 积分商品排序方法
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.integralService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author hx
	 * @Description 积分商品批量删除成功
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
					Integral integral  = this.integralService.get(id);
					integral.setIs_del("1");
					this.integralService.update(integral);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分积分商品已被引用,被引用的积分商品删除失败！");
		}else{
			model.addAttribute("promptmsg","积分商品删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author hx
	 * @Description 启用积分商品
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Integral integral  = this.integralService.get(parameter_id);
		integral.setIs_up("1");
		this.integralService.update(integral);
		model.addAttribute("promptmsg","积分商品上架成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author hx
	 * @Description 禁用积分商品
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Integral integral  = this.integralService.get(parameter_id);
		integral.setIs_up("0");
		this.integralService.update(integral);
		model.addAttribute("promptmsg","积分商品下架成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量启用积分商品
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Integral integral  = this.integralService.get(id);
			integral.setIs_up("1");
			this.integralService.update(integral);
		}
		model.addAttribute("promptmsg","积分商品批量上架成功！");
		return list(request, model);
	}
	

	/**
	 * @author hx
	 * @Description 批量禁用积分商品
	 * @param
	 * @date 2017-07-19 19:56:18
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Integral integral  = this.integralService.get(id);
			integral.setIs_up("0");
			this.integralService.update(integral);
		}
		model.addAttribute("promptmsg","积分商品批量下架成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author hx
	 * @Description 验证删除积分商品时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String integral_id){
		return false;
	}
	
	
	
}


/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-06 11:20:10  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: AdvController.java 
 * Author:chenjx
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.aten.annotation.TokenAnnotation;
import com.communal.util.StringUtil;
import com.aten.model.orm.Adv;
import com.aten.service.AdService;
import com.aten.service.AdvService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenjx
 * @Function 广告位  controller
 * @date 2017-07-06 11:20:10
 */
@Controller
@RequestMapping("admin/adv")
public class AdvController extends BaseController{

	private static final Logger logger = Logger.getLogger(AdvController.class);
	
	@Autowired
	private AdvService advService;
	@Autowired
	private AdService adService;
	
	/**
	 * @author chenjx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenjx
	 * @Description 广告位列表页方法
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.advService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Adv> advList = this.advService.getList(paraMap);
		for(Adv adv : advList){
			HashMap<String, Object> hMap = new HashMap<String, Object>();
			hMap.put("adv_id", adv.getAdv_id());
			int adCount = this.adService.getCount(hMap);
			if(adCount>0){
				adv.setIs_add_ads("1");
			}else{
				adv.setIs_add_ads("0");
			}
		}
		model.addAttribute("advList", advList);
		return "adv/list";
	}
	
	
	
	
	 /**
	 * @author chenjx
	 * @Description 跳转到添加广告位页面方法
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "adv/insert";
	}
	
	
	/**
	 * @author chenjx
	 * @Description 添加广告位方法
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Adv adv,Model model,RedirectAttributesModelMap modelMap){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap.put("adv_code",adv.getAdv_code());
		paraMap.put("the_terminal",adv.getThe_terminal());
		int count = this.advService.getCount(paraMap);
		if(count>0){
			model.addAttribute("promptmsg","广告位编码已存在！");
			return add(model);
		}
		this.advService.insert(adv);
		//model.addAttribute("promptmsg","广告位添加成功！");
		modelMap.addFlashAttribute("promptmsg", "广告位添加成功！");
		//return add(model);
		return goUrl("adv/add");
	}
	
	
		
	/**
	 * @author chenjx
	 * @Description 跳转到修改广告位页面方法
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Adv adv = this.advService.get(parameter_id);
		model.addAttribute("adv", adv);
		return "adv/update";
	}
	
	
	/**
	 * @author chenjx
	 * @Description 修改广告位方法
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,Adv adv,Model model){
		Adv oldAdv=advService.get(adv.getAdv_id());
		if(!oldAdv.getThe_terminal().equals(adv.getThe_terminal())){
			HashMap<String, Object> paraMap = new HashMap<String,Object>();
			//搜索封装
			paraMap.put("adv_code",adv.getAdv_code());
			paraMap.put("the_terminal",adv.getThe_terminal());

			int count = this.advService.getCount(paraMap);
			if(count>0){
				model.addAttribute("promptmsg","该广告位编码已存在该所属终端！");
				return add(model);
			}
		}

		this.advService.update(adv);
		model.addAttribute("promptmsg","广告位修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenjx
	 * @Description 删除广告位方法
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.advService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","广告位删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenjx
	 * @Description 广告位排序方法
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.advService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenjx
	 * @Description 广告位批量删除成功
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
					this.advService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分广告位已被引用,被引用的广告位删除失败！");
		}else{
			model.addAttribute("promptmsg","广告位删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenjx
	 * @Description 启用广告位
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Adv adv  = this.advService.get(parameter_id);
		adv.setState("1");
		this.advService.update(adv);
		model.addAttribute("promptmsg","广告位启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenjx
	 * @Description 禁用广告位
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Adv adv  = this.advService.get(parameter_id);
		adv.setState("0");
		this.advService.update(adv);
		model.addAttribute("promptmsg","广告位禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenjx
	 * @Description 批量启用广告位
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Adv adv  = this.advService.get(id);
			adv.setState("1");
			this.advService.update(adv);
		}
		model.addAttribute("promptmsg","广告位批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenjx
	 * @Description 批量禁用广告位
	 * @param
	 * @date 2017-07-06 11:20:10
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Adv adv  = this.advService.get(id);
			adv.setState("0");
			this.advService.update(adv);
		}
		model.addAttribute("promptmsg","广告位批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenjx
	 * @Description 验证删除广告位时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String adv_code){
		return false;
	}
	
	
	
}


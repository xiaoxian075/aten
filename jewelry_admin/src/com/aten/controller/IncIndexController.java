/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-18 17:01:21  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: IncIndexController.java 
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
import com.aten.model.orm.IncIndex;
import com.aten.service.IncIndexService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function  索引增量更新记录表  controller
 * @date 2017-07-18 17:01:21
 */
@Controller
@RequestMapping("admin/incindex")
public class IncIndexController extends BaseController{

	private static final Logger logger = Logger.getLogger(IncIndexController.class);
	
	@Autowired
	private IncIndexService incIndexService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description  索引增量更新记录表列表页方法
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.incIndexService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<IncIndex> incIndexList = this.incIndexService.getList(paraMap);
		model.addAttribute("incIndexList", incIndexList);
		return "incIndex/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加 索引增量更新记录表页面方法
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "incIndex/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加 索引增量更新记录表方法
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(IncIndex incIndex,Model model){
		this.incIndexService.insert(incIndex);
		model.addAttribute("promptmsg"," 索引增量更新记录表添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改 索引增量更新记录表页面方法
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		IncIndex incIndex = this.incIndexService.get(parameter_id);
		model.addAttribute("incIndex", incIndex);
		return "incIndex/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改 索引增量更新记录表方法
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,IncIndex incIndex,Model model){
		this.incIndexService.update(incIndex);
		model.addAttribute("promptmsg"," 索引增量更新记录表修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除 索引增量更新记录表方法
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.incIndexService.deleteOne(parameter_id);
		model.addAttribute("promptmsg"," 索引增量更新记录表删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description  索引增量更新记录表排序方法
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.incIndexService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description  索引增量更新记录表批量删除成功
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
					this.incIndexService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分 索引增量更新记录表已被引用,被引用的 索引增量更新记录表删除失败！");
		}else{
			model.addAttribute("promptmsg"," 索引增量更新记录表删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用 索引增量更新记录表
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		IncIndex incIndex  = this.incIndexService.get(parameter_id);
		//incIndex.setState("1");
		this.incIndexService.update(incIndex);
		model.addAttribute("promptmsg"," 索引增量更新记录表启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用 索引增量更新记录表
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		IncIndex incIndex  = this.incIndexService.get(parameter_id);
		//incIndex.setState("0");
		this.incIndexService.update(incIndex);
		model.addAttribute("promptmsg"," 索引增量更新记录表禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用 索引增量更新记录表
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			IncIndex incIndex  = this.incIndexService.get(id);
			//incIndex.setState("1");
			this.incIndexService.update(incIndex);
		}
		model.addAttribute("promptmsg"," 索引增量更新记录表批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用 索引增量更新记录表
	 * @param
	 * @date 2017-07-18 17:01:21
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			IncIndex incIndex  = this.incIndexService.get(id);
			//incIndex.setState("0");
			this.incIndexService.update(incIndex);
		}
		model.addAttribute("promptmsg"," 索引增量更新记录表批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除 索引增量更新记录表时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String index_id){
		return false;
	}
	
	
	
}


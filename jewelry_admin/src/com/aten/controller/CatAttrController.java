
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.CatAttr;
import com.aten.service.CatAttrService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/catattr")
public class CatAttrController extends BaseController{

	private static final Logger logger = Logger.getLogger(CatAttrController.class);
	
	@Autowired
	private CatAttrService catAttrService;
	
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.catAttrService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<CatAttr> catAttrList = this.catAttrService.getList(paraMap);
		model.addAttribute("catAttrList", catAttrList);
		return "catAttr/list";
	}
	
	
	
	
	@RequestMapping("add")
	public String add(Model model){
		return "catAttr/insert";
	}
	
	
	@RequestMapping("insert")
	public String insert(CatAttr catAttr,Model model){
		this.catAttrService.insert(catAttr);
		model.addAttribute("promptmsg","分类属性映射表添加成功！");
		return add(model);
	}
	
	
		
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		CatAttr catAttr = this.catAttrService.get(parameter_id);
		model.addAttribute("catAttr", catAttr);
		return "catAttr/update";
	}
	
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,CatAttr catAttr,Model model){
		this.catAttrService.update(catAttr);
		model.addAttribute("promptmsg","分类属性映射表修改成功！");
		return list(request,model);
	}
	
	
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.catAttrService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","分类属性映射表删除成功！");
		return list(request, model);
	}
	
	
	
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.catAttrService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


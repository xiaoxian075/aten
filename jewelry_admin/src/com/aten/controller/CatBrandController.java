
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.CatBrand;
import com.aten.service.CatBrandService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/catbrand")
public class CatBrandController extends BaseController{

	private static final Logger logger = Logger.getLogger(CatBrandController.class);
	
	@Autowired
	private CatBrandService catBrandService;
	
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.catBrandService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<CatBrand> catBrandList = this.catBrandService.getList(paraMap);
		model.addAttribute("catBrandList", catBrandList);
		return "catBrand/list";
	}
	
	
	
	
	@RequestMapping("add")
	public String add(Model model){
		return "catBrand/insert";
	}
	
	
	@RequestMapping("insert")
	public String insert(CatBrand catBrand,Model model){
		this.catBrandService.insert(catBrand);
		model.addAttribute("promptmsg","分类品牌关联添加成功！");
		return add(model);
	}
	
	
		
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		CatBrand catBrand = this.catBrandService.get(parameter_id);
		model.addAttribute("catBrand", catBrand);
		return "catBrand/update";
	}
	
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,CatBrand catBrand,Model model){
		this.catBrandService.update(catBrand);
		model.addAttribute("promptmsg","分类品牌关联修改成功！");
		return list(request,model);
	}
	
	
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.catBrandService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","分类品牌关联删除成功！");
		return list(request, model);
	}
	
	
	
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.catBrandService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


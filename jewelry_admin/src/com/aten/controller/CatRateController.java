
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.CatRate;
import com.aten.service.CatRateService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/catrate")
public class CatRateController extends BaseController{

	private static final Logger logger = Logger.getLogger(CatRateController.class);
	
	@Autowired
	private CatRateService catRateService;
	
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.catRateService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<CatRate> catRateList = this.catRateService.getList(paraMap);
		model.addAttribute("catRateList", catRateList);
		return "catRate/list";
	}
	
	
	
	
	@RequestMapping("add")
	public String add(Model model){
		return "catRate/insert";
	}
	
	
	@RequestMapping("insert")
	public String insert(CatRate catRate,Model model){
		this.catRateService.insert(catRate);
		model.addAttribute("promptmsg","分成比例添加成功！");
		return add(model);
	}
	
	
		
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		CatRate catRate = this.catRateService.get(parameter_id);
		model.addAttribute("catRate", catRate);
		return "catRate/update";
	}
	
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,CatRate catRate,Model model){
		this.catRateService.update(catRate);
		model.addAttribute("promptmsg","分成比例修改成功！");
		return list(request,model);
	}
	
	
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.catRateService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","分成比例删除成功！");
		return list(request, model);
	}
	
	
	
	
}


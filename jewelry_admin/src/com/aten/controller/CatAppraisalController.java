
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.CatAppraisal;
import com.aten.service.CatAppraisalService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/catappraisal")
public class CatAppraisalController extends BaseController{

	private static final Logger logger = Logger.getLogger(CatAppraisalController.class);
	
	@Autowired
	private CatAppraisalService catAppraisalService;
	
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.catAppraisalService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<CatAppraisal> catAppraisalList = this.catAppraisalService.getList(paraMap);
		model.addAttribute("catAppraisalList", catAppraisalList);
		return "catAppraisal/list";
	}
	
	
	
	
	@RequestMapping("add")
	public String add(Model model){
		return "catAppraisal/insert";
	}
	
	
	@RequestMapping("insert")
	public String insert(CatAppraisal catAppraisal,Model model){
		this.catAppraisalService.insert(catAppraisal);
		model.addAttribute("promptmsg","分类机构关联添加成功！");
		return add(model);
	}
	
	
		
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		CatAppraisal catAppraisal = this.catAppraisalService.get(parameter_id);
		model.addAttribute("catAppraisal", catAppraisal);
		return "catAppraisal/update";
	}
	
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,CatAppraisal catAppraisal,Model model){
		this.catAppraisalService.update(catAppraisal);
		model.addAttribute("promptmsg","分类机构关联修改成功！");
		return list(request,model);
	}
	
	
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.catAppraisalService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","分类机构关联删除成功！");
		return list(request, model);
	}
	
	
	
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.catAppraisalService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


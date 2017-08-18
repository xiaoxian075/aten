
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.DivideRecord;
import com.aten.service.DivideRecordService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/dividerecord")
public class DivideRecordController extends BaseController{

	private static final Logger logger = Logger.getLogger(DivideRecordController.class);
	
	@Autowired
	private DivideRecordService divideRecordService;
	
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.divideRecordService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<DivideRecord> divideRecordList = this.divideRecordService.getList(paraMap);
		model.addAttribute("divideRecordList", divideRecordList);
		return "divideRecord/list";
	}
	
	
	
	
	@RequestMapping("add")
	public String add(Model model){
		return "divideRecord/insert";
	}
	
	
	@RequestMapping("insert")
	public String insert(DivideRecord divideRecord,Model model){
		this.divideRecordService.insert(divideRecord);
		model.addAttribute("promptmsg","记录分成比例记录流添加成功！");
		return add(model);
	}
	
	
		
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		DivideRecord divideRecord = this.divideRecordService.get(parameter_id);
		model.addAttribute("divideRecord", divideRecord);
		return "divideRecord/update";
	}
	
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,DivideRecord divideRecord,Model model){
		this.divideRecordService.update(divideRecord);
		model.addAttribute("promptmsg","记录分成比例记录流修改成功！");
		return list(request,model);
	}
	
	
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.divideRecordService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","记录分成比例记录流删除成功！");
		return list(request, model);
	}
	
	
	
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.divideRecordService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


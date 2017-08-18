
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.Attr;
import com.aten.service.AttrService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/attr")
public class AttrController extends BaseController{

	private static final Logger logger = Logger.getLogger(AttrController.class);
	
	@Autowired
	private AttrService attrService;
	
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.attrService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Attr> attrList = this.attrService.getList(paraMap);
		model.addAttribute("attrList", attrList);
		return "attr/list";
	}
	
	
	
	
	@RequestMapping("add")
	public String add(Model model){
		return "attr/insert";
	}
	
	
	@RequestMapping("insert")
	public String insert(Attr attr,Model model){
		String attr_name=attr.getAttr_name();
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("attr_name",attr_name);
		List<Attr> attrList = this.attrService.getList(paraMap);
		if (attrList!=null&&attrList.size()>0){
			model.addAttribute("promptmsg","添加失败！该属性名称已存在！");
			return add(model);
		}
		this.attrService.insert(attr);
		model.addAttribute("promptmsg","属性添加成功！");
		attr=null;
		model.addAttribute("attr",attr);
		return add(model);
	}
	
	
		
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Attr attr = this.attrService.get(parameter_id);
		model.addAttribute("attr", attr);
		return "attr/update";
	}
	
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,Attr attr,Model model){
		String old_attr_name = request.getParameter("old_attr_name");
		if(!old_attr_name.equals(attr.getAttr_name())){
			HashMap<String, Object> paraMap = new HashMap<String,Object>();
			paraMap.put("attr_name",attr.getAttr_name());
			List<Attr> attrList = this.attrService.getList(paraMap);
			if (attrList!=null&&attrList.size()>0){
				model.addAttribute("promptmsg","修改失败！该属性名称已存在！");
				return edit(request,model);
			}
		}
		this.attrService.update(attr);
		model.addAttribute("promptmsg","属性表修改成功！");
		return list(request,model);
	}

	/**
	 * @author chenyi
	 * @Description 删除
	 * @param
	 * @date 2017-7-4 下午4:04:28
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		if (ids!=null){
				attrService.batchdelete(ids);
		}
		model.addAttribute("promptmsg","操作成功！(部分被引用的属性无法删除)");
		return list(request, model);
	}


	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.attrService.updateBatch(sortMapList);
		return list(request, model);
	}


	/*禁用*/
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals("")) return list(request, model);
		String attr_id=parameter_id;
		attrService.limitState(parameter_id);
		model.addAttribute("promptmsg", "操作成功(部分被引用的属性无法禁用)！");
		return list(request, model);
	}


	/**
	 * @author chenyi
	 * @Description 启用
	 * @param
	 * @date 2017-7-4 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		String attr_id=parameter_id;
		attrService.enableState(parameter_id);
		model.addAttribute("promptmsg","启用成功！");
		return list(request, model);
	}



}


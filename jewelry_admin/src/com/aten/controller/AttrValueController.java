package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aten.model.orm.Attr;
import com.aten.model.orm.AttrValue;
import com.aten.service.AttrService;
import com.aten.service.AttrValueService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linjunqin
 * @Function 属性值管理  controller
 * @date 2017-05-04 10:31:57
 */
@Controller
@RequestMapping("admin/attrvalue")
public class AttrValueController extends BaseController{

	private static final Logger logger = Logger.getLogger(AttrValueController.class);
	
	@Autowired
	private AttrValueService attrValueService;
	@Autowired
	private AttrService attrService;
	
	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-1-5 下午2:37:11
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
		String attr_id_code = request.getParameter("attr_code_s");
		Attr attr = this.attrService.get(attr_id_code);
		model.addAttribute("attr_id_code", attr_id_code);
//		model.addAttribute("attr_name_code", attr.getAttr_name());
    }  
	
	
	/**
	 * @author linjunqin
	 * @Description 属性值列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		paraMap.put("attr_id",parameter_id);
		List<AttrValue> attrValueList = this.attrValueService.getList(paraMap);
		model.addAttribute("attrValueList", attrValueList);
		model.addAttribute("attr_id",parameter_id);
		return "attrValue/list";
	}
	@RequestMapping("/list/{attr_id}")
	public String list(HttpServletRequest request,Model model,@PathVariable String attr_id){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		paraMap.put("attr_id",attr_id);
		List<AttrValue> attrValueList = this.attrValueService.getList(paraMap);
		model.addAttribute("attrValueList", attrValueList);
		model.addAttribute("attr_id",attr_id);
		return "attrValue/list";
	}
	/**
	 * @author linjunqin
	 * @Description 跳转到添加属性值页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("/add/{attr_id}")
	public String add(HttpServletRequest request,Model model,@PathVariable String attr_id){
		model.addAttribute("attr_id",attr_id);
		return "attrValue/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加属性值方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(HttpServletRequest request,AttrValue attrValue,Model model){
		String attrId=attrValue.getAttr_id();
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("attr_id",attrValue.getAttr_id());
		paraMap.put("attr_value",attrValue.getAttr_value());
		List<AttrValue> attrValueList = this.attrValueService.getList(paraMap);
		if (attrValueList!=null&&attrValueList.size()>0){
			model.addAttribute("promptmsg","添加失败！该属性值名称已存在！");
			return add(request,model,attrId);
		}
		attrValue.setAttr_name("");
		attrValue.setAttr_value_ico("");
		attrValue.setState("1");
		this.attrValueService.insert(attrValue);
		model.addAttribute("promptmsg","属性值添加成功！");
		model.addAttribute("parameter_id",attrValue.getAttr_id());
		attrValue=null;
		model.addAttribute("attrValue",attrValue);
		return add(request,model,attrId);
	}
	
	
		
	/**
	 * @author linjunqin
	 * @Description 跳转到修改属性值页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		AttrValue attrValue = this.attrValueService.get(parameter_id);
		model.addAttribute("parameter_id", parameter_id);
		model.addAttribute("attrValue", attrValue);
		return "attrValue/update";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 修改属性值方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,AttrValue attrValue,Model model){
		String old_attr_value = request.getParameter("old_attr_value");
		if(!old_attr_value.equals(attrValue.getAttr_value())){
			HashMap<String, Object> paraMap = new HashMap<String,Object>();
			paraMap.put("attr_value",attrValue.getAttr_value());
			List<AttrValue> attrValueList = this.attrValueService.getList(paraMap);
			if (attrValueList!=null&&attrValueList.size()>0){
				model.addAttribute("promptmsg","修改失败！该属性值名称已存在！");
				return edit(request,model);
			}
		}
		this.attrValueService.update(attrValue);
		model.addAttribute("promptmsg","属性值修改成功！");
		return list(request,model,attrValue.getAttr_id());
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 删除属性值方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		String attr_id=attrValueService.get(parameter_id).getAttr_id();
		this.attrValueService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","属性值删除成功！");
		return list(request, model,attr_id);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 属性值排序方法
	 * @param
	 * @date 2017-1-5 下午2:41:02
	 */
	@RequestMapping("sort/{attr_id}")
	public String sort(HttpServletRequest request,Model model,@PathVariable String attr_id){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.attrValueService.updateBatch(sortMapList);
		model.addAttribute("parameter_id",attr_id);
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		paraMap.put("attr_id",attr_id);
		List<AttrValue> attrValueList = this.attrValueService.getList(paraMap);
		model.addAttribute("attrValueList", attrValueList);
		model.addAttribute("attr_id",attr_id);
		return "attrValue/list";
	}
	
	/**
	 * @author linjunqin
	 * @Description 批量删除属性值成功
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		
		//转成数组更新
		String[] ids = parameter_id.split(",");
		String attr_id ="0";
		if(ids!=null&&ids.length>0){
			AttrValue attrValue = this.attrValueService.get(ids[0]);
			attr_id = attrValue.getAttr_id();
			model.addAttribute("parameter_id",attr_id);
		}
		this.attrValueService.delete(ids);
		model.addAttribute("promptmsg","属性值批量删除成功！");
		return list(request, model,attr_id);
	}
	
}


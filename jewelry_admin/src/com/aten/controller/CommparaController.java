package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.Commpara;
import com.aten.service.CommparaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/commpara")
public class CommparaController extends BaseController{

	private static final Logger logger = Logger.getLogger(CommparaController.class);
	
	@Autowired
	private CommparaService commparaService;
	
	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-1-5 下午2:37:11
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author linjunqin
	 * @Description 字典列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.commparaService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Commpara> commparaList = this.commparaService.getList(paraMap);
		model.addAttribute("commparaList", commparaList);
		return "commpara/list";
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到添加字典页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(){
		return "commpara/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加字典方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Commpara commpara,Model model){
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("para_code", commpara.getPara_code());
		paraMap.put("para_key", commpara.getPara_key());
		List<Commpara> commparaList = this.commparaService.getList(paraMap);
		if(commparaList!=null && commparaList.size()>0){
			model.addAttribute("promptmsg","字典编码对应的字典值编码已存在！");
			return add();
		}
		this.commparaService.insert(commpara);
		String para_code = commpara.getPara_code();
		commpara = new Commpara();
		commpara.setPara_code(para_code);
		model.addAttribute("commpara",commpara);
		model.addAttribute("promptmsg","字典添加成功！");
		return add();
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到修改字典页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Commpara commpara = this.commparaService.get(parameter_id);
		model.addAttribute("commpara", commpara);
		return "commpara/update";
	}
	
	/**
	 * @author linjunqin
	 * @Description 修改字典方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Commpara commpara,Model model){
		String old_para_code = request.getParameter("old_para_code");
		String old_para_key = request.getParameter("old_para_key");
		//编码一样
		if(commpara.getPara_code().equals(old_para_code)){
			//key 一样
			if(commpara.getPara_key().equals(old_para_key)){
				this.commparaService.update(commpara);
				model.addAttribute("promptmsg","字典修改成功！");
				return list(request,model);
			}
		}
		//key不一致时
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("para_code", commpara.getPara_code());
		paraMap.put("para_key", commpara.getPara_key());
		List<Commpara> commparaList = this.commparaService.getList(paraMap);
		if(commparaList!=null && commparaList.size()>0){
			model.addAttribute("promptmsg","字典编码对应的字典值已存在！");
			return list(request,model);
		}
		//直接更新
		this.commparaService.update(commpara);
		model.addAttribute("promptmsg","字典修改成功！");
		return list(request,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 删除字典方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.commparaService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","字典删除成功！");
		return list(request, model);
	}
	@RequestMapping("preDelete")
	public String preDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.commparaService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","分类编码删除成功！");
		return pre(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 批量删除成功
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		this.commparaService.delete(ids);
		model.addAttribute("promptmsg","字典删除成功！");
		return list(request, model);
	}
	/**
	 * @author linjunqin
	 * @Description 批量删除成功
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("preBatchDelete")
	public String preBatchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		this.commparaService.delete(ids);
		model.addAttribute("promptmsg","分类编码删除成功！");
		return pre(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 字典排序方法
	 * @param
	 * @date 2017-1-5 下午2:41:02
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.commparaService.updateBatch(sortMapList);
		model.addAttribute("promptmsg","字典排序成功！");
		return list(request, model);
	}
	@RequestMapping("presort")
	public String presort(HttpServletRequest request,Model model){
		String sort_val = request.getParameter("sort_val");
		String sort_id = request.getParameter("sort_id");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.commparaService.updateBatch(sortMapList);
		model.addAttribute("promptmsg","分类排序成功！");
		return pre(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 前台分类设置
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("pre")
	public String pre(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap.put("para_code","cfg_index_cat");
		paraMap = searchMap(request,paraMap,model);
		int count = this.commparaService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Commpara> commparaList = this.commparaService.getList(paraMap);
		model.addAttribute("commparaList", commparaList);
		return "commpara/pre";
	}
	/**
	 * @author chenyi
	 * @Description 跳转到前台分类设置
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("preadd")
	public String preadd(){
		return "commpara/preinsert";
	}

	@RequestMapping("preinsert")
	public String preinsert(Commpara commpara,Model model){
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("para_code", commpara.getPara_code());
		paraMap.put("para_key", commpara.getPara_key());
		List<Commpara> commparaList = this.commparaService.getList(paraMap);
		if(commparaList!=null && commparaList.size()>0){
			model.addAttribute("promptmsg","分类编码已存在！");
			return preadd();
		}
		this.commparaService.insert(commpara);
		String para_code = commpara.getPara_code();
		commpara = new Commpara();
		commpara.setPara_code(para_code);
		model.addAttribute("commpara",commpara);
		model.addAttribute("promptmsg","分类编码添加成功！");
		return preadd();
	}
	
	@RequestMapping("preupdate")
	public String preupdate(HttpServletRequest request,Commpara commpara,Model model){
		//直接更新
		this.commparaService.update(commpara);
		model.addAttribute("promptmsg","分类编码修改成功！");
		return pre(request,model);
	}
	
	@RequestMapping("preedit")
	public String preedit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Commpara commpara = this.commparaService.get(parameter_id);
		model.addAttribute("commpara", commpara);
		return "commpara/preupdate";
	}
}


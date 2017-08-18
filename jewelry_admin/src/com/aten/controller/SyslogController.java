package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.Syslog;
import com.aten.service.SyslogService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/syslog")
public class SyslogController extends BaseController{

	private static final Logger logger = Logger.getLogger(SyslogController.class);
	
	@Autowired
	private SyslogService syslogService  ;
	
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
	 * @Description 系统日志列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		if(paraMap.get("start_time") != null){
			paraMap.put("start_time", paraMap.get("start_time")+" 00:00:00");
		}
		if(paraMap.get("end_time") != null){
			paraMap.put("end_time", paraMap.get("end_time")+" 23:59:59");
		}
		int count = this.syslogService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Syslog> syslogList = this.syslogService.getList(paraMap);
		model.addAttribute("syslogList", syslogList);
		return "syslog/list";
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到添加系统日志页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(){
		return "syslog/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加系统日志方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Syslog syslog,Model model){
		this.syslogService.insert(syslog);
		model.addAttribute("promptmsg","系统日志添加成功！");
		return add();
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到修改系统日志页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Syslog syslog = this.syslogService.get(parameter_id);
		model.addAttribute("syslog", syslog);
		return "syslog/update";
	}
	
	/**
	 * @author linjunqin
	 * @Description 修改系统日志方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Syslog syslog,Model model){
		this.syslogService.update(syslog);
		model.addAttribute("promptmsg","系统日志修改成功！");
		return list(request,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 删除系统日志方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.syslogService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","系统日志删除成功！");
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 系统日志排序方法
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
		this.syslogService.updateBatch(sortMapList);
		return list(request, model);
	}
	
}


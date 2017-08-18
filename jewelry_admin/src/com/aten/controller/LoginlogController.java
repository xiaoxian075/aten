package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.LoginLog;
import com.aten.service.LoginLogService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/loginlog")
public class LoginlogController extends BaseController{

	private static final Logger logger = Logger.getLogger(LoginlogController.class);
	
	@Autowired
	private LoginLogService loginlogService;
	
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
	 * @Description 登录日志列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.loginlogService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<LoginLog> loginlogList = this.loginlogService.getList(paraMap);
		model.addAttribute("loginlogList", loginlogList);
		return "loginlog/list";
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到添加登录日志页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(){
		return "loginlog/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加登录日志方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(LoginLog loginlog,Model model){
		this.loginlogService.insert(loginlog);
		model.addAttribute("promptmsg","登录日志添加成功！");
		return add();
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到修改登录日志页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		LoginLog loginlog = this.loginlogService.get(parameter_id);
		model.addAttribute("loginlog", loginlog);
		return "loginlog/update";
	}
	
	/**
	 * @author linjunqin
	 * @Description 修改登录日志方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,LoginLog loginlog,Model model){
		this.loginlogService.update(loginlog);
		model.addAttribute("promptmsg","登录日志修改成功！");
		return list(request,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 删除登录日志方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.loginlogService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","登录日志删除成功！");
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 登录日志排序方法
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
		this.loginlogService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


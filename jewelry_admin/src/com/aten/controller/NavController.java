package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.Nav;
import com.aten.service.NavService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linjunqin
 * @Function 平台导航管理  controller
 * @date 2017-02-14 10:06:24
 */
@Controller
@RequestMapping("admin/nav")
public class NavController extends BaseController{

	private static final Logger logger = Logger.getLogger(NavController.class);
	
	@Autowired
	private NavService navService;
	
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
	 * @Description 导航列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		int count = this.navService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Nav> navList = this.navService.getList(paraMap);
		model.addAttribute("navList", navList);
		return "nav/list";
	}
	
	
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到添加导航页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(){
		return "nav/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加导航方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Nav nav,Model model){
		this.navService.insert(nav);
		model.addAttribute("promptmsg","导航添加成功！");
		return add();
	}
	
	
		
	/**
	 * @author linjunqin
	 * @Description 跳转到修改导航页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Nav nav = this.navService.get(parameter_id);
		model.addAttribute("nav", nav);
		return "nav/update";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 修改导航方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Nav nav,Model model){
		this.navService.update(nav);
		model.addAttribute("promptmsg","导航修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 删除导航方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.navService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","导航删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 导航排序方法
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
		this.navService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


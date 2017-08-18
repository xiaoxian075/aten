package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.SmsTemporary;
import com.aten.service.SmsTemporaryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linjunqin
 * @Function 临时短信类  controller
 * @date 2017-03-03 10:50:46
 */
@Controller
@RequestMapping("admin/smstemporary")
public class SmsTemporaryController extends BaseController{

	private static final Logger logger = Logger.getLogger(SmsTemporaryController.class);
	
	@Autowired
	private SmsTemporaryService smsTemporaryService;
	
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
	 * @Description 临时短信列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		int count = this.smsTemporaryService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<SmsTemporary> smsTemporaryList = this.smsTemporaryService.getList(paraMap);
		model.addAttribute("smsTemporaryList", smsTemporaryList);
		return "smsTemporary/list";
	}
	
	
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到添加临时短信页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(){
		return "smsTemporary/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加临时短信方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(SmsTemporary smsTemporary,Model model){
		this.smsTemporaryService.insert(smsTemporary);
		model.addAttribute("promptmsg","临时短信添加成功！");
		return add();
	}
	
	
		
	/**
	 * @author linjunqin
	 * @Description 跳转到修改临时短信页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		SmsTemporary smsTemporary = this.smsTemporaryService.get(parameter_id);
		model.addAttribute("smsTemporary", smsTemporary);
		return "smsTemporary/update";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 修改临时短信方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,SmsTemporary smsTemporary,Model model){
		this.smsTemporaryService.update(smsTemporary);
		model.addAttribute("promptmsg","临时短信修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 删除临时短信方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.smsTemporaryService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","临时短信删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 临时短信排序方法
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
		this.smsTemporaryService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


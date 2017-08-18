package com.aten.controller;

import javax.servlet.http.HttpServletRequest;

import com.communal.util.RandomCharUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.orm.Payway;
import com.aten.service.PaywayService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linjunqin
 * @Function 支付方式管理  controller
 * @date 2017-05-12 17:03:34
 */
@Controller
@RequestMapping("admin/payway")
public class PaywayController extends BaseController{

	private static final Logger logger = Logger.getLogger(PaywayController.class);
	
	@Autowired
	private PaywayService paywayService;
	
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
	 * @Description 支付方式列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.paywayService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Payway> paywayList = this.paywayService.getList(paraMap);
		model.addAttribute("paywayList", paywayList);
		return "payway/list";
	}
	
	
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到添加支付方式页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(Model model){
		return "payway/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加支付方式方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Payway payway,Model model){
		String pay_id = payway.getPay_id();
		Payway pw = this.paywayService.get(pay_id);
		if(pw!=null){
			model.addAttribute("promptmsg","支付编码已存在！");
			return add(model);
		}
		this.paywayService.insert(payway);
		model.addAttribute("promptmsg","支付方式添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author linjunqin
	 * @Description 跳转到修改支付方式页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Payway payway = this.paywayService.get(parameter_id);
		model.addAttribute("payway", payway);
		model.addAttribute("parameter_id",parameter_id);
		return "payway/update";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 修改支付方式方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Payway payway,Model model){
		String old_pay_id=request.getParameter("old_pay_id");
		String pay_id = payway.getPay_id();
		//如果修改了pay_id  则验证pay_id是否已存在
		if(!old_pay_id.equals(pay_id)){
			    Payway pw = this.paywayService.get(pay_id);
			    if (pw!=null){
					model.addAttribute("promptmsg","支付编码已存在！");
					return edit(request,model);
				}
		}
		this.paywayService.update(payway);
		model.addAttribute("promptmsg","支付方式修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 删除支付方式方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.paywayService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","支付方式删除成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量删除
	 * @param
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			this.paywayService.deleteOne(id);
		}
		model.addAttribute("promptmsg","批量删除成功！");
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 支付方式排序方法
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
		this.paywayService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


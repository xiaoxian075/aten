package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.aten.function.SysmenuFuc;
import com.aten.model.orm.Cat;
import com.aten.model.orm.Power;
import com.aten.model.orm.Role;
import com.aten.service.PowerService;
import com.communal.util.JsonUtil;
import com.communal.util.RandomCharUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("admin/power")
public class PowerController extends BaseController{

	private static final Logger logger = Logger.getLogger(PowerController.class);
	
	@Autowired
	private PowerService powerService;
	
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
	 * @Description 权限列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.powerService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Power> powerList = this.powerService.getList(paraMap);
		//菜单ID串转菜单名称
		for(int i=0;i<powerList.size();i++){
			Power power = powerList.get(i);
			power.setMenu_name_str(SysmenuFuc.getMenuNameStr(power.getMenu_id()));
		}
		model.addAttribute("powerList", powerList);
		return "power/list";
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到添加权限页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(Model model){
		return "power/insert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加权限方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Power power,Model model){
		//验证URL请求地址是否被关闭
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("url", power.getUrl());
		int count = this.powerService.getCount(paraMap);
		if(count>0){
			model.addAttribute("promptmsg","权限已被添加！");
			return add(model);
		}
		//随机生成十位字符做为标识
		String power_id =RandomCharUtil.getNumberRand();
		power.setPower_id(power_id);
		this.powerService.insert(power);
		model.addAttribute("promptmsg","权限添加成功！");
		return add(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到修改权限页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Power power = this.powerService.get(parameter_id);
		model.addAttribute("power", power);
		return "power/update";
	}
	

	
	/**
	 * @author linjunqin
	 * @Description 修改权限方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Power power,Model model){
		this.powerService.update(power);
		model.addAttribute("promptmsg","权限修改成功！");
		return list(request,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 删除权限方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.powerService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","权限删除成功！");
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 批量删除权限
	 * @param
	 * @date 2017-5-24 下午12:57:27
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(int i=0;i<ids.length;i++){
			this.powerService.deleteOne(ids[i]);
		}
		model.addAttribute("promptmsg","批量删除权限成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 权限排序方法
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
		this.powerService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * 权限表数据初始化
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("init")
	public void init(HttpServletRequest request, HttpServletResponse response) {
		// 存储返回信息列表数据
		List<String> uList = new ArrayList<String>();

		HashMap<String, Object> paraMap = new HashMap<String, Object>();

		// 获取SpringMVC中所有RequestMapping映射URL地址
		WebApplicationContext wac = (WebApplicationContext) request
				.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);// 获取上下文对象
		RequestMappingHandlerMapping bean = wac.getBean(RequestMappingHandlerMapping.class);// 通过上下文对象获取RequestMappingHandlerMapping实例对象
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();
		for (RequestMappingInfo rmi : handlerMethods.keySet()) {
			PatternsRequestCondition prc = rmi.getPatternsCondition();
			// 所有的映射地址
			Set<String> patterns = prc.getPatterns();
			for (String uStr : patterns) {
				// 判断数据库是否已经有此地址
				paraMap.clear();
				paraMap.put("url", uStr);
				int count = this.powerService.getCount(paraMap);
				if (count > 0) {
					// 数据库已经有就直接返回信息
					StringBuilder sb = new StringBuilder();
					sb.append("|");
					sb.append(uStr);
					sb.append("|被添加");
					uList.add(sb.toString());
				} else {
					// 创建新的对象
					Power p = new Power();
					p.setPower_name("-");
					p.setMenu_id("-");
					p.setPath_name("-");
					p.setPlat_role("0");
					p.setUrl(uStr);
					// 获取id
					String power_id = RandomCharUtil.getNumberRand();
					p.setPower_id(power_id);
					try {
						// 插入数据库
						this.powerService.insert(p);
						StringBuilder sb = new StringBuilder();
						sb.append("|");
						sb.append(uStr);
						sb.append("|成功");
						uList.add(sb.toString());
					} catch (Exception e) {
						e.printStackTrace();
						StringBuilder sb = new StringBuilder();
						sb.append("|");
						sb.append(uStr);
						sb.append("|异常");
						uList.add(sb.toString());
					}

				}
			}
		}
		// 返回json数据到页面
		outPrint(response, JsonUtil.list2json(uList));
	}
}


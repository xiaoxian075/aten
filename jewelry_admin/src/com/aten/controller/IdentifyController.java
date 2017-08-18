
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aten.model.AreaShort;
import com.aten.model.orm.Area;
import com.aten.model.orm.Identify;
import com.aten.service.AreaService;
import com.aten.service.IdentifyService;
import com.communal.node.ReqMsg;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/identify")
public class IdentifyController extends BaseController{

	//private static final Logger logger = Logger.getLogger(IdentifyController.class);
	
	@Autowired
	private IdentifyService identifyService;
	@Autowired
	private AreaService areaService;
	
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.identifyService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Identify> identifyList = this.identifyService.getList(paraMap);
		model.addAttribute("identifyList", identifyList);
		return "identify/list";
	}
	
	/**
	 * @author linjunqin
	 * @Description 查看用户页面
	 * @param
	 * @date 2017-5-9 下午4:38:41
	 */
	@RequestMapping("view")
	public String view(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Identify identify = this.identifyService.get(parameter_id);
		model.addAttribute("identify", identify);
		return "identify/view";
	}
	
	
	@RequestMapping("add")
	public String add(Model model){
		return "identify/insert";
	}
	
	
	@RequestMapping("insert")
	public String insert(Identify identify,Model model){
		String privode = identify.getIden_province();
		String city = identify.getIden_city();
		String count = identify.getIden_county();
		Area area1 = areaService.get(privode);
		Area area2 = areaService.get(city);
		Area area3 = areaService.get(count);
		identify.setIden_province(area1.getArea_name());
		identify.setIden_city(area2.getArea_name());
		identify.setIden_county(area3.getArea_name());
		identify.setCreate_time(new Timestamp(System.currentTimeMillis()).toString());
		this.identifyService.insert(identify);
		model.addAttribute("promptmsg","测试添加成功！");
		return "identify/insert";
	}
	
	@RequestMapping(value="getparentarea",produces="application/json;charset=UTF-8;")
	@ResponseBody
	public String getParentArea(HttpServletResponse response, String parent_area_id){
		List<AreaShort> area = areaService.getParentArea(parent_area_id);
		String res = new Gson().toJson(new ReqMsg(0,"succ",area));
		response.setCharacterEncoding("UTF-8");
		return res;
	}
	
	
		
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Identify identify = this.identifyService.get(parameter_id);
		model.addAttribute("identify", identify);
		return "identify/update";
	}
	
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,Identify identify,Model model){
		String privode = identify.getIden_province();
		String city = identify.getIden_city();
		String count = identify.getIden_county();
		Area area1=null,area2=null,area3=null;
		if (privode!=null && privode.length()>0)
			area1 = areaService.get(privode);
		if (city!=null && city.length()>0)
			area2 = areaService.get(city);
		if (count!=null && count.length()>0)
			area3 = areaService.get(count);
		if (area1==null || area2==null || area3==null) {
			identify.setIden_province(null);
			identify.setIden_city(null);
			identify.setIden_county(null);
		} else {
			identify.setIden_province(area1.getArea_name());
			identify.setIden_city(area2.getArea_name());
			identify.setIden_county(area3.getArea_name());
		}
		
		this.identifyService.update(identify);
		model.addAttribute("promptmsg","机构修改成功！");
		return list(request,model);
	}
	
	
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.identifyService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","测试删除成功！");
		return list(request, model);
	}
	
	
	
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.identifyService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
}


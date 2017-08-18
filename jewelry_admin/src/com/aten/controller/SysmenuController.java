package com.aten.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.model.bean.TreeTableBean;
import com.aten.model.orm.Cat;
import com.aten.model.orm.Sysmenu;
import com.aten.service.SysmenuService;
import com.communal.util.JsonUtil;
import com.communal.util.RandomCharUtil;
import com.communal.util.StringUtil;

/**
 * @author linjunqin
 * @Description 系统菜单管理模块
 * @param
 * @date 2016-12-31 上午8:44:12
 */
@Controller
@RequestMapping("/admin/sysmenu")
public class SysmenuController extends BaseController{

	private static final Logger logger = Logger.getLogger(SysmenuController.class);
	
	@Autowired
	private SysmenuService sysmenuService;
	
	/**
	 * @author linjunqin
	 * @Description 跳转系统菜单列表页
	 * @param
	 * @date 2016-12-31 上午8:44:08
	 */
	@RequestMapping("list")
	public String list(TreeTableBean treeBean,Model model){
		model.addAttribute("treeBean",treeBean);
		return "sysmenu/list";
	}
	
	@RequestMapping("panterlist")
	public String panterlist(TreeTableBean treeBean,Model model){
		model.addAttribute("treeBean",treeBean);
		return "sysmenu/panterlist";
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取系统菜单列表数据json字符串返回
	 * @param
	 * @date 2016-12-31 上午8:51:21
	 */
	@RequestMapping("getChildList")
	public void getChildList(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> paraMap = new HashMap<String,Object>();
		//上一级ID
		String up_id = request.getParameter("up_id");  
		if(up_id!=null && !up_id.equals("")){
			paraMap.put("parent_menu_id", up_id);
		}
		//角色类型
		String plat_role = request.getParameter("pr");  
		if(!StringUtil.isEmpty(plat_role)){
			paraMap.put("plat_role", plat_role);
		}
		HashMap<String,Object> jsonMap = new HashMap<String,Object>();
		//获取菜单对象
		Sysmenu sysmenu = this.sysmenuService.get(up_id);
		jsonMap.put("title", sysmenu.getMenu_name());
		jsonMap.put("title_id",  sysmenu.getMenu_id());
		//返回子列表
		List<Sysmenu> list = this.sysmenuService.getList(paraMap);
		//String listJson = JsonUtil.list2json(list);
		jsonMap.put("list",list);
		String listJson = JsonUtil.map2json(jsonMap);
		outPrint(response, listJson);
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取正常显示数据的菜单
	 * @param
	 * @date 2017-1-10 上午10:43:33
	 */
	@RequestMapping("normalList")
	public void normalList(HttpServletRequest request,HttpServletResponse response){
		//上一级分类标识
		String parent_cat_id = request.getParameter("id");
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		if(parent_cat_id!=null && !parent_cat_id.equals("")){
			paraMap.put("parent_menu_id", parent_cat_id);
			//获取菜单标识
			String pr = request.getParameter("pr");
			paraMap.put("plat_role", pr);
			List<Sysmenu> list = this.sysmenuService.getList(paraMap);
			HashMap<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("list",list);
			String listJson = JsonUtil.map2json(jsonMap);
			outPrint(response, listJson);
		}
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到新增页面
	 * @param
	 * @date 2016-12-31 下午2:56:00
	 */
	@RequestMapping("add")
	public String add(TreeTableBean treeBean,Model model){
		String up_id =treeBean.getUp_id();  
		if(up_id==null) return null;
		//获取菜单对象
		Sysmenu sysmenu = this.sysmenuService.get(up_id);
		String parent_name = sysmenu.getMenu_name();
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("up_id", up_id);
		model.addAttribute("treeBean", treeBean);
		return "sysmenu/insert";
	}
	
	@RequestMapping("panteradd")
	public String panteradd(TreeTableBean treeBean,Model model){
		String up_id =treeBean.getUp_id();  
		if(up_id==null) return null;
		//获取菜单对象
		Sysmenu sysmenu = this.sysmenuService.get(up_id);
		String parent_name = sysmenu.getMenu_name();
		model.addAttribute("parent_name", parent_name);
		model.addAttribute("up_id", up_id);
		model.addAttribute("treeBean", treeBean);
		return "sysmenu/panterinsert";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 添加系统菜单
	 * @param
	 * @date 2016-12-31 下午2:57:52
	 */
	@RequestMapping("insert")
	public String insert(TreeTableBean treeBean,Sysmenu sysmenu,Model model){
		Sysmenu parent_sysmenu = sysmenuService.get(sysmenu.getParent_menu_id());
		//随机生成十位字符做为菜单标识
		String menu_id =RandomCharUtil.getNumberRand();
		//算出子菜单的等级
		String child_level =  String.valueOf(Integer.parseInt(parent_sysmenu.getMenu_level().trim())+1);
		String level_menu = parent_sysmenu.getLevel_menu()+","+menu_id;
		sysmenu.setMenu_id(menu_id);
		sysmenu.setMenu_level(child_level);
		sysmenu.setLevel_menu(level_menu);
		this.sysmenuService.insert(sysmenu);
		model.addAttribute("promptmsg","系统菜单添加成功！");
		return add(treeBean,model);
	}
	
	@RequestMapping("panterinsert")
	public String panterinsert(TreeTableBean treeBean,Sysmenu sysmenu,Model model){
		Sysmenu parent_sysmenu = sysmenuService.get(sysmenu.getParent_menu_id());
		//随机生成十位字符做为菜单标识
		String menu_id =RandomCharUtil.getNumberRand();
		//算出子菜单的等级
		String child_level =  String.valueOf(Integer.parseInt(parent_sysmenu.getMenu_level().trim())+1);
		String level_menu = parent_sysmenu.getLevel_menu()+","+menu_id;
		sysmenu.setMenu_id(menu_id);
		sysmenu.setMenu_level(child_level);
		sysmenu.setLevel_menu(level_menu);
		this.sysmenuService.insert(sysmenu);
		model.addAttribute("promptmsg","系统菜单添加成功！");
		return panteradd(treeBean,model);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到更新页面
	 * @param
	 * @date 2016-12-31 下午2:56:55
	 */
	@RequestMapping("edit")
	public String edit(TreeTableBean treeBean,Model model){
		String id = treeBean.getId();
		if(id==null) return null;
		//获取当前菜单的对象
		Sysmenu sysmenu = this.sysmenuService.get(id);
		model.addAttribute("sysmenu",sysmenu);
		//获取父节点菜单名称
		Sysmenu parent_sysmenu = this.sysmenuService.get(sysmenu.getParent_menu_id().trim());
		String parent_name = parent_sysmenu.getMenu_name();
		model.addAttribute("parent_name", parent_name);
		treeBean.setUp_id(sysmenu.getParent_menu_id());
		model.addAttribute("treeBean", treeBean);
		return "sysmenu/update";
	}
	
	@RequestMapping("panteredit")
	public String panteredit(TreeTableBean treeBean,Model model){
		String id = treeBean.getId();
		if(id==null) return null;
		//获取当前菜单的对象
		Sysmenu sysmenu = this.sysmenuService.get(id);
		model.addAttribute("sysmenu",sysmenu);
		//获取父节点菜单名称
		Sysmenu parent_sysmenu = this.sysmenuService.get(sysmenu.getParent_menu_id().trim());
		String parent_name = parent_sysmenu.getMenu_name();
		model.addAttribute("parent_name", parent_name);
		treeBean.setUp_id(sysmenu.getParent_menu_id());
		model.addAttribute("treeBean", treeBean);
		return "sysmenu/panterupdate";
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新商品
	 * @param
	 * @date 2017-1-3 上午11:07:24
	 */
	@RequestMapping("update")
	public String update(TreeTableBean treeBean,Sysmenu sysmenu,Model model){
		String menu_id = treeBean.getId();
		sysmenu.setMenu_id(menu_id);
		this.sysmenuService.update(sysmenu);
		return list(treeBean,model);
	}
	
	@RequestMapping("panterupdate")
	public String panterupdate(TreeTableBean treeBean,Sysmenu sysmenu,Model model){
		String menu_id = treeBean.getId();
		sysmenu.setMenu_id(menu_id);
		this.sysmenuService.update(sysmenu);
		return panterlist(treeBean,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 删除菜单信息
	 * @param
	 * @date 2017-1-3 上午10:34:50
	 */
	@RequestMapping("delete")
	public String delete(TreeTableBean treeBean,Model model){
		if(treeBean.getId()!=null){
			this.sysmenuService.deleteOne(treeBean.getId().trim());
		}
		return list(treeBean, model);
	}
	
	@RequestMapping("panterdelete")
	public String panterdelete(TreeTableBean treeBean,Model model){
		if(treeBean.getId()!=null){
			this.sysmenuService.deleteOne(treeBean.getId().trim());
		}
		return list(treeBean, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 系统菜单的排序
	 * @param
	 * @date 2017-1-3 上午11:58:09
	 */
	@RequestMapping("sort")
	public String sort(TreeTableBean treeBean,Model model,HttpServletRequest request){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.sysmenuService.updateBatch(sortMapList);
		return list(treeBean, model);
	}
	
	@RequestMapping("pantersort")
	public String pantersort(TreeTableBean treeBean,Model model,HttpServletRequest request){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.sysmenuService.updateBatch(sortMapList);
		return list(treeBean, model);
	}
	
}


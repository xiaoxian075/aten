package com.aten.controller;

import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.r;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aten.model.orm.Manager;
import com.aten.model.orm.Power;
import com.aten.model.orm.Role;
import com.aten.model.orm.Sysmenu;
import com.aten.service.ManagerService;
import com.aten.service.PowerService;
import com.aten.service.RoleService;
import com.aten.service.SysmenuService;
import com.communal.util.RandomCharUtil;
import com.communal.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/role")
public class RoleController extends BaseController{

	private static final Logger logger = Logger.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private SysmenuService sysmenuService;
	@Autowired
	private PowerService powerService;
	@Autowired
	private ManagerService managerService;
	
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
	 * @Description 角色列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.roleService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Role> roleList = this.roleService.getList(paraMap);
		model.addAttribute("roleList", roleList);
		return "role/list";
	}
	
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到添加角色页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(Model model){
		getMenuPowerList(model,null);
		return "role/insert";
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取菜单，操作权限的数据
	 * @param
	 * @date 2017-1-19 下午5:01:51
	 */
	private void getMenuPowerList(Model model,String plat_role){
		HashMap<String, Object> menuMap = new HashMap<String, Object>();
		if(StringUtil.isEmpty(plat_role)){
			plat_role="0";
		}
		menuMap.put("plat_role", plat_role);
		//一级菜单
		menuMap.put("menu_level","1");
		List<Sysmenu> sysmenuOneList = this.sysmenuService.getList(menuMap);
		model.addAttribute("sysmenuOneList", sysmenuOneList);
		//二级菜单
		menuMap.put("menu_level","2");
		List<Sysmenu> sysmenuTwoList = this.sysmenuService.getList(menuMap);
		model.addAttribute("sysmenuTwoList", sysmenuTwoList);
		//3级菜单
		menuMap.put("menu_level","3");
		List<Sysmenu> sysmenuThreeList = this.sysmenuService.getList(menuMap);
		model.addAttribute("sysmenuThreeList", sysmenuThreeList);
		//获取权限的列表
		HashMap<String,Object> powerMap = new HashMap<String, Object>();
		if(plat_role==null){
			powerMap.put("plat_role","0");
		}else{
			powerMap.put("plat_role",plat_role);
		}
		List<Power> powerList = this.powerService.getList(powerMap);
		model.addAttribute("powerList", powerList);
	}
	
	/**
	 * @author linjunqin
	 * @Description 添加角色方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Role role,Model model){
		//随机生成十位字符做为菜单标识
		//String roleCode =RandomCharUtil.getNumberRand();
		//role.setRole_code(roleCode);
		HashMap<String, Object> para = new HashMap<String, Object>();
		para.put("role_name", role.getRole_name());
		List<Role> roles = this.roleService.getList(para);
		if(roles!=null && roles.size()>0){
			model.addAttribute("promptmsg","角色名称已存在！");
			return add(model);
		}
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("role_code", role.getRole_code());
		List<Role> roleList = this.roleService.getList(paraMap);
		if(roleList!=null && roleList.size()>0){
			model.addAttribute("promptmsg","角色编码已存在！");
			return add(model);
		}
		role.setIs_sys("0");
		role.setPlat_role("0");
		this.roleService.insert(role);
		role=null;
		model.addAttribute("role", role);
		model.addAttribute("promptmsg","角色添加成功！");
		return add(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到修改角色页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Role role = this.roleService.get(parameter_id);
		model.addAttribute("role", role);
		model.addAttribute("is_update", "1");
		getMenuPowerList(model,role.getPlat_role());
		return "role/update";
	}
	
	/**
	 * @author linjunqin
	 * @Description 修改角色方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request,Role role,Model model){
		String old_role_code = request.getParameter("role_code");
		if(old_role_code.equals( role.getRole_code())){
			//如果是系统管理员  状态不能是禁用
			if("syscode".equals(role.getRole_code())){
				role.setState("1");
			}
			this.roleService.update(role);
		}else{
			//找寻是否存在角色编码
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("role_code", role.getRole_code());
			List<Role> roleList = this.roleService.getList(paraMap);
			if(roleList!=null && roleList.size()>0){
				model.addAttribute("promptmsg","角色名称已存在！");
				return add(model);
			}
			this.roleService.update(role);
		}
		model.addAttribute("promptmsg","角色修改成功！");
		return list(request,model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 删除角色方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//验证是否可以删除角色
		if(checkRole(parameter_id)){
			model.addAttribute("promptmsg","角色已关联用户,不可删除！");
			return list(request, model);
		}
		this.roleService.deleteByCode(parameter_id);
		model.addAttribute("promptmsg","角色删除成功！");
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 验证删除角色时是否被引用
	 * @param
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkRole(String role_code){
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("role_code", role_code);
		List<Manager> managerList = this.managerService.getList(paraMap);
		if(managerList!=null && managerList.size()>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 角色排序方法
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
		this.roleService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 启用角色
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Role role = new Role();
		role.setState("1");
		role.setRole_code(parameter_id);
		this.roleService.update(role);
		model.addAttribute("promptmsg","角色启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 禁用角色
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Role role = this.roleService.get(parameter_id);
		if(role.getIs_sys().equals("0")){
			role.setState("0");
			this.roleService.update(role);
			//对应角色的用户也要对应禁用
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("role_code", role.getRole_code());
			List<Manager> managerList = this.managerService.getList(paraMap);
			for(int i=0;i<managerList.size();i++){
				Manager manager = managerList.get(i);
				manager.setState("0");//同步禁用
				this.managerService.update(manager);
			}
			model.addAttribute("promptmsg","角色禁用成功，对应的用户也已禁用！");
		}
		return list(request, model);
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
		boolean flag=true;
		for(String id:ids){
			//验证是否可以删除角色
			if(!checkRole(id)){
				this.roleService.deleteByCode(id);
			}else{
				flag =false;
			}
		}
		//判断返回消息
		if(flag){
			model.addAttribute("promptmsg","角色删除成功！");
		}else{
			model.addAttribute("promptmsg","角色批量删除成功,部分角色被关联无法删除！");
		}
		
		return list(request, model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 批量启用角色
	 * @param
	 * @date 2017-5-3 下午4:08:03
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Role role = new Role();
			role.setState("1");
			role.setRole_code(id);
			this.roleService.update(role);
		}
		model.addAttribute("promptmsg","角色批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author linjunqin
	 * @Description 批量禁用角色
	 * @param
	 * @date 2017-5-3 下午4:08:03
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String msg="角色禁用成功，对应的用户也已禁用！";
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Role role = this.roleService.get(id);
			if(role.getIs_sys().equals("0")){
				role.setState("0");
				this.roleService.update(role);
				//对应角色的用户也要对应禁用
				HashMap<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("role_code", role.getRole_code());
				List<Manager> managerList = this.managerService.getList(paraMap);
				for(int i=0;i<managerList.size();i++){
					Manager manager = managerList.get(i);
					manager.setState("0");//同步禁用
					this.managerService.update(manager);
				}
				model.addAttribute("promptmsg",msg);
			}
		}
		return list(request, model);
	}
}


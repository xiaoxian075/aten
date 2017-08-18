package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aten.annotation.TokenAnnotation;
import com.aten.model.orm.Manager;
import com.aten.model.orm.Organize;
import com.aten.model.orm.Role;
import com.aten.service.ManagerService;
import com.aten.service.OrganizeService;
import com.aten.service.RoleService;
import com.communal.constants.Constant;
import com.communal.util.Md5Util;
import com.communal.util.StringUtil;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("admin/manager")
public class ManagerController extends BaseController {

	private static final Logger logger = Logger.getLogger(ManagerController.class);

	@Autowired
	private ManagerService managerService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizeService organizeService;

	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-1-5 下午2:37:11
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 用户列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		// 加载角色
		loadRole(model);
		// 搜索封装
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap = searchMap(request, paraMap, model);
		int count = this.managerService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Manager> managerList = this.managerService.getList(paraMap);
		model.addAttribute("managerList", managerList);
		return "manager/list";
	}

	/**
	 * @author linjunqin
	 * @Description 验证角色是否被禁用
	 * @param
	 * @date 2017-5-23 下午7:18:54
	 */
	private boolean checkRole(String role_code) {
		Role role = this.roleService.get(role_code);
		if (role.getState().equals("1")) {
			return false;
		}
		return true;
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到添加用户页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		loadRole(model);
		return "manager/insert";
	}

	/**
	 * @author linjunqin
	 * @Description 添加用户方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Manager manager, Model model) {
		String manager_name = manager.getMana_name();
		manager.setMana_type("1");
		// 查看是否存在该用户
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("mana_name", manager_name);
		int count = this.managerService.getCount(paraMap);
		if (count > 0) {
			model.addAttribute("promptmsg", "用户已存在！");
			return add(model);
		}
		// 设置密码
		manager.setPassword(Md5Util.getMD5(Constant.INIT_PASSWORD));
		// 处理环信帐号
		String ease_user_name = manager.getMana_name();
		String ease_password = "";
		manager.setEase_id(ease_user_name);
		manager.setEase_pwd(ease_password);
		// 注册环信帐号
		// EasemobClient.addEaseMobUser(ease_user_name,ease_password);
		this.managerService.insert(manager);
		model.addAttribute("promptmsg", "用户添加成功！");
		return add(model);
	}

	/**
	 * @author linjunqin
	 * @Description 加载角色
	 * @param
	 * @date 2017-2-16 上午10:22:14
	 */
	private void loadRole(Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("plat_role", "0");
		paraMap.put("state", "1");
		List<Role> roleList = this.roleService.getList(paraMap);
		model.addAttribute("roleList", roleList);
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到修改用户页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Manager manager = this.managerService.get(parameter_id);
		model.addAttribute("manager", manager);
		loadRole(model);
		return "manager/update";
	}

	/**
	 * @author linjunqin
	 * @Description 查看用户页面
	 * @param
	 * @date 2017-5-9 下午4:38:41
	 */
	@RequestMapping("view")
	public String view(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Manager manager = this.managerService.get(parameter_id);
		// 获取角色名称
		Role role = this.roleService.get(manager.getRole_code());
		model.addAttribute("manager", manager);
		if (role != null && role.getRole_name() != null) {
			model.addAttribute("role_name", role.getRole_name());
		}
		// 获取部门名称
		if (manager != null) {
			String last_org_id = manager.getThe_org();
			if (last_org_id != null && !last_org_id.equals("")) {
				if (last_org_id.indexOf(",") != -1) {
					last_org_id = last_org_id.substring(last_org_id.lastIndexOf(",")+1, last_org_id.length());
				}
			}
			Organize organize = this.organizeService.get(last_org_id);
			if (organize != null) {
				model.addAttribute("organize_name", organize.getOrg_name());
			}
		}
		return "manager/view";
	}

	/**
	 * @author linjunqin
	 * @Description 用户修改密码页面
	 * @param
	 * @date 2017-5-9 下午4:53:01
	 */
	@RequestMapping("editpwd")
	public String editpwd(HttpServletRequest request, Model model) {
		/*
		 * String parameter_id = request.getParameter("parameter_id");
		 * if(parameter_id==null ||parameter_id.equals("")) return
		 * list(request,model);
		 */
		String user_id = (String) request.getSession().getAttribute(Constant.USER_ID);
		// 获取对象
		Manager manager = this.managerService.get(user_id);
		model.addAttribute("manager", manager);
		model.addAttribute("mana_name", request.getSession().getAttribute(Constant.USER_NAME));
		return "manager/editpwd";
	}

	/**
	 * @author linjunqin
	 * @Description 修改密码
	 * @param
	 * @date 2017-5-9 下午4:53:36
	 */
	@RequestMapping("updatepwd")
	public String updatepwd(HttpServletRequest request, Model model) {
		String old_password = request.getParameter("old_password");
		String new_password = request.getParameter("new_password");
		String confirm_password = request.getParameter("confirm_password");

		// 验证旧密码
		if (StringUtil.isEmpty(old_password)) {
			model.addAttribute("promptmsg", "旧密码不能为空！");
			return editpwd(request, model);
		}
		// 验证新密码
		if (StringUtil.isEmpty(new_password)) {
			model.addAttribute("promptmsg", "新密码不能为空！");
			return editpwd(request, model);
		}
		if (new_password.length() < 6) {
			model.addAttribute("promptmsg", "密码长度不能小于6位！");
			return editpwd(request, model);
		}
		if (new_password.length() > 16) {
			model.addAttribute("promptmsg", "密码长度不能大于16位！");
			return editpwd(request, model);
		}
		// 验证确认新密码不能为空
		if (StringUtil.isEmpty(confirm_password)) {
			model.addAttribute("promptmsg", "确认新密码不能为空！");
			return editpwd(request, model);
		}

		// 验证是否为数字和字母组成
		String regEx = "[0-9]+[a-zA-Z]+[0-9a-zA-Z]*|[a-zA-Z]+[0-9]+[0-9a-zA-Z]*";
		//String regEx = "[a-zA-Z0-9]+";
		if (!new_password.matches(regEx)) {
			model.addAttribute("promptmsg", "密码必须由字母和数字组成！");
			return editpwd(request, model);
		}
		// 验证新密码是否与确认新密码一致
		if (!confirm_password.equals(new_password)) {
			model.addAttribute("promptmsg", "新密码与确认新密码不一致,请重新输入！");
			return editpwd(request, model);
		}
		// 修改密码业务regEx
		String user_id = (String) request.getSession().getAttribute(Constant.USER_ID);
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("mana_id", user_id);
		paraMap.put("password", Md5Util.getMD5(old_password));
		List<Manager> managerList = this.managerService.getList(paraMap);
		if (managerList != null && managerList.size() > 0) {
			Manager manager = new Manager();
			manager.setMana_id(user_id);
			manager.setPassword(Md5Util.getMD5(new_password));
			this.managerService.update(manager);
			// model.addAttribute("mana_name",request.getSession().getAttribute(Constants.USER_NAME));
			// model.addAttribute("manager",manager);
			// model.addAttribute("promptmsg","密码修改成功！");
			getSession().invalidate();
			return "redirect:/login";
		} else {
			model.addAttribute("mana_name", request.getSession().getAttribute(Constant.USER_NAME));
			model.addAttribute("promptmsg", "旧密码不正确！");
			return "manager/editpwd";
		}
	}

	/**
	 * @author linjunqin
	 * @Description 修改用户方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request, Manager manager, Model model) {
		// 获取旧用户名
		String old_mana_name = request.getParameter("old_mana_name");
		// 验证角色是否被禁用
		if (checkRole(manager.getRole_code()) && manager.getState().equals("0")) {
			model.addAttribute("promptmsg", "用户所属的角色被禁用,请先启用角色！");
			return list(request, model);
		}
		// 更新时如果用户名与修改前一样，则直接更新
		if (old_mana_name.equals(manager.getMana_name())) {
			this.managerService.update(manager);
			model.addAttribute("promptmsg", "用户修改成功！");
			return list(request, model);
		}
		// 否则判断修改用户名，查看是否存在该用户
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("mana_name", manager.getMana_name());
		int count = this.managerService.getCount(paraMap);
		if (count > 0) {
			model.addAttribute("promptmsg", "修改失败,用户已存在！");
			model.addAttribute("parameter_id", manager.getMana_id());
			return edit(request, model);
		}
		this.managerService.update(manager);
		model.addAttribute("promptmsg", "用户修改成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 初始化密码
	 * @param
	 * @date 2017-5-9 下午3:17:43
	 */
	@RequestMapping("initpwd")
	public String initpwd(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		Manager manager = new Manager();
		manager.setMana_id(parameter_id);
		manager.setPassword(Md5Util.getMD5(Constant.INIT_PASSWORD));
		this.managerService.update(manager);
		model.addAttribute("promptmsg", "初始化密码成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 启用用户
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Manager manager = this.managerService.get(parameter_id);
		// 验证角色是否被禁用
		if (checkRole(manager.getRole_code())) {
			model.addAttribute("promptmsg", "用户所属的角色被禁用,请先启用角色！");
			return list(request, model);
		} else {
			manager = new Manager();
			manager.setState("1");
			manager.setMana_id(parameter_id);
			this.managerService.update(manager);
		}
		model.addAttribute("promptmsg", "用户启用成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 禁用用户
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);

		Manager manager = this.managerService.get(parameter_id);
		if (manager.getMana_type().equals("1") && !manager.getRole_code().equals(Constant.SYS_ROLE_CODE)) {// 不是系统管理员
			manager = new Manager();
			manager.setState("0");
			manager.setMana_id(parameter_id);
			this.managerService.update(manager);
			model.addAttribute("promptmsg", "用户禁用成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 删除用户方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.managerService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "用户删除成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量删除成功
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		boolean falg = true;
		for (String id : ids) {
			Manager manager = this.managerService.get(id);
			if (manager.getMana_type().equals("1") && !manager.getRole_code().equals(Constant.SYS_ROLE_CODE)) {// 不是系统管理员
				this.managerService.deleteOne(id);
			} else {
				falg = false;
			}
		}
		if (falg) {
			model.addAttribute("promptmsg", "用户批量删除成功！");
		} else {
			model.addAttribute("promptmsg", "用户批量删除成功,部分用户不符合条件,不做修改！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量启用用户
	 * @param
	 * @date 2017-5-3 下午4:08:03
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		boolean flag = false;
		for (String id : ids) {
			Manager manager = this.managerService.get(id);
			// 验证角色是否被禁用
			if (checkRole(manager.getRole_code())) {
				flag = true;
			} else {
				manager = new Manager();
				manager.setState("1");
				manager.setMana_id(id);
				this.managerService.update(manager);
			}
		}
		// 判断提示
		if (flag) {
			model.addAttribute("promptmsg", "部分用户所属的角色被禁用,请先启用角色！");
		} else {
			model.addAttribute("promptmsg", "用户批量启用成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量禁用用户
	 * @param
	 * @date 2017-5-3 下午4:08:03
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		boolean falg = true;
		for (String id : ids) {
			Manager manager = this.managerService.get(id);
			if (manager.getMana_type().equals("1") && !manager.getRole_code().equals(Constant.SYS_ROLE_CODE)) {// 不是系统管理员
				manager = new Manager();
				manager.setState("0");
				manager.setMana_id(id);
				this.managerService.update(manager);
			} else {
				falg = false;
			}
		}
		if (falg) {
			model.addAttribute("promptmsg", "用户批量禁用成功！");
		} else {
			model.addAttribute("promptmsg", "用户批量禁用成功,部分用户不符合条件,不做修改！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量初始化密码
	 * @param
	 * @date 2017-5-9 下午3:53:30
	 */
	@RequestMapping("batchinitpwd")
	public String batchinitpwd(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Manager manager = new Manager();
			manager.setPassword(Md5Util.getMD5(Constant.INIT_PASSWORD));
			manager.setMana_id(id);
			this.managerService.update(manager);
		}
		model.addAttribute("promptmsg", "用户批量初始化密码成功！");
		return list(request, model);
	}
}

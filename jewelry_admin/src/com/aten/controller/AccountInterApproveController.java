/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-20 16:31:02  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: AccountInterApproveController.java 
 * Author:hx
 */
package com.aten.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aten.annotation.TokenAnnotation;
import com.aten.model.orm.Account;
import com.aten.model.orm.AccountInterApprove;
import com.aten.model.orm.Manager;
import com.aten.model.orm.Role;
import com.aten.service.AccountInterApproveService;
import com.aten.service.AccountService;
import com.aten.service.ManagerService;
import com.aten.service.RoleService;
import com.communal.constants.Constant;
import com.communal.util.RandomCharUtil;
import com.communal.util.StringUtil;

/**
 * @author hx
 * @Function 积分调账类 controller
 * @date 2017-07-20 16:31:02
 */
@Controller
@RequestMapping("admin/accountInterApprove")
public class AccountInterApproveController extends BaseController {

	private static final Logger logger = Logger.getLogger(AccountInterApproveController.class);

	@Autowired
	private AccountInterApproveService accountInterApproveService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AccountService accountService;

	/**
	 * @author hx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author hx
	 * @Description 积分调账列表页方法
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.accountInterApproveService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<AccountInterApprove> accountInterApproveList = this.accountInterApproveService.getList(paraMap);
		model.addAttribute("accountInterApproveList", accountInterApproveList);
		return "accountInterApprove/list";
	}

	/**
	 * @author hx
	 * @Description 跳转到添加积分调账页面方法
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		return "accountInterApprove/insert";
	}

	/**
	 * @author hx
	 * @Description 添加积分调账方法
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(HttpServletRequest request, AccountInterApprove accountInterApprove, Model model) {
		// 判断扣减时余额积分够不够
		Account ac = accountService.getByLoginname(accountInterApprove.getLogin_name());
		if (ac == null) {
			model.addAttribute("promptmsg", "会员帐号不存在");
			return add(model);
		}
		if (accountInterApprove.getIo_type().equals("1")) {
			BigDecimal oldinter = new BigDecimal(ac.getIntegral());
			BigDecimal inter = new BigDecimal(accountInterApprove.getInter_value());
			// 判断大小
			if (oldinter.compareTo(inter) < 0) {
				model.addAttribute("promptmsg", "调账积分超出会员剩余积分值，请重新申请！");
				return list(request, model);
			}
		}
		accountInterApprove.setAccount_id(ac.getId() + "");
		// 审核单号
		String approve_num = "sp" + RandomCharUtil.randOrderNumber();
		accountInterApprove.setApprove_num(approve_num);
		String submitter_id = request.getSession().getAttribute(Constant.USER_ID).toString();
		String submitter_name = request.getSession().getAttribute(Constant.USER_NAME).toString();
		accountInterApprove.setSubmitter_id(submitter_id);
		accountInterApprove.setSubmitter_name(submitter_name);
		// 操作人员
		Manager manager = this.managerService.get(submitter_id);
		Role role = this.roleService.get(manager.getRole_code());
		accountInterApprove.setSubmitter_rolename(role.getRole_name());
		// 待审核状态
		accountInterApprove.setAudit_state("0");
		// 数据插入
		this.accountInterApproveService.insert(accountInterApprove);
		model.addAttribute("promptmsg", "积分调账申请成功！");
		return goUrl("accountInterApprove/list");
	}

	@RequestMapping("approve")
	@TokenAnnotation(needSaveToken = true)
	public String approve(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		AccountInterApprove aia = this.accountInterApproveService.get(parameter_id);
		aia.setCreate_time(StringUtil.getStandDate(aia.getCreate_time()));
		model.addAttribute("accountInterApprove", aia);
		Account account = accountService.getByLoginname(aia.getLogin_name());
		model.addAttribute("account", account);
		return "accountInterApprove/approve";
	}

	@RequestMapping("view")
	@TokenAnnotation(needSaveToken = true)
	public String view(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		AccountInterApprove aia = this.accountInterApproveService.get(parameter_id);
		aia.setCreate_time(StringUtil.getStandDate(aia.getCreate_time()));
		if (aia.getApprove_time() != null && !aia.getApprove_time().equals("")) {
			aia.setApprove_time(StringUtil.getStandDate(aia.getApprove_time()));
		}
		model.addAttribute("accountInterApprove", aia);
		Account account = accountService.getByLoginname(aia.getLogin_name());
		model.addAttribute("account", account);
		return "accountInterApprove/view";
	}

	/**
	 * @author hx
	 * @Description 修改积分调账方法
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, AccountInterApprove accountInterApprove, Model model) {
		AccountInterApprove accountInterApproveOld = this.accountInterApproveService
				.get(accountInterApprove.getIa_id());
		accountInterApprove.setInter_value(accountInterApproveOld.getInter_value());
		accountInterApprove.setLogin_name(accountInterApproveOld.getLogin_name());
		accountInterApprove.setAccount_id(accountInterApproveOld.getAccount_id());
		accountInterApprove.setSubmitter_note(accountInterApproveOld.getSubmitter_note());
		String approve_mana_id = request.getSession().getAttribute(Constant.USER_ID).toString();
		String approve_mana_name = request.getSession().getAttribute(Constant.USER_NAME).toString();
		accountInterApprove.setApprove_mana_id(approve_mana_id);
		accountInterApprove.setApprove_mana_name(approve_mana_name);

		Manager manager = this.managerService.get(approve_mana_id);
		Role role = this.roleService.get(manager.getRole_code());
		accountInterApprove.setApprove_rolename(role.getRole_name());

		this.accountInterApproveService.approve(accountInterApprove);
		model.addAttribute("promptmsg", "已审核！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 删除积分调账方法
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.accountInterApproveService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "积分调账删除成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 积分调账排序方法
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.accountInterApproveService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 积分调账批量删除成功
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		boolean flag = false;
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			if (!StringUtil.isEmpty(id)) {
				if (checkBrand(id)) {
					flag = true;
				} else {
					this.accountInterApproveService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分积分调账已被引用,被引用的积分调账删除失败！");
		} else {
			model.addAttribute("promptmsg", "积分调账删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 启用积分调账
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		AccountInterApprove accountInterApprove = this.accountInterApproveService.get(parameter_id);
		// accountInterApprove.setState("1");
		this.accountInterApproveService.update(accountInterApprove);
		model.addAttribute("promptmsg", "积分调账启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 禁用积分调账
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		AccountInterApprove accountInterApprove = this.accountInterApproveService.get(parameter_id);
		// accountInterApprove.setState("0");
		this.accountInterApproveService.update(accountInterApprove);
		model.addAttribute("promptmsg", "积分调账禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量启用积分调账
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			AccountInterApprove accountInterApprove = this.accountInterApproveService.get(id);
			// accountInterApprove.setState("1");
			this.accountInterApproveService.update(accountInterApprove);
		}
		model.addAttribute("promptmsg", "积分调账批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量禁用积分调账
	 * @param
	 * @date 2017-07-20 16:31:02
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			AccountInterApprove accountInterApprove = this.accountInterApproveService.get(id);
			// accountInterApprove.setState("0");
			this.accountInterApproveService.update(accountInterApprove);
		}
		model.addAttribute("promptmsg", "积分调账批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 验证删除积分调账时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String ia_id) {
		return false;
	}

}

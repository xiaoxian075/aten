/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-20 16:29:41  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: AccountBalanceApproveController.java 
 * Author:hx
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.communal.constants.Constant;
import com.communal.util.AmountUtil;
import com.communal.util.IpUtil;
import com.communal.util.RandomCharUtil;
import com.communal.util.StringUtil;
import com.aten.model.orm.Account;
import com.aten.model.orm.AccountBalanceApprove;
import com.aten.model.orm.Manager;
import com.aten.model.orm.Role;
import com.aten.service.AccountBalanceApproveService;
import com.aten.service.AccountService;
import com.aten.service.ManagerService;
import com.aten.service.RoleService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hx
 * @Function 余额调账类 controller
 * @date 2017-07-20 16:29:41
 */
@Controller
@RequestMapping("admin/accountBalanceApprove")
public class AccountBalanceApproveController extends BaseController {

	private static final Logger logger = Logger.getLogger(AccountBalanceApproveController.class);

	@Autowired
	private AccountBalanceApproveService accountBalanceApproveService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private RoleService roleService;

	/**
	 * @author hx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author hx
	 * @Description 会员信息查询
	 * @param
	 * @date 2017-07-12 21:13:13
	 */
	@RequestMapping("getAccountMsg")
	public String getAccountMsg(HttpServletRequest request, Model model) {
		return "accountBalanceApprove/getAccountMsg";
	}

	/**
	 * @author hx
	 * @Description 余额调账列表页方法
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.accountBalanceApproveService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<AccountBalanceApprove> accountBalanceApproveList = this.accountBalanceApproveService.getList(paraMap);
		for (AccountBalanceApprove aba : accountBalanceApproveList) {
			aba.setApprove_amount(AmountUtil.fenToYuan(aba.getApprove_amount()));
		}
		model.addAttribute("accountBalanceApproveList", accountBalanceApproveList);
		return "accountBalanceApprove/list";
	}

	/**
	 * @author hx
	 * @Description 跳转到添加余额调账页面方法
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("recharge")
	@TokenAnnotation(needSaveToken = true)
	public String recharge(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Account account = accountService.getByLoginname(parameter_id);
		String balance = AmountUtil.fenToYuan(account.getBalance());
		model.addAttribute("account", account);
		model.addAttribute("act", 2);
		model.addAttribute("balance", balance);
		return "accountBalanceApprove/insert";
	}

	/**
	 * @author hx
	 * @Description 跳转到添加余额调账页面方法
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("deduction")
	@TokenAnnotation(needSaveToken = true)
	public String deduction(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Account account = accountService.getByLoginname(parameter_id);
		String balance = AmountUtil.fenToYuan(account.getBalance());
		model.addAttribute("account", account);
		model.addAttribute("act", 1);
		model.addAttribute("balance", balance);
		return "accountBalanceApprove/insert";
	}

	/**
	 * @author hx
	 * @Description 添加余额调账方法
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(HttpServletRequest request, AccountBalanceApprove accountBalanceApprove, Model model) {
		if ("1".equals(accountBalanceApprove.getIo_type())) {
			Account account = accountService.getById(new BigInteger(accountBalanceApprove.getAccount_id()));
			BigDecimal oldbalance = new BigDecimal(account.getBalance());
			BigDecimal balance = new BigDecimal(AmountUtil.yuanToFen(accountBalanceApprove.getApprove_amount()));

			if (oldbalance.compareTo(balance) < 0) {
				model.addAttribute("promptmsg", "调账金额超出会员余额，请重新申请！");
				return list(request, model);
			}
		}
		String approve_num = "sp" + RandomCharUtil.randOrderNumber();
		accountBalanceApprove.setApprove_num(approve_num);
		String submitter_id = request.getSession().getAttribute(Constant.USER_ID).toString();
		String submitter_name = request.getSession().getAttribute(Constant.USER_NAME).toString();
		accountBalanceApprove.setSubmitter_id(submitter_id);
		accountBalanceApprove.setSubmitter_name(submitter_name);

		Manager manager = this.managerService.get(submitter_id);
		Role role = this.roleService.get(manager.getRole_code());
		accountBalanceApprove.setSubmitter_rolename(role.getRole_name());

		accountBalanceApprove.setApprove_amount(AmountUtil.yuanToFen(accountBalanceApprove.getApprove_amount()));
		accountBalanceApprove.setAudit_state("0");

		this.accountBalanceApproveService.insert(accountBalanceApprove);
		model.addAttribute("promptmsg", "会员调账申请成功！");
		return goUrl("accountBalanceApprove/list");
	}

	@RequestMapping("approve")
	@TokenAnnotation(needSaveToken = true)
	public String approve(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		AccountBalanceApprove aba = this.accountBalanceApproveService.get(parameter_id);
		aba.setApprove_amount(AmountUtil.fenToYuan(aba.getApprove_amount()));
		aba.setCreate_time(StringUtil.getStandDate(aba.getCreate_time()));
		model.addAttribute("accountBalanceApprove", aba);
		Account account = accountService.getByLoginname(aba.getLogin_name());
		String balance = AmountUtil.fenToYuan(account.getBalance());
		model.addAttribute("account", account);
		model.addAttribute("balance", balance);
		return "accountBalanceApprove/approve";
	}

	@RequestMapping("view")
	@TokenAnnotation(needSaveToken = true)
	public String view(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		AccountBalanceApprove aba = this.accountBalanceApproveService.get(parameter_id);
		aba.setApprove_amount(AmountUtil.fenToYuan(aba.getApprove_amount()));
		aba.setCreate_time(StringUtil.getStandDate(aba.getCreate_time()));
		if (aba.getApprove_time() != null && !aba.getApprove_time().equals("")) {
			aba.setApprove_time(StringUtil.getStandDate(aba.getApprove_time()));
		}
		model.addAttribute("accountBalanceApprove", aba);
		Account account = accountService.getByLoginname(aba.getLogin_name());
		String balance = AmountUtil.fenToYuan(account.getBalance());
		model.addAttribute("account", account);
		model.addAttribute("balance", balance);
		return "accountBalanceApprove/view";
	}

	/**
	 * @author hx
	 * @Description 修改余额调账方法
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, AccountBalanceApprove accountBalanceApprove, Model model) {
		AccountBalanceApprove accountBalanceApproveOld = this.accountBalanceApproveService
				.get(accountBalanceApprove.getBa_id());
		accountBalanceApprove.setApprove_amount(accountBalanceApproveOld.getApprove_amount());
		accountBalanceApprove.setLogin_name(accountBalanceApproveOld.getLogin_name());
		accountBalanceApprove.setAccount_id(accountBalanceApproveOld.getAccount_id());
		accountBalanceApprove.setSubmitter_note(accountBalanceApproveOld.getSubmitter_note());
		String approve_mana_id = request.getSession().getAttribute(Constant.USER_ID).toString();
		String approve_mana_name = request.getSession().getAttribute(Constant.USER_NAME).toString();
		accountBalanceApprove.setApprove_mana_id(approve_mana_id);
		accountBalanceApprove.setApprove_mana_name(approve_mana_name);

		Manager manager = this.managerService.get(approve_mana_id);
		Role role = this.roleService.get(manager.getRole_code());
		accountBalanceApprove.setApprove_rolename(role.getRole_name());

		this.accountBalanceApproveService.approve(accountBalanceApprove);
		model.addAttribute("promptmsg", "已审核！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 删除余额调账方法
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.accountBalanceApproveService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "余额调账删除成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 余额调账排序方法
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.accountBalanceApproveService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 余额调账批量删除成功
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
					this.accountBalanceApproveService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分余额调账已被引用,被引用的余额调账删除失败！");
		} else {
			model.addAttribute("promptmsg", "余额调账删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 启用余额调账
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		AccountBalanceApprove accountBalanceApprove = this.accountBalanceApproveService.get(parameter_id);
		// accountBalanceApprove.setState("1");
		this.accountBalanceApproveService.update(accountBalanceApprove);
		model.addAttribute("promptmsg", "余额调账启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 禁用余额调账
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		AccountBalanceApprove accountBalanceApprove = this.accountBalanceApproveService.get(parameter_id);
		// accountBalanceApprove.setState("0");
		this.accountBalanceApproveService.update(accountBalanceApprove);
		model.addAttribute("promptmsg", "余额调账禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量启用余额调账
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			AccountBalanceApprove accountBalanceApprove = this.accountBalanceApproveService.get(id);
			// accountBalanceApprove.setState("1");
			this.accountBalanceApproveService.update(accountBalanceApprove);
		}
		model.addAttribute("promptmsg", "余额调账批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量禁用余额调账
	 * @param
	 * @date 2017-07-20 16:29:41
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			AccountBalanceApprove accountBalanceApprove = this.accountBalanceApproveService.get(id);
			// accountBalanceApprove.setState("0");
			this.accountBalanceApproveService.update(accountBalanceApprove);
		}
		model.addAttribute("promptmsg", "余额调账批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 验证删除余额调账时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String ba_id) {
		return false;
	}

}

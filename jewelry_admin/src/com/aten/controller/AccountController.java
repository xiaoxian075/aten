package com.aten.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aten.function.SysconfigFuc;
import com.aten.model.orm.Account;
import com.aten.model.orm.AccountBill;
import com.aten.service.AccountBillService;
import com.aten.service.AccountService;
import com.communal.node.ReqMsg;
import com.communal.util.AmountUtil;
import com.communal.util.StringUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("admin/user")
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountBillService accountBillService;

	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		return "user/list";
	}

	@RequestMapping(value = "get", produces = "application/json;charset=UTF-8;")
	@ResponseBody
	public String get(String login_name) {
		ReqMsg<Account> reqMsg = null;
		Account account = accountService.getByLoginname(login_name);
		if (account != null) {
			if (account.getTotal_earnings() == null) {
				account.setTotal_earnings(new BigInteger("0"));
			}
			account.setCreate_time(StringUtil.getStandDate(account.getCreate_time()));
			account.setLast_time(StringUtil.getStandDate(account.getLast_time()));
			if (account.getHead_pic() == null || account.getHead_pic().equals("")) {
				account.setHead_pic(SysconfigFuc.getSysValue("cfg_default_headerImg"));
			}
			reqMsg = new ReqMsg<Account>(0, "succ", account);
		} else {
			reqMsg = new ReqMsg<Account>(0, "succ", null);
		}
		return new Gson().toJson(reqMsg);
	}

	@RequestMapping("upList")
	public String upList(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("account_id", parameter_id);
		paraMap.put("bill_type", "5");

		List<Map<String, Object>> billList = new ArrayList<Map<String, Object>>();

		List<AccountBill> bList = accountBillService.getList(paraMap);
		for (AccountBill ab : bList) {
			Map<String, Object> m = new HashMap<String, Object>();
			Account account = accountService.getById(ab.getAccount_id());
			m.put("account", account.getLogin_name());
			m.put("billTime", StringUtil.getStandDate(ab.getCreate_time().toString()));
			m.put("ioType", "付费升级");
			m.put("amount", "￥" + AmountUtil.fenToYuan(ab.getAmount()));
			billList.add(m);
		}
		model.addAttribute("billList", billList);
		return "user/upList";
	}
}

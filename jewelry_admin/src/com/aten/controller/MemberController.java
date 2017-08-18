/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-11 18:25:41  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: MemberController.java 
 * Author:chenyi
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
import com.communal.util.AmountUtil;
import com.communal.util.StringUtil;
import com.aten.model.orm.AccountBill;
import com.aten.model.orm.Member;
import com.aten.service.AccountBillService;
import com.aten.service.MemberService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyi
 * @Function 会员类 controller
 * @date 2017-08-11 18:25:41
 */
@Controller
@RequestMapping("admin/member")
public class MemberController extends BaseController {

	private static final Logger logger = Logger.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;
	@Autowired
	private AccountBillService accountBillService;

	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 会员列表页方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.memberService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Member> memberList = this.memberService.getList(paraMap);
		for (Member m : memberList) {
			if (m.getNick_name() == null || m.getNick_name().equals("")) {
				m.setNick_name("-");
			}
			if ("1".equals(m.getSex())) {
				m.setSex("男");
			} else if ("2".equals(m.getSex())) {
				m.setSex("女");
			} else {
				m.setSex("保密");
			}
			if ("1".equals(m.getLev())) {
				m.setLev("普通");
			} else if ("2".equals(m.getLev())) {
				m.setLev("vip");
			}
			m.setBalance(AmountUtil.fenToYuan(m.getBalance()));
			m.setEarnings(AmountUtil.fenToYuan(m.getEarnings()));
			m.setTotal_earnings(AmountUtil.fenToYuan(m.getTotal_earnings()));
			m.setLast_time(StringUtil.getStandDate(m.getLast_time()));
		}
		model.addAttribute("memberList", memberList);
		return "member/list";
	}

	/**
	 * @author chenyi
	 * @Description 跳转到添加会员页面方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("listBill")
	public String listBill(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);

		model.addAttribute("parameter_id", parameter_id);
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("account_id", parameter_id);
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.accountBillService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<AccountBill> accountBillList = this.accountBillService.getList(paraMap);
		for (AccountBill ab : accountBillList) {
			if (ab.getPay_way() == 1) {
				ab.setPay_way_str("余额");
			} else if (ab.getPay_way() == 2) {
				ab.setPay_way_str("云付通");
			}
			ab.setAmount_str(AmountUtil.fenToYuan(ab.getAmount()));
			if (ab.getIo_type() == 1) {
				ab.setIo_type_str("支出");
			} else if (ab.getIo_type() == 2) {
				ab.setIo_type_str("收入");
			}

			ab.setCreate_time_str(StringUtil.getStandDate(ab.getCreate_time().toString()));
		}
		model.addAttribute("accountBillList", accountBillList);
		return "member/listBill";
	}

	/**
	 * @author chenyi
	 * @Description 跳转到添加会员页面方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		return "member/insert";
	}

	/**
	 * @author chenyi
	 * @Description 添加会员方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Member member, Model model) {
		this.memberService.insert(member);
		model.addAttribute("promptmsg", "会员添加成功！");
		return add(model);
	}

	/**
	 * @author chenyi
	 * @Description 跳转到修改会员页面方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Member member = this.memberService.get(parameter_id);
		model.addAttribute("member", member);
		return "member/update";
	}

	/**
	 * @author chenyi
	 * @Description 修改会员方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, Member member, Model model) {
		this.memberService.update(member);
		model.addAttribute("promptmsg", "会员修改成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 删除会员方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.memberService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "会员删除成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 会员排序方法
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.memberService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 会员批量删除成功
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
					this.memberService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分会员已被引用,被引用的会员删除失败！");
		} else {
			model.addAttribute("promptmsg", "会员删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 启用会员
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Member member = this.memberService.get(parameter_id);
		this.memberService.update(member);
		model.addAttribute("promptmsg", "会员启用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 禁用会员
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Member member = this.memberService.get(parameter_id);
		this.memberService.update(member);
		model.addAttribute("promptmsg", "会员禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用会员
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Member member = this.memberService.get(id);
			this.memberService.update(member);
		}
		model.addAttribute("promptmsg", "会员批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量禁用会员
	 * @param
	 * @date 2017-08-11 18:25:41
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Member member = this.memberService.get(id);
			this.memberService.update(member);
		}
		model.addAttribute("promptmsg", "会员批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 验证删除会员时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String id) {
		return false;
	}

}

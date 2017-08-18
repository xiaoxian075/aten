/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-30 17:25:39  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: FeedbackController.java 
 * Author:chenyi
 */
package com.aten.controller;

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.aten.model.orm.Feedback;
import com.aten.service.AccountService;
import com.aten.service.FeedbackService;
import com.communal.util.StringUtil;

/**
 * @author chenyi
 * @Function 意见反馈类 controller
 * @date 2017-07-30 17:25:39
 */
@Controller
@RequestMapping("admin/feedback")
public class FeedbackController extends BaseController {

	private static final Logger logger = Logger.getLogger(FeedbackController.class);

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private AccountService accountService;

	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 意见反馈列表页方法
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.feedbackService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Feedback> feedbackList = new ArrayList<Feedback>();
		List<Feedback> feedbackLists = this.feedbackService.getList(paraMap);
		for (int i = 0; i < feedbackLists.size(); i++) {
			Feedback fb = feedbackLists.get(i);
			Account ac = accountService.getById(new BigInteger(fb.getAccount_id()));
			if (ac != null) {
				fb.setAccount_id(ac.getLogin_name());
				if (ac.getNick_name() == null) {
					fb.setNick_name("-");
				} else {
					fb.setNick_name(ac.getNick_name());
				}
				fb.setFb_time(StringUtil.getStandDate(fb.getFb_time()));
			} else {
				fb.setAccount_id("-");
				fb.setNick_name("-");
				fb.setFb_time(StringUtil.getStandDate(fb.getFb_time()));
			}
			feedbackList.add(i, fb);
		}
		model.addAttribute("feedbackList", feedbackList);
		return "feedback/list";
	}

	/**
	 * @author chenyi
	 * @Description 跳转到添加意见反馈页面方法
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		return "feedback/insert";
	}

	/**
	 * @author chenyi
	 * @Description 添加意见反馈方法
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Feedback feedback, Model model) {
		this.feedbackService.insert(feedback);
		model.addAttribute("promptmsg", "意见反馈添加成功！");
		return add(model);
	}

	/**
	 * @author chenyi
	 * @Description 跳转到修改意见反馈页面方法
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Feedback feedback = this.feedbackService.get(parameter_id);
		model.addAttribute("feedback", feedback);
		return "feedback/update";
	}

	/**
	 * @author chenyi
	 * @Description 修改意见反馈方法
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, Feedback feedback, Model model) {
		this.feedbackService.update(feedback);
		model.addAttribute("promptmsg", "意见反馈修改成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 删除意见反馈方法
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.feedbackService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "意见反馈删除成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 意见反馈排序方法
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.feedbackService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 意见反馈批量删除成功
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
					this.feedbackService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分意见反馈已被引用,被引用的意见反馈删除失败！");
		} else {
			model.addAttribute("promptmsg", "意见反馈删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 启用意见反馈
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Feedback feedback = this.feedbackService.get(parameter_id);
		this.feedbackService.update(feedback);
		model.addAttribute("promptmsg", "意见反馈启用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 禁用意见反馈
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Feedback feedback = this.feedbackService.get(parameter_id);
		this.feedbackService.update(feedback);
		model.addAttribute("promptmsg", "意见反馈禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用意见反馈
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Feedback feedback = this.feedbackService.get(id);
			this.feedbackService.update(feedback);
		}
		model.addAttribute("promptmsg", "意见反馈批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量禁用意见反馈
	 * @param
	 * @date 2017-07-30 17:25:39
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Feedback feedback = this.feedbackService.get(id);
			this.feedbackService.update(feedback);
		}
		model.addAttribute("promptmsg", "意见反馈批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 验证删除意见反馈时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String fd_id) {
		return false;
	}

}

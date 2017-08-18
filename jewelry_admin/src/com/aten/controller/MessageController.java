/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-17 10:12:14  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: MessageController.java 
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
import com.communal.util.StringUtil;
import com.aten.model.orm.Manager;
import com.aten.model.orm.Message;
import com.aten.service.ManagerService;
import com.aten.service.MessageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hx
 * @Function 消息类 controller
 * @date 2017-07-17 10:12:14
 */
@Controller
@RequestMapping("admin/message")
public class MessageController extends BaseController {

	private static final Logger logger = Logger.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;
	/**
	 * @author hx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author hx
	 * @Description 消息列表页方法
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.messageService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Message> messageList = this.messageService.getList(paraMap);
		for (Message message : messageList) {
			message.setIn_date(StringUtil.getStandDate(message.getIn_date()));
		}
		model.addAttribute("messageList", messageList);
		return "message/list";
	}

	/**
	 * @author hx
	 * @Description 跳转到添加消息页面方法
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		return "message/insert";
	}

	/**
	 * @author hx
	 * @Description 添加消息方法
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Message message, Model model) {
		this.messageService.insert(message);
		model.addAttribute("promptmsg", "消息添加成功！");
		return add(model);
	}

	/**
	 * @author hx
	 * @Description 跳转到修改消息页面方法
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Message message = this.messageService.get(parameter_id);
		message.setIn_date(StringUtil.getStandDate(message.getIn_date()));
		model.addAttribute("message", message);
		return "message/update";
	}

	/**
	 * @author hx
	 * @Description 修改消息方法
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, Message message, Model model) {
		this.messageService.update(message);
		model.addAttribute("promptmsg", "消息修改成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 删除消息方法
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.messageService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "消息删除成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 消息排序方法
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.messageService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 消息批量删除成功
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
					this.messageService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分消息已被引用,被引用的消息删除失败！");
		} else {
			model.addAttribute("promptmsg", "消息删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 启用消息
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Message message = this.messageService.get(parameter_id);
		// message.setState("1");
		this.messageService.update(message);
		model.addAttribute("promptmsg", "消息启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 禁用消息
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Message message = this.messageService.get(parameter_id);
		// message.setState("0");
		this.messageService.update(message);
		model.addAttribute("promptmsg", "消息禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量启用消息
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Message message = this.messageService.get(id);
			// message.setState("1");
			this.messageService.update(message);
		}
		model.addAttribute("promptmsg", "消息批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量禁用消息
	 * @param
	 * @date 2017-07-17 10:12:14
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Message message = this.messageService.get(id);
			// message.setState("0");
			this.messageService.update(message);
		}
		model.addAttribute("promptmsg", "消息批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 验证删除消息时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String msg_id) {
		return false;
	}

}

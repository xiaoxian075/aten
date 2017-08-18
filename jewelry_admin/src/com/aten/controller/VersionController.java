/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-01 15:05:11  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: VersionController.java 
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
import com.communal.util.StringUtil;
import com.aten.model.orm.Version;
import com.aten.service.VersionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyi
 * @Function 版本更新类 controller
 * @date 2017-08-01 15:05:11
 */
@Controller
@RequestMapping("admin/version")
public class VersionController extends BaseController {

	private static final Logger logger = Logger.getLogger(VersionController.class);

	@Autowired
	private VersionService versionService;

	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 版本更新列表页方法
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.versionService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Version> versionList = this.versionService.getList(paraMap);
		for (Version vs : versionList) {
			vs.setUpdate_time(StringUtil.getStandDate(vs.getUpdate_time()));
		}
		model.addAttribute("versionList", versionList);
		return "version/list";
	}

	/**
	 * @author chenyi
	 * @Description 跳转到添加版本更新页面方法
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		return "version/insert";
	}

	/**
	 * @author chenyi
	 * @Description 添加版本更新方法
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Version version, Model model) {
		this.versionService.insert(version);
		model.addAttribute("promptmsg", "版本更新添加成功！");
		return add(model);
	}

	/**
	 * @author chenyi
	 * @Description 跳转到修改版本更新页面方法
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		model.addAttribute("is_update", "1");
		// 获取对象
		Version version = this.versionService.get(parameter_id);
		version.setUpdate_time(StringUtil.getStandDate(version.getUpdate_time()));
		model.addAttribute("version", version);
		getPolicyList(model, version.getUpdate_logs());
		return "version/update";
	}

	private void getPolicyList(Model model, String policy) {
		String pos = "###";
		String[] policyArray = null;
		if (policy != null) {
			policyArray = policy.split(pos);
		}
		model.addAttribute("policyArray", policyArray);
	}

	/**
	 * @author chenyi
	 * @Description 修改版本更新方法
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, Version version, Model model) {
		this.versionService.update(version);
		model.addAttribute("promptmsg", "版本更新修改成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 删除版本更新方法
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.versionService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "版本更新删除成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 版本更新排序方法
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.versionService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 版本更新批量删除成功
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
					this.versionService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分版本更新已被引用,被引用的版本更新删除失败！");
		} else {
			model.addAttribute("promptmsg", "版本更新删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 启用版本更新
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Version version = this.versionService.get(parameter_id);
		this.versionService.update(version);
		model.addAttribute("promptmsg", "版本更新启用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 禁用版本更新
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Version version = this.versionService.get(parameter_id);
		this.versionService.update(version);
		model.addAttribute("promptmsg", "版本更新禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用版本更新
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Version version = this.versionService.get(id);
			this.versionService.update(version);
		}
		model.addAttribute("promptmsg", "版本更新批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量禁用版本更新
	 * @param
	 * @date 2017-08-01 15:05:11
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Version version = this.versionService.get(id);
			this.versionService.update(version);
		}
		model.addAttribute("promptmsg", "版本更新批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 验证删除版本更新时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String version_id) {
		return false;
	}

}

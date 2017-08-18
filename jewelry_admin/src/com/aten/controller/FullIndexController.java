/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-17 10:21:42  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: FullIndexController.java 
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
import com.aten.client.RedisClient;
import com.communal.constants.Constant;
import com.communal.constants.EsContant;
import com.communal.constants.RedisConstants;
import com.communal.util.DateUtil;
import com.communal.util.StringUtil;
import com.es.CatAttrEsIndex;
import com.es.EsClient;
import com.es.GoodsEsIndex;
import com.aten.model.orm.FullIndex;
import com.aten.model.orm.Manager;
import com.aten.service.FullIndexService;
import com.aten.service.ManagerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hx
 * @Function 索引全量更新记录类 controller
 * @date 2017-07-17 10:21:42
 */
@Controller
@RequestMapping("admin/fullIndex")
public class FullIndexController extends BaseController {

	private static final Logger logger = Logger.getLogger(FullIndexController.class);

	private static boolean isGoodsIndexUpdate = false;

	private static boolean isCatattrIndexUpdate = false;

	@Autowired
	private FullIndexService fullIndexService;
	@Autowired
	private ManagerService managerService;

	/**
	 * @author hx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author hx
	 * @Description 索引全量更新记录列表页方法
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.fullIndexService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<FullIndex> fullIndexList = this.fullIndexService.getList(paraMap);
		for (FullIndex fi : fullIndexList) {
			fi.setOper_time(StringUtil.getStandDate(fi.getOper_time()));
			Manager manager = this.managerService.get(fi.getOper_man());
			fi.setOper_man(manager.getMana_name());
		}
		model.addAttribute("fullIndexList", fullIndexList);
		return "fullIndex/list";
	}

	/**
	 * @author hx
	 * @Description 跳转到添加索引全量更新记录页面方法
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		return "fullIndex/insert";
	}

	/**
	 * @author hx
	 * @Description 添加索引全量更新记录方法
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(FullIndex fullIndex, Model model) {
		this.fullIndexService.insert(fullIndex);
		model.addAttribute("promptmsg", "索引全量更新记录添加成功！");
		return add(model);
	}

	/**
	 * @author hx
	 * @Description 跳转到修改索引全量更新记录页面方法
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		FullIndex fullIndex = this.fullIndexService.get(parameter_id);
		model.addAttribute("fullIndex", fullIndex);
		return "fullIndex/update";
	}

	/**
	 * @author hx
	 * @Description 修改索引全量更新记录方法
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("updateUse")
	public String update(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.fullIndexService.updateUse(request, parameter_id);
		model.addAttribute("promptmsg", "索引全量更新记录修改成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 删除索引全量更新记录方法
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);

		FullIndex fullIndex = this.fullIndexService.get(parameter_id);
		if (fullIndex.getUse_version().equals("1")) {
			model.addAttribute("promptmsg", "索引全量使用中不可删除！");
			return list(request, model);
		}

		new EsClient().deleteEsIndex(fullIndex.getIndex_version());

		this.fullIndexService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "索引全量更新记录删除成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 索引全量更新记录排序方法
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.fullIndexService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 索引全量更新记录批量删除成功
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
					this.fullIndexService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分索引全量更新记录已被引用,被引用的索引全量更新记录删除失败！");
		} else {
			model.addAttribute("promptmsg", "索引全量更新记录删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 启用索引全量更新记录
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		FullIndex fullIndex = this.fullIndexService.get(parameter_id);
		// fullIndex.setState("1");
		this.fullIndexService.update(fullIndex);
		model.addAttribute("promptmsg", "索引全量更新记录启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 禁用索引全量更新记录
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		FullIndex fullIndex = this.fullIndexService.get(parameter_id);
		// fullIndex.setState("0");
		this.fullIndexService.update(fullIndex);
		model.addAttribute("promptmsg", "索引全量更新记录禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量启用索引全量更新记录
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			FullIndex fullIndex = this.fullIndexService.get(id);
			// fullIndex.setState("1");
			this.fullIndexService.update(fullIndex);
		}
		model.addAttribute("promptmsg", "索引全量更新记录批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量禁用索引全量更新记录
	 * @param
	 * @date 2017-07-17 10:21:42
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			FullIndex fullIndex = this.fullIndexService.get(id);
			// fullIndex.setState("0");
			this.fullIndexService.update(fullIndex);
		}
		model.addAttribute("promptmsg", "索引全量更新记录批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 验证删除索引全量更新记录时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String full_index_id) {
		return false;
	}

	
	@RequestMapping("createCatattrIndex")
	public String createCatattrIndex(HttpServletRequest request, Model model) {
		if (!isUpdateCatattrIndexProcess(request)) {
			model.addAttribute("promptmsg", "分类属性索引创建中......！");
		} else {
			model.addAttribute("promptmsg", "分类属性索引创建成功！");
		}
		return list(request, model);
	}

	
	private boolean isUpdateCatattrIndexProcess(HttpServletRequest request) {
		if (!isCatattrIndexUpdate) {
			isCatattrIndexUpdate = true;
			String version = EsContant.CATATTRINDEX + DateUtil.getYmdhmsFName();
			FullIndex fi = new FullIndex();
			fi.setModule(EsContant.CATATTRTYPE);
			fi.setIndex_version(version);
			fi.setOper_man(request.getSession().getAttribute(Constant.USER_ID).toString());
			fi.setUse_version("2");// 更新中
			fullIndexService.insertGetPk(fi);

			// 创建索引结构
			CatAttrEsIndex.createCatAttrMapping(version, EsContant.CATATTRTYPE);
			// 创建索引数据
			CatAttrEsIndex.createCatAttrIndex(version, EsContant.CATATTRTYPE);
			fi.setUse_version("0");
			fullIndexService.update(fi);
			isCatattrIndexUpdate = false;
		}
		return true;
	}

	@RequestMapping("createGoodsIndex")
	public String createGoodsIndex(HttpServletRequest request, Model model) {
		if (!isUpdateGoodsIndexProcess(request)) {
			model.addAttribute("promptmsg", "商品索引创建中......！");
		} else {
			model.addAttribute("promptmsg", "商品索引创建成功！");
		}
		return list(request, model);
	}

	private boolean isUpdateGoodsIndexProcess(HttpServletRequest request) {
		if (!isGoodsIndexUpdate) {
			isGoodsIndexUpdate = true;
			String version = EsContant.GOODSINDEX + DateUtil.getYmdhmsFName();
			FullIndex fi = new FullIndex();
			fi.setModule(EsContant.GOODS);
			fi.setIndex_version(version);
			fi.setOper_man(request.getSession().getAttribute(Constant.USER_ID).toString());
			fi.setUse_version("2");
			fullIndexService.insertGetPk(fi);

			// 创建索引结构
			GoodsEsIndex.createGoodsMapping(version, EsContant.GOODS);
			// 创建索引数据
			GoodsEsIndex.createGoodsIndex(version, EsContant.GOODS);

			fi.setUse_version("0");
			fullIndexService.update(fi);
			isGoodsIndexUpdate = false;
		}
		return true;
	}

}

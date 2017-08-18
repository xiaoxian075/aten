/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  Tue Jan 03 16:26:02 CST 2017  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: Cat.java 
 * Author:linjunqin
 */
package com.aten.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aten.function.CatFuc;
import com.aten.function.SysconfigFuc;
import com.aten.model.bean.TreeTableBean;
import com.aten.model.orm.Cat;
import com.aten.model.orm.News;
import com.aten.service.CatService;
import com.communal.constants.Constant;
import com.communal.util.ChineseToEnglishUtil;
import com.communal.util.JsonUtil;
import com.communal.util.RandomCharUtil;

@Controller
@RequestMapping("/admin/newscat")
public class NewscatController extends BaseController {

	private static final Logger logger = Logger.getLogger(NewscatController.class);

	@Autowired
	private CatService catService;

	/**
	 * @param
	 * @author linjunqin
	 * @Description 初始方法
	 * @date 2017-1-5 下午2:37:11
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
		// 默认的地区节点
		model.addAttribute("cfg_news_cat", SysconfigFuc.getSysValue("cfg_news_cat"));
	}

	/**
	 * @author linjunqin
	 * @Description 获取分类列表页
	 * @param
	 * @date 2017-1-3 下午4:27:50
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		paraMap.put("module", "news");
		int count = this.catService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Cat> newscatList = this.catService.getList(paraMap);
		for (Cat cat : newscatList) {
			cat.setParent_cat_id(CatFuc.getCatNameStr(cat.getLevel_cat(),22,Constant.POS));
		}
		model.addAttribute("newscatList", newscatList);
		return "newscat/list";
	}

	/**
	 * @author linjunqin
	 * @Description 获取系统分类列表数据json字符串返回
	 * @param
	 * @date 2017-1-3 下午4:28:32
	 */
	@RequestMapping("getChildList")
	public void getChildList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		String up_id = request.getParameter("up_id");
		if (up_id != null && !up_id.equals("")) {
			paraMap.put("parent_cat_id", up_id);
		}
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		// 获取菜单对象
		Cat cat = this.catService.get(up_id);
		jsonMap.put("title", cat.getCat_name());
		jsonMap.put("title_id", cat.getCat_id());
		// 返回子列表
		List<Cat> list = this.catService.getList(paraMap);
		jsonMap.put("list", list);
		String listJson = JsonUtil.map2json(jsonMap);
		outPrint(response, listJson);
	}

	/**
	 * @author linjunqin
	 * @Description 获取正常显示数据的分类
	 * @param
	 * @date 2017-1-10 上午10:43:33
	 */
	@RequestMapping("normalList")
	public void normalList(HttpServletRequest request, HttpServletResponse response) {
		// 上一级分类标识
		String parent_cat_id = request.getParameter("id");
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		if (parent_cat_id != null && !parent_cat_id.equals("")) {
			paraMap.put("parent_cat_id", parent_cat_id);
			List<Cat> list = this.catService.getList(paraMap);
			HashMap<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("list", list);
			String listJson = JsonUtil.map2json(jsonMap);
			outPrint(response, listJson);
		}
	}

	/**
	 * @author linjunqin
	 * @Description 跳转新增分类列表页
	 * @param
	 * @date 2017-1-3 下午4:29:59
	 */
	@RequestMapping("add")
	public String add(HttpServletRequest request, Model model) {
		return "newscat/insert";
	}

	/**
	 * @author linjunqin
	 * @Description 添加系统分类
	 * @param
	 * @date 2017-1-3 下午4:31:33
	 */
	@RequestMapping("insert")
	public String insert(HttpServletRequest request, Cat cat, Model model) {
		// 上级分类
		String parent_id = SysconfigFuc.getSysValue("cfg_news_cat");
		String parent_cat_id = cat.getParent_cat_id();
		if (!"".equals(parent_cat_id)) {
			parent_id = parent_cat_id.substring(parent_cat_id.length() - 10, parent_cat_id.length());
		}
		Cat parent_cat = catService.get(parent_id);
		// 随机生成十位字符做为标识
		String cat_id = RandomCharUtil.getNumberRand();
		// 算出子菜单的等级
		String child_level = String.valueOf(Integer.parseInt(parent_cat.getCat_level().trim()) + 1);
		String level_cat = parent_cat.getLevel_cat() + "," + cat_id;
		cat.setCat_id(cat_id);
		cat.setParent_cat_id(parent_id);
		cat.setEn_name("-");
		cat.setWord_index(ChineseToEnglishUtil.getPinYinHeadChar(cat.getCat_name()));
		cat.setLevel_cat(level_cat);
		cat.setCat_level(child_level);
		cat.setNote("-");
		cat.setModule("news");
		cat.setIs_sys("0");
		this.catService.insert(cat);
		model.addAttribute("promptmsg", "资讯分类添加成功！");
		return add(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到更新分类页面
	 * @param
	 * @date 2017-1-3 下午4:34:21
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取当前菜单的对象
		Cat newscat = this.catService.get(parameter_id);
		newscat.setParent_cat_id(CatFuc.getCatNameStr(newscat.getLevel_cat(),22,Constant.POS));
		model.addAttribute("newscat", newscat);
		model.addAttribute("isEdit", "true");
		return "newscat/update";
	}

	/**
	 * @author linjunqin
	 * @Description 更新分类页面
	 * @param
	 * @date 2017-1-3 下午4:35:53
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request, Cat cat, Model model) {
		// 算出子菜单的等级
		cat.setEn_name("-");
		cat.setWord_index(ChineseToEnglishUtil.getPinYinHeadChar(cat.getCat_name()));
		cat.setNote("-");
		cat.setModule("news");
		this.catService.update(cat);
		model.addAttribute("promptmsg", "资讯分类修改成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 删除分类信息
	 * @param
	 * @date 2017-1-3 下午4:37:13
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		String msg = this.catService.deleteNewsClass(parameter_id);
		model.addAttribute("promptmsg",msg);
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 分类批量排序
	 * @param
	 * @date 2017-1-3 下午4:37:47
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.catService.updateBatch(sortMapList);
		return list(request, model);
	}

	/* 禁用分类 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 禁用下级
		catService.limitState(parameter_id);
		model.addAttribute("promptmsg", "禁用成功！");
		return list(request, model);
	}

	/**
	 * @param
	 * @author linjunqin
	 * @Description 批量禁用
	 * @date 2017-5-3 下午4:08:03
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		catService.batchLimitState(parameter_id);
		model.addAttribute("promptmsg", "批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 启用
	 * @param
	 * @date 2017-7-4 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		catService.enableState(parameter_id);
		model.addAttribute("promptmsg", "启用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用
	 * @param
	 * @date 2017-7-4 下午4:08:03
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		catService.batchEnableState(parameter_id);
		model.addAttribute("promptmsg", "批量启用成功！");
		return list(request, model);
	}
}

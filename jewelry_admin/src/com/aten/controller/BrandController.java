/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-02 18:45:01  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: BrandController.java 
 * Author:linjunqin
 */
package com.aten.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.aten.annotation.TokenAnnotation;
import com.aten.function.SysconfigFuc;
import com.aten.model.orm.Brand;
import com.aten.model.orm.Goods;
import com.aten.service.BrandService;
import com.aten.service.GoodsService;
import com.communal.util.StringUtil;

/**
 * @author linjunqin
 * @Function 商品品牌功能模块 controller
 * @date 2017-07-02 18:45:01
 */
@Controller
@RequestMapping("admin/brand")
public class BrandController extends BaseController {

	private static final Logger logger = Logger.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;
	@Autowired
	private GoodsService goodsService;

	
	
	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 品牌列表页方法
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.brandService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Brand> brandList = this.brandService.getList(paraMap);
		model.addAttribute("brandList", brandList);
		return "brand/list";
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到添加品牌页面方法
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		return "brand/insert";
	}

	/**
	 * @author linjunqin
	 * @Description 添加品牌方法
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Brand brand, Model model,RedirectAttributesModelMap modelMap) {
		this.brandService.insert(brand);
		//model.addAttribute("promptmsg", "品牌添加成功！");
		//model.addAttribute("brand", null);
		modelMap.addFlashAttribute("promptmsg", "品牌添加成功！");
		//return add(model);
		return goUrl("brand/add");
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到修改品牌页面方法
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Brand brand = this.brandService.get(parameter_id);
		model.addAttribute("brand", brand);
		return "brand/update";
	}

	/**
	 * @author linjunqin
	 * @Description 修改品牌方法
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, Brand brand, Model model) {
		if (checkBrand(brand.getBrand_id()) && brand.getState().equals("0")) {
			model.addAttribute("promptmsg", "该品牌已被引用不可禁用！");
		} else {
			this.brandService.update(brand);
			model.addAttribute("promptmsg", "品牌修改成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 删除品牌方法
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		if (checkBrand(parameter_id)) {
			model.addAttribute("promptmsg", "该品牌已被引用不可删除！");
		} else {
			this.brandService.deleteOne(parameter_id);
			model.addAttribute("promptmsg", "品牌删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 品牌排序方法
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.brandService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 品牌批量删除成功
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
					this.brandService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分品牌已被引用,被引用的品牌删除失败！");
		} else {
			model.addAttribute("promptmsg", "品牌删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 启用品牌
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Brand brand = this.brandService.get(parameter_id);
		brand.setState("1");
		this.brandService.update(brand);
		model.addAttribute("promptmsg", "品牌启用成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 禁用品牌
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		if (checkBrand(parameter_id)) {
			model.addAttribute("promptmsg", "该品牌已被引用不可禁用！");
		} else {
			Brand brand = this.brandService.get(parameter_id);
			brand.setState("0");
			this.brandService.update(brand);
			model.addAttribute("promptmsg", "品牌禁用成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量启用品牌
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			Brand brand = this.brandService.get(id);
			brand.setState("1");
			this.brandService.update(brand);
		}
		model.addAttribute("promptmsg", "品牌批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量禁用品牌
	 * @param
	 * @date 2017-07-02 18:45:01
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		boolean flag = false;
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			if (checkBrand(id)) {
				flag = true;
			} else {
				Brand brand = this.brandService.get(id);
				brand.setState("0");
				this.brandService.update(brand);
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分品牌已被引用,被引用的品牌禁用失败！");
		} else {
			model.addAttribute("promptmsg", "品牌批量禁用成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 验证删除品牌时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String brand_id) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("brand_id", brand_id);
		paraMap.put("is_del", "1");
		List<Goods> glist = goodsService.getList(paraMap);
		if (glist.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}

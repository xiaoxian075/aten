
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.aten.function.AreaFuc;
import com.aten.model.orm.Goods;
import com.aten.model.orm.Supply;
import com.aten.service.GoodsService;
import com.aten.service.SupplyService;
import com.communal.util.DateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/supply")
public class SupplyController extends BaseController {

	private static final Logger logger = Logger.getLogger(SupplyController.class);

	@Autowired
	private SupplyService supplyService;
	@Autowired
	private GoodsService goodsService;

	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.supplyService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Supply> supplyList = this.supplyService.getList(paraMap);
		for (Supply supply : supplyList) {
			supply.setValid_time_start(supply.getValid_time_start().substring(0, 10));
			supply.setValid_time_end(supply.getValid_time_end().substring(0, 10));
		}
		model.addAttribute("supplyList", supplyList);
		return "supply/list";
	}

	@RequestMapping("add")
	public String add(Model model) {
		return "supply/insert";
	}

	/**
	 * 供应商插入
	 * 
	 * @param supply
	 * @param model
	 * @return
	 */
	@RequestMapping("insert")
	public String insert(Supply supply, Model model,RedirectAttributesModelMap modelMap) {
		// 判断供应商名称是否重复
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("supply_name", supply.getSupply_name());
		List<Supply> slist = supplyService.getList(paraMap);
		// 大于0说明名称已被使用数据库中有
		if (slist.size() > 0) {
			model.addAttribute("promptmsg", "此供应商名称已被使用！");
		} else {
			int re = DateUtil.compare_date(supply.getValid_time_start(), supply.getValid_time_end(), 2);
			if (re == 1) {
				model.addAttribute("promptmsg", "有效期开始时间大于结束时间！");
			} else {
				// 插入操作
				this.supplyService.insert(supply);
				//model.addAttribute("promptmsg", "供应商添加成功！");
				modelMap.addFlashAttribute("promptmsg", "供应商添加成功！");
			}
		}
		//return add(model);
		return goUrl("supply/add");
	}

	@RequestMapping("view")
	public String view(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Supply supply = this.supplyService.get(parameter_id);
		supply.setValid_time_start(supply.getValid_time_start().substring(0, 10));
		supply.setValid_time_end(supply.getValid_time_end().substring(0, 10));
		supply.setThe_area(AreaFuc.getAreaNameStr(supply.getThe_area()));
		model.addAttribute("supply", supply);
		return "supply/view";
	}

	@RequestMapping("edit")
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Supply supply = this.supplyService.get(parameter_id);
		supply.setValid_time_start(supply.getValid_time_start().substring(0, 10));
		supply.setValid_time_end(supply.getValid_time_end().substring(0, 10));
		model.addAttribute("supply", supply);
		return "supply/update";
	}

	@RequestMapping("update")
	public String update(HttpServletRequest request, Supply supply, Model model) {
		if (checkSupply(supply.getSupply_id()) && supply.getState().equals("0")) {
			model.addAttribute("promptmsg", "该供应商已被引用不可禁用！");
		} else {
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("not_supply_id", supply.getSupply_id());
			paraMap.put("supply_name", supply.getSupply_name());
			List<Supply> slist = supplyService.getList(paraMap);
			if (slist.size() > 0) {
				model.addAttribute("promptmsg", "此供应商名称已被使用！");
			} else {
				int re = DateUtil.compare_date(supply.getValid_time_start(), supply.getValid_time_end(), 2);
				if (re == 1) {
					model.addAttribute("promptmsg", "有效期开始时间大于结束时间！");
				} else {
					this.supplyService.update(supply);
					model.addAttribute("promptmsg", "供应商修改成功！");
				}
			}
		}
		return list(request, model);
	}

	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		if (checkSupply(parameter_id)) {
			model.addAttribute("promptmsg", "该供应商已被引用不可删除！");
		} else {
			this.supplyService.deleteOne(parameter_id);
			model.addAttribute("promptmsg", "供应商删除成功！");
		}
		return list(request, model);
	}

	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.supplyService.updateBatch(sortMapList);
		return list(request, model);
	}

	@RequestMapping("editState")
	public String editState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		Supply supply = this.supplyService.get(parameter_id);
		if (supply == null)
			return list(request, model);
		if (checkSupply(parameter_id) && supply.getState().equals("1")) {
			model.addAttribute("promptmsg", "该供应商已被引用不可禁用！");
		} else {
			if (supply.getState().equals("1")) {
				supply.setSupply_id(parameter_id);
				supply.setState("0");
				this.supplyService.update(supply);
				model.addAttribute("promptmsg", "禁用成功！");
			} else {
				supply.setSupply_id(parameter_id);
				supply.setState("1");
				this.supplyService.update(supply);
				model.addAttribute("promptmsg", "启用成功！");
			}

		}
		return list(request, model);
	}

	@RequestMapping("batchdelete")
	public String batchdelete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		boolean flag = false;
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		if (ids != null) {
			for (String id : ids) {
				if (checkSupply(id)) {
					flag = true;
				} else {
					this.supplyService.deleteOne(id);
				}
			}
		}
		if (flag) {
			model.addAttribute("promptmsg", "部分供应商已被引用,被引用的供应商不可删除！");
		} else {
			model.addAttribute("promptmsg", "供应商批量删除成功！");
		}
		return list(request, model);
	}

	@RequestMapping("batchenablestate")
	public String batchenablestate(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		if (ids != null) {
			for (String id : ids) {
				Supply supply = new Supply();
				supply.setSupply_id(id);
				supply.setState("1");
				this.supplyService.update(supply);
			}
		}
		model.addAttribute("promptmsg", "供应商批量启用成功！");
		return list(request, model);
	}

	@RequestMapping("batchlimitstate")
	public String batchlimitstate(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		boolean flag = false;
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		if (ids != null) {
			for (String id : ids) {
				if (checkSupply(id)) {
					flag = true;
				} else {
					Supply supply = new Supply();
					supply.setSupply_id(id);
					supply.setState("0");
					this.supplyService.update(supply);
				}
			}
		}
		if (flag) {
			model.addAttribute("promptmsg", "部分供应商已被引用,被引用的供应商不可禁用！");
		} else {
			model.addAttribute("promptmsg", "供应商批量禁用成功！");
		}
		return list(request, model);
	}

	private boolean checkSupply(String supply_id) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("supply_id", supply_id);
		paraMap.put("is_del", "1");
		List<Goods> glist = goodsService.getList(paraMap);
		if (glist.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}


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

import com.aten.function.AreaFuc;
import com.aten.model.orm.Appraisal;
import com.aten.model.orm.Goods;
import com.aten.service.AppraisalService;
import com.aten.service.GoodsService;

@Controller
@RequestMapping("admin/appraisal")
public class AppraisalController extends BaseController {

	private static final Logger logger = Logger.getLogger(AppraisalController.class);

	@Autowired
	private AppraisalService appraisalService;
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
		int count = this.appraisalService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Appraisal> appraisalList = this.appraisalService.getList(paraMap);
		for (Appraisal appraisal : appraisalList) {
			appraisal.setThe_area(AreaFuc.getAreaNameStr(appraisal.getThe_area()));
		}
		model.addAttribute("appraisalList", appraisalList);
		return "appraisal/list";
	}

	@RequestMapping("add")
	public String add(Model model) {
		return "appraisal/insert";
	}

	@RequestMapping("insert")
	public String insert(Appraisal appraisal, Model model,RedirectAttributesModelMap modelMap) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appraisal_name", appraisal.getAppraisal_name());
		List<Appraisal> alist = appraisalService.getList(paraMap);
		if (alist.size() > 0) {
			model.addAttribute("promptmsg", "此鉴定机构名称已被使用！");
		} else {
			this.appraisalService.insert(appraisal);
			//model.addAttribute("promptmsg", "鉴定机构添加成功！");
			modelMap.addFlashAttribute("promptmsg", "鉴定机构添加成功！");
		}
		//return add(model);
		return goUrl("appraisal/add");
	}

	@RequestMapping("edit")
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Appraisal appraisal = this.appraisalService.get(parameter_id);
		model.addAttribute("appraisal", appraisal);
		return "appraisal/update";
	}

	@RequestMapping("view")
	public String view(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		Appraisal appraisal = this.appraisalService.get(parameter_id);
		appraisal.setThe_area(AreaFuc.getAreaNameStr(appraisal.getThe_area()));
		model.addAttribute("appraisal", appraisal);
		return "appraisal/view";
	}

	@RequestMapping("update")
	public String update(HttpServletRequest request, Appraisal appraisal, Model model) {
		if (checkAppraisal(appraisal.getAppraisal_id()) && appraisal.getState().equals("0")) {
			model.addAttribute("promptmsg", "该鉴定机构已被引用不可禁用！");
		} else {
			HashMap<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("not_appraisal_id", appraisal.getAppraisal_id());
			paraMap.put("appraisal_name", appraisal.getAppraisal_name());
			List<Appraisal> alist = appraisalService.getList(paraMap);
			if (alist.size() > 0) {
				model.addAttribute("promptmsg", "此鉴定机构名称已被使用！");
			} else {
				this.appraisalService.update(appraisal);
				model.addAttribute("promptmsg", "鉴定机构修改成功！");
			}
		}
		return list(request, model);
	}

	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		if (checkAppraisal(parameter_id)) {
			model.addAttribute("promptmsg", "该鉴定机构已被引用不可删除！");
		} else {
			this.appraisalService.deleteOne(parameter_id);
			model.addAttribute("promptmsg", "鉴定机构删除成功！");
		}
		return list(request, model);
	}

	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		boolean flag = false;
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		if (ids != null) {
			for (String id : ids) {
				if (checkAppraisal(id)) {
					flag = true;
				} else {
					this.appraisalService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分鉴定机构已被引用,被引用的鉴定机构不可删除！");
		} else {
			model.addAttribute("promptmsg", "鉴定机构批量删除成功！");
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
		this.appraisalService.updateBatch(sortMapList);
		return list(request, model);
	}

	private boolean checkAppraisal(String appraisal_id) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appraisal_id", appraisal_id);
		paraMap.put("is_del", "1");
		List<Goods> glist = goodsService.getList(paraMap);
		if (glist.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}

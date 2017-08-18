package com.aten.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aten.function.CommparaFuc;
import com.aten.model.orm.Adv;
import com.aten.service.AdvService;
import com.communal.util.DateUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.aten.model.orm.Ad;
import com.aten.service.AdService;

/**
 * @author linjunqin
 * @Function 广告类 controller
 * @date 2017-02-14 09:39:53
 */
@Controller
@RequestMapping("admin/ad")
public class AdController extends BaseController {

	private static final Logger logger = Logger.getLogger(AdController.class);

	@Autowired
	private AdService adService;
	@Autowired
	private AdvService advService;

	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-1-5 下午2:37:11
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
		model.addAttribute("cfg_ad_type", CommparaFuc.getParaList("cfg_ad_type"));
	}

	/**
	 * @author linjunqin
	 * @Description 广告列表页方法
	 * @param
	 * @date 2017-1-4 上午11:58:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		String adv_id = this.getRequest().getParameter("adv_id");
		if (adv_id == null || adv_id.equals("")) {
			return goUrl("adv/list");
		}
		// 判断是否第一次传入
		model.addAttribute("adv_id", adv_id);
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("adv_id", adv_id);
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.adService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<Ad> adList = this.adService.getList(paraMap);
		try {
			for (Ad ad : adList) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date nowtime = new Date();
				Date start = sdf.parse(ad.getStart_time());
				Date end = sdf.parse(ad.getEnd_time());
				if (nowtime.getTime() < start.getTime()) {
					ad.setTime_state("0");
				} else if (nowtime.getTime() >= start.getTime() && nowtime.getTime() <= end.getTime()) {
					ad.setTime_state("1");
				} else if (nowtime.getTime() > end.getTime()) {
					ad.setTime_state("2");
				}
				//根据广告类型标识获取广告类型名称
				ad.setAd_type_name(CommparaFuc.getParaName("cfg_ad_type",ad.getAd_type()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("adList", adList);
		return "ad/list";
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到添加广告页面方法
	 * @param
	 * @date 2017-1-4 下午4:53:04
	 */
	@RequestMapping("add")
	public String add(Model model) {
		String adv_id = this.getRequest().getParameter("adv_id");
		// 判断是否第一次传入
		model.addAttribute("adv_id", adv_id);
		return "ad/insert";
	}

	/**
	 * @author linjunqin
	 * @Description 添加广告方法
	 * @param
	 * @date 2017-1-4 下午4:53:58
	 */
	@RequestMapping("insert")
	public String insert(Ad ad, Model model,RedirectAttributesModelMap modelMap) {
		Adv adv = advService.get(ad.getAdv_id());
		ad.setAdv_code(adv.getAdv_code());
		int re = DateUtil.compare_date(ad.getStart_time(), ad.getEnd_time(), 2);
		if (re == 1) {
			model.addAttribute("promptmsg", "开始时间大于结束时间！");
		} else {
			this.adService.insertAffair(ad);
			model.addAttribute("promptmsg", "广告添加成功！");
			//modelMap.addFlashAttribute("promptmsg", "广告添加成功！");
		}
		return add(model);
		//return goUrl("ad/add");
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到修改广告页面方法
	 * @param
	 * @date 2017-1-5 下午2:22:57
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取广告编码
		String adv_id = this.getRequest().getParameter("adv_id");
		// 判断是否第一次传入
		model.addAttribute("adv_id", adv_id);
		// 获取对象
		Ad ad = this.adService.get(parameter_id);
		model.addAttribute("ad", ad);
		model.addAttribute("parameter_id", parameter_id);
		return "ad/update";
	}

	/**
	 * @author linjunqin
	 * @Description 修改广告方法
	 * @param
	 * @date 2017-1-5 下午2:29:42
	 */
	@RequestMapping("update")
	public String update(HttpServletRequest request, Ad ad, Model model) {
		Adv adv = advService.get(ad.getAdv_id());
		if (adv == null) {
			return goUrl("adv/list");
		}
		ad.setAdv_code(adv.getAdv_code());
		int re = DateUtil.compare_date(ad.getStart_time(), ad.getEnd_time(), 2);
		if (re == 1) {
			model.addAttribute("promptmsg", "开始时间大于结束时间！");
			model.addAttribute("parameter_id",ad.getAd_id());
			return edit(request, model);
		} else {
			this.adService.update(ad);
			model.addAttribute("promptmsg", "广告修改成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 删除广告方法
	 * @param
	 * @date 2017-1-5 下午2:36:30
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.adService.deleteAffair(parameter_id);
		model.addAttribute("promptmsg", "广告删除成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 广告排序方法
	 * @param
	 * @date 2017-1-5 下午2:41:02
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.adService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量删除广告
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);

		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (int i = 0; i < ids.length; i++) {
			this.adService.deleteOne(ids[i]);
		}
		model.addAttribute("promptmsg", "广告批量删除成功！");
		return list(request, model);
	}

}

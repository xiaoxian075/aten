/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-03 14:50:59  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: NewsController.java 
 * Author:linjunqin
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
import com.aten.function.CatFuc;
import com.aten.function.SysconfigFuc;
import com.communal.constants.Constant;
import com.communal.util.StringUtil;
import com.aten.model.orm.Cat;
import com.aten.model.orm.Job;
import com.aten.model.orm.News;
import com.aten.service.CatService;
import com.aten.service.JobService;
import com.aten.service.NewsService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linjunqin
 * @Function 资讯类 controller
 * @date 2017-07-03 14:50:59
 */
@Controller
@RequestMapping("admin/news")
public class NewsController extends BaseController {

	private static final Logger logger = Logger.getLogger(NewsController.class);

	@Autowired
	private NewsService newsService;
	@Autowired
	private JobService jobService;
	@Autowired
	private CatService catService;

	/**
	 * @author linjunqin
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
		// 默认的地区节点
		model.addAttribute("cfg_news_cat", SysconfigFuc.getSysValue("cfg_news_cat"));
	}

	/**
	 * @author linjunqin
	 * @Description 资讯列表页方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		int count = this.newsService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<News> newsList = this.newsService.getList(paraMap);
		for (News news : newsList) {
			news.setThe_cat(CatFuc.getCatNameStr(news.getThe_cat(), Constant.POS));
			news.setIssue_time(StringUtil.getStandDate(news.getIssue_time()));
		}
		model.addAttribute("newsList", newsList);
		return "news/list";
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到添加资讯页面方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model) {
		return "news/insert";
	}

	/**
	 * @author linjunqin
	 * @Description 添加资讯方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(HttpServletRequest request, News news, Model model) {
		if (news.getNews_title() == null || news.getNews_title().equals("") || news.getThe_cat() == null
				|| news.getThe_cat().equals("") || news.getNews_picture() == null || news.getNews_picture().equals("")
				|| news.getSort_no() == null || news.getSort_no().equals("")) {
			return add(model);
		}
		news.setIntroduction("-");
		news.setIs_top("0");
		if (news.getState().equals("2")) {
			int r = this.newsService.insertGetPk(news);
			if (r > 0) {
				Job j = new Job();
				j.setInfo_id(news.getNews_id());
				j.setModoule("news");
				j.setIn_date(news.getIssue_time());
				jobService.insert(j);
			}
		} else if (news.getState().equals("1")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String issue_time = sdf.format(new Date());
			news.setIssue_time(issue_time);
			this.newsService.insertGetPk(news);
		} else {
			news.setIssue_time(null);
			this.newsService.insertGetPk(news);
		}
		model.addAttribute("promptmsg", "资讯添加成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到修改资讯页面方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		News news = this.newsService.get(parameter_id);
		news.setIssue_time(StringUtil.getStandDate(news.getIssue_time()));
		model.addAttribute("news", news);
		return "news/update";
	}

	/**
	 * @author linjunqin
	 * @Description 跳转到修改资讯页面方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("view")
	@TokenAnnotation(needSaveToken = true)
	public String view(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		News news = this.newsService.get(parameter_id);
		news.setThe_cat(CatFuc.getCatNameStr(news.getThe_cat(), Constant.POS));
		news.setCreate_time(StringUtil.getStandDate(news.getCreate_time()));
		model.addAttribute("news", news);
		return "news/view";
	}

	/**
	 * @author linjunqin
	 * @Description 修改资讯方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, News news, Model model) {
		news.setIntroduction("-");
		news.setIs_top("0");
		if (news.getState().equals("2")) {
			this.newsService.update(news);
			Job j = new Job();
			j.setInfo_id(news.getNews_id());
			j.setModoule("news");
			j.setIn_date(news.getIssue_time());
			jobService.insert(j);
		} else if (news.getState().equals("1")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String issue_time = sdf.format(new Date());
			news.setIssue_time(issue_time);
			this.newsService.update(news);
		} else {
			news.setIssue_time(null);
			this.newsService.update(news);
		}
		this.newsService.update(news);
		model.addAttribute("promptmsg", "资讯修改成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 删除资讯方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		this.newsService.deleteOne(parameter_id);
		model.addAttribute("promptmsg", "资讯删除成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 资讯排序方法
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.newsService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 资讯批量删除成功
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
					this.newsService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分资讯已被引用,被引用的资讯删除失败！");
		} else {
			model.addAttribute("promptmsg", "资讯删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 启用资讯
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("pushState")
	public String pushState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		News news = this.newsService.get(parameter_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String issue_time = sdf.format(new Date());

		news.setIssue_time(issue_time);
		news.setState("1");
		this.newsService.update(news);
		model.addAttribute("promptmsg", "资讯发布成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 启用资讯
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		News news = this.newsService.get(parameter_id);
		news.setState("1");
		this.newsService.update(news);
		model.addAttribute("promptmsg", "资讯发布成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 禁用资讯
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		News news = this.newsService.get(parameter_id);
		news.setState("0");
		this.newsService.update(news);
		model.addAttribute("promptmsg", "资讯禁用成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量启用资讯
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			News news = this.newsService.get(id);
			news.setState("1");
			this.newsService.update(news);
		}
		model.addAttribute("promptmsg", "资讯批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 批量禁用资讯
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			News news = this.newsService.get(id);
			news.setState("0");
			this.newsService.update(news);
		}
		model.addAttribute("promptmsg", "资讯批量禁用成功！");
		return list(request, model);
	}

	/**
	 * @author linjunqin
	 * @Description 验证删除资讯时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String news_id) {
		return false;
	}

}

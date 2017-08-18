/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-15 16:14:29  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: CustomizedPageController.java 
 * Author:hx
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aten.function.DiscountFuc;
import com.aten.model.bean.ZtreeBean;
import com.aten.model.orm.*;
import com.aten.model.vo.DiscountData;
import com.aten.service.CatService;
import com.aten.service.GoodsActivityService;
import com.communal.constants.Constant;
import com.communal.entity.JSONResult;
import com.communal.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.aten.annotation.TokenAnnotation;
import com.aten.service.CustomizedPageService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author hx
 * @Function 专题页面类  controller
 * @date 2017-07-15 16:14:29
 */
@Controller
@RequestMapping("admin/customizedPage")
public class CustomizedPageController extends BaseController{

	private static final Logger logger = Logger.getLogger(CustomizedPageController.class);
	@Autowired
	private CatService catService;
	@Autowired
	private CustomizedPageService customizedPageService;
	@Autowired
	private GoodsActivityService goodsActivityService;
	/**
	 * @author hx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-15 16:14:29
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }

	@RequestMapping(value = "saveModule" )
	public void saveModule(HttpServletResponse response ,  CustomizedPage customizedPage, String jsonData) {
		//保存用户操作模块
		try{
			customizedPageService.insertPageModule(customizedPage,jsonData);
			outPrint(response,"保存成功");
			return;
		}catch (Exception e ){
			logger.error("e",e);
			outPrint(response,"保存失败");
		}
	}

	/**
	 * 活动商品列表
	 */
	@RequestMapping("getActivityGoodsList")
	public void getActivityGoodsList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		String listJson = "";
		try{
			//查询列表数据
			Query query = new Query(params);
			query.put("start", query.get("offset"));
			List<HashMap> list = customizedPageService.selectActivityGoodsList(query);
			for (HashMap map:list){
				MapGetterTool mapGetterTool = new MapGetterTool(map);
				Long lower_price = mapGetterTool.getLongDefault("lower_price");
				map.put("list_img",ImageUtil.getRealImgPath(mapGetterTool.getString("list_img")));
				map.put("goods_price",FunUtil.fenToYuan(new BigDecimal(lower_price)));
				//销售价格计算
				DiscountData ds = new DiscountData();
				ds.setActivityType(mapGetterTool.getInteger("activity_type"));
				ds.setDiscountAmount(mapGetterTool.getString("discount"));
				lower_price = DiscountFuc.dealDiscountPrice(ds,mapGetterTool.getLongDefault("lower_price"));//优惠后折扣价
				map.put("goods_sale_price",FunUtil.fenToYuan(new BigDecimal(lower_price)));
			}
			int count = this.customizedPageService.selectActivityGoodsCount(query);

			PageUtils pageUtil = new PageUtils(list, count, query.getLimit(), query.getPage());

			listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));
		}catch (Exception e){
			logger.error("e",e);
		}
		outPrint(response, listJson);
	}



	/**
	 * 自定义页面列表
     */
	@RequestMapping("getCustomizedPageList")
	public void getCustomizedPageList(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		//查询列表数据
		Query query = new Query(params);
		query.put("start", query.get("offset"));
		List<CustomizedPage> list = customizedPageService.getList(query);
		for (CustomizedPage customizedPage:list){
			customizedPage.setPageBody(null);
		}
		int count = this.customizedPageService.getCount(query);

		PageUtils pageUtil = new PageUtils(list, count, query.getLimit(), query.getPage());

		String listJson = JsonUtil.object2json(R.ok().put("page", pageUtil));

		outPrint(response, listJson);
	}


	@RequestMapping("linkTools")
	public String customizedPageLinkTools() {
		return "customizedPage/linkTools";
	}

	/**
	 * @author hx
	 * @Description 专题页面列表页方法
	 * @param
	 * @date 2017-07-15 16:14:29
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.customizedPageService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<CustomizedPage> customizedPageList = this.customizedPageService.getList(paraMap);
		for(CustomizedPage cp : customizedPageList){
			cp.setCreateTime(StringUtil.getStandDate(cp.getCreateTime()));
			if (cp.getLastTime() != null && !cp.getLastTime().equals("")) {
				cp.setLastTime(StringUtil.getStandDate(cp.getLastTime()));
			}
		}
		model.addAttribute("customizedPageList", customizedPageList);
		return "customizedPage/list";
	}
	
	
	
	
	 /**
	 * @author hx
	 * @Description 跳转到添加专题页面页面方法
	 * @param
	 * @date 2017-07-15 16:14:29
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "customizedPage/insert";
	}

	/**
	 * 跳转到专题页面
	 * @param model
	 * @param customizedPage
     * @return
     */
	@RequestMapping("appPage")
	@TokenAnnotation(needSaveToken = true)
	public String appPage(Model model, CustomizedPage customizedPage) {
		model.addAttribute("info",customizedPage);
		return "customizedPage/appPage";
	}

	@RequestMapping("viewEdit")
	@TokenAnnotation(needSaveToken = true)
	public String viewEdit(HttpServletRequest request,Model model, CustomizedPage customizedPage) {
		String parameter_id = request.getParameter("parameter_id");
		customizedPage.setPageUnique(parameter_id);
		model.addAttribute("ossPath", Constant.OSS_IMG_URL);
		if(StringUtils.isEmpty(customizedPage.getPageUnique())){
			model.addAttribute("info",new CustomizedPage());
		}else{
			CustomizedPage customizedPage1=customizedPageService.get(customizedPage.getPageUnique());
			model.addAttribute("info",customizedPage1);
		}
		return "customizedPage/viewEdit";
	}
	/**
	 * @author hx
	 * @Description 添加专题页面方法
	 * @param
	 * @date 2017-07-15 16:14:29
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(CustomizedPage customizedPage,Model model){
		customizedPage.setPageUnique(FunUtil.randNumID());
		customizedPage.setCreateTime(FunUtil.getSysDateLong());
		this.customizedPageService.insert(customizedPage);
		model.addAttribute("promptmsg","专题页面添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author hx
	 * @Description 跳转到修改专题页面页面方法
	 * @param
	 * @date 2017-07-15 16:14:29
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		CustomizedPage customizedPage = this.customizedPageService.get(parameter_id);
		model.addAttribute("customizedPage", customizedPage);
		model.addAttribute("isEdit", true);
		return "customizedPage/update";
	}
	
	
	/**
	 * @author hx
	 * @Description 修改专题页面方法
	 * @param
	 * @date 2017-07-15 16:14:29
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,CustomizedPage customizedPage,Model model){
		this.customizedPageService.update(customizedPage);
		model.addAttribute("promptmsg","专题页面修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author hx
	 * @Description 删除专题页面方法
	 * @param
	 * @date 2017-07-15 16:14:29
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.customizedPageService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","专题页面删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author hx
	 * @Description 专题页面排序方法
	 * @param
	 * @date 2017-07-15 16:14:29
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.customizedPageService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author hx
	 * @Description 专题页面批量删除成功
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		boolean flag=false;
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			if(!StringUtil.isEmpty(id)){
				if(checkBrand(id)){
					flag = true;
				}else{
					this.customizedPageService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分专题页面已被引用,被引用的专题页面删除失败！");
		}else{
			model.addAttribute("promptmsg","专题页面删除成功！");
		}
		return list(request, model);
	}

	
	/**
	 * @author hx
	 * @Description 验证删除专题页面时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String page_unique){
		return false;
	}
	
	
	
}


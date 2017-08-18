/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-19 21:00:14  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: OrderIntegralController.java 
 * Author:hx
 */
package com.aten.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aten.model.orm.*;
import com.aten.model.vo.BillFlowVo;
import com.aten.model.vo.IntegralVo;
import com.communal.util.AmountUtil;
import com.communal.util.ExportExcel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.communal.util.StringUtil;
import com.aten.service.IntegralService;
import com.aten.service.OrderIntegralService;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hx
 * @Function 兑换记录类 controller
 * @date 2017-07-19 21:00:14
 */
@Controller
@RequestMapping("admin/orderIntegral")
public class OrderIntegralController extends BaseController {

	private static final Logger logger = Logger.getLogger(OrderIntegralController.class);

	@Autowired
	private OrderIntegralService orderIntegralService;
	@Autowired
	private IntegralService integralService;

	/**
	 * @author hx
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-19 21:00:14
	 */
	@ModelAttribute
	public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request, model);
	}

	/**
	 * @author hx
	 * @Description 兑换记录列表页方法
	 * @param
	 * @date 2017-07-19 21:00:14
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		// 搜索封装
		paraMap = searchMap(request, paraMap, model);
		String parameter_id = request.getParameter("parameter_id");
		paraMap.put("integral_id", parameter_id);
		int count = this.orderIntegralService.getCount(paraMap);
		// 分页工具
		paraMap = pageTool(request, count, model, paraMap);
		List<OrderIntegral> orderIntegralList = this.orderIntegralService.getList(paraMap);
		for (OrderIntegral oi : orderIntegralList) {
			oi.setExchange_time(StringUtil.getStandDate(oi.getExchange_time()));
		}
		model.addAttribute("orderIntegralList", orderIntegralList);
		model.addAttribute("parameter_id", parameter_id);
		return "orderIntegral/list";
	}

	/**
	 * @author hx
	 * @Description 查看兑换记录详情
	 * @param
	 * @date 2017-07-03 14:50:59
	 */
	@RequestMapping("view")
	public String view(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 获取对象
		OrderIntegral oi = this.orderIntegralService.get(parameter_id);
		oi.setExchange_time(StringUtil.getStandDate(oi.getExchange_time()));
		model.addAttribute("oi", oi);
		Integral integral = integralService.get(oi.getIntegral_id());
		model.addAttribute("integral", integral);
		OrderExpressNode orderExpress = this.orderIntegralService.getOrderExpress(oi.getOrder_number());
		model.addAttribute("orderExpress", orderExpress);
		return "orderIntegral/view";
	}

	/**
	 * @author hx
	 * @Description 兑换记录排序方法
	 * @param
	 * @date 2017-07-19 21:00:14
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model) {
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if (sort_id == null || sort_val == null)
			return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id, sort_val);
		this.orderIntegralService.updateBatch(sortMapList);
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 兑换记录批量删除成功
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
					this.orderIntegralService.deleteOne(id);
				}
			}
		}
		// 判断标志提示
		if (flag) {
			model.addAttribute("promptmsg", "部分兑换记录已被引用,被引用的兑换记录删除失败！");
		} else {
			model.addAttribute("promptmsg", "兑换记录删除成功！");
		}
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 启用兑换记录
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		OrderIntegral orderIntegral = this.orderIntegralService.get(parameter_id);
		this.orderIntegralService.update(orderIntegral);
		model.addAttribute("promptmsg", "兑换记录启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 禁用兑换记录
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		OrderIntegral orderIntegral = this.orderIntegralService.get(parameter_id);
		this.orderIntegralService.update(orderIntegral);
		model.addAttribute("promptmsg", "兑换记录禁用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量启用兑换记录
	 * @param
	 * @date 2017-07-19 21:00:14
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			OrderIntegral orderIntegral = this.orderIntegralService.get(id);
			this.orderIntegralService.update(orderIntegral);
		}
		model.addAttribute("promptmsg", "兑换记录批量启用成功！");
		return list(request, model);
	}

	/**
	 * @author hx
	 * @Description 批量禁用兑换记录
	 * @param
	 * @date 2017-07-19 21:00:14
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model) {
		String parameter_id = request.getParameter("parameter_id");
		if (parameter_id == null || parameter_id.equals(""))
			return list(request, model);
		// 转成数组更新
		String[] ids = parameter_id.split(",");
		for (String id : ids) {
			OrderIntegral orderIntegral = this.orderIntegralService.get(id);
			this.orderIntegralService.update(orderIntegral);
		}
		model.addAttribute("promptmsg", "兑换记录批量禁用成功！");
		return list(request, model);
	}


	/**
	 * 导出
	 */
	@RequestMapping("/export")
	public String export(HttpServletRequest request, Model model, RedirectAttributesModelMap modelMap, HttpServletResponse response){

		ExportExcel<IntegralVo> ex = new ExportExcel<IntegralVo>();
		String[] headers =
				{ "兑换编号", "礼品名称", "兑换时间","使用积分值", "会员账户", "收货人", "联系电话", "收货地址"};
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		String parameter_id = request.getParameter("parameter_id");
		paraMap.put("integral_id", parameter_id);
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		List<OrderIntegral> list = this.orderIntegralService.getList(paraMap);
		List<IntegralVo> data=new ArrayList<>();
		for (int i=0;i<list.size();i++){
			OrderExpressNode orderExpress = this.orderIntegralService.getOrderExpress(list.get(i).getOrder_number());
			IntegralVo vo=new IntegralVo();
			vo.setOrder_number(list.get(i).getOrder_number());
			vo.setIntegral_goods_name(list.get(i).getIntegral_goods_name());
			vo.setUse_integral(list.get(i).getUse_integral());
			vo.setExchange_time(list.get(i).getExchange_time());
			vo.setLogin_name(list.get(i).getLogin_name());
			if(orderExpress!=null){
				vo.setConsignee(orderExpress.getConsignee());
				vo.setConsignee_mobile(orderExpress.getConsignee_mobile());
				vo.setConsignee_address(orderExpress.getConsignee_address());
			}

			data.add(vo);
		}
		try
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String fileName="积分兑换"+sdf.format(new Date())+".xls";
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			ServletOutputStream outputStream = response.getOutputStream();
			ex.exportExcel(fileName,headers, data, outputStream,"yyyy-MM-dd HH:mm:ss");
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		modelMap.addFlashAttribute("promptmsg","积分兑换数据导出成功");
		return goUrl("accountwithdrawbill/list");
	}

	/**
	 * @author hx
	 * @Description 验证删除兑换记录时是否被引用
	 * @param
	 * @return true 被引用 false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String ex_id) {
		return false;
	}

}

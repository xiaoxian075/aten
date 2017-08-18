/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-28 18:35:56  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: BillFlowController.java 
 * Author:chenyi
 */
package com.aten.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aten.model.orm.AccountWithdrawBill;
import com.aten.model.vo.BillFlowVo;
import com.aten.model.vo.CashVo;
import com.communal.util.AmountUtil;
import com.communal.util.ExportExcel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.aten.function.CommparaFuc;
import com.communal.util.StringUtil;
import com.aten.model.orm.BillFlow;
import com.aten.model.orm.ManaFunds;
import com.aten.service.BillFlowService;
import com.aten.service.ManaFundsService;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author chenyi
 * @Function 账单详情  controller
 * @date 2017-07-28 18:35:56
 */
@Controller
@RequestMapping("admin/billflow")
public class BillFlowController extends BaseController{

	private static final Logger logger = Logger.getLogger(BillFlowController.class);
	
	@Autowired
	private BillFlowService billFlowService;
	@Autowired
	private ManaFundsService manaFundsService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 账单详情列表页方法
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		//获取平台总资金
		ManaFunds manaFunds = manaFundsService.get("0");
		model.addAttribute("balance",AmountUtil.fenToYuan(manaFunds.getBalance()));
		model.addAttribute("total_assets",AmountUtil.fenToYuan(manaFunds.getTotal_assets()));
		model.addAttribute("frozen_amount",AmountUtil.fenToYuan(manaFunds.getFrozen_amount()));
		//获取平台帐单流信息
		String settle_date = request.getParameter("parameter_id");
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("settle_date",settle_date);
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.billFlowService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<BillFlow> billFlowList = this.billFlowService.getList(paraMap);
		for(BillFlow billFlow:billFlowList){
			billFlow.setOrder_type_name(CommparaFuc.getParaName("cfg_order_type", billFlow.getOrder_type()));
			billFlow.setAmount(AmountUtil.formatMoney(billFlow.getBill_amount().toString()));
		}
		model.addAttribute("billFlowList", billFlowList);
		model.addAttribute("parameter_id",settle_date);
		return "billFlow/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加账单详情页面方法
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "billFlow/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加账单详情方法
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(BillFlow billFlow,Model model){
		this.billFlowService.insert(billFlow);
		model.addAttribute("promptmsg","账单详情添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改账单详情页面方法
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		BillFlow billFlow = this.billFlowService.get(parameter_id);
		model.addAttribute("billFlow", billFlow);
		return "billFlow/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改账单详情方法
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,BillFlow billFlow,Model model){
		this.billFlowService.update(billFlow);
		model.addAttribute("promptmsg","账单详情修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除账单详情方法
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.billFlowService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","账单详情删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 账单详情排序方法
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.billFlowService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 账单详情批量删除成功
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
					this.billFlowService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分账单详情已被引用,被引用的账单详情删除失败！");
		}else{
			model.addAttribute("promptmsg","账单详情删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用账单详情
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		BillFlow billFlow  = this.billFlowService.get(parameter_id);
		//billFlow.setState("1");
		this.billFlowService.update(billFlow);
		model.addAttribute("promptmsg","账单详情启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用账单详情
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		BillFlow billFlow  = this.billFlowService.get(parameter_id);
		//billFlow.setState("0");
		this.billFlowService.update(billFlow);
		model.addAttribute("promptmsg","账单详情禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用账单详情
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			BillFlow billFlow  = this.billFlowService.get(id);
			//billFlow.setState("1");
			this.billFlowService.update(billFlow);
		}
		model.addAttribute("promptmsg","账单详情批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用账单详情
	 * @param
	 * @date 2017-07-28 18:35:56
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			BillFlow billFlow  = this.billFlowService.get(id);
			//billFlow.setState("0");
			this.billFlowService.update(billFlow);
		}
		model.addAttribute("promptmsg","账单详情批量禁用成功！");
		return list(request, model);
	}



	/**
	 * 导出
	 */
	@RequestMapping("/export")
	public String export(HttpServletRequest request, Model model, RedirectAttributesModelMap modelMap, HttpServletResponse response){

		ExportExcel<BillFlowVo> ex = new ExportExcel<BillFlowVo>();
		String[] headers =
				{ "交易流水号", "支付方式", "交易金额", "账单时间", "订单类型", "收益类型", "订单号", "备注"};
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		List<BillFlow> list = this.billFlowService.getList(paraMap);
		List<BillFlowVo> data=new ArrayList<>();
		for (int i=0;i<list.size();i++){
			BillFlowVo vo=new BillFlowVo();
			vo.setBill_amount(AmountUtil.formatMoney(list.get(i).getBill_amount().toString()));
			vo.setBill_time(list.get(i).getBill_time());
			vo.setBill_type((list.get(i).getBill_type()+"").equals("1")?"收入":"支出");
			vo.setNote(list.get(i).getNote());
			vo.setOrder_type(list.get(i).getOrder_type_name());
			vo.setRalation_id(list.get(i).getRalation_id());
			vo.setTrade_id(list.get(i).getTrade_id());
			vo.setPay_way_name(list.get(i).getPay_way().equals("1")?"余额支付":"云付通支付");
			data.add(vo);
		}
		try
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String fileName="财务账单"+sdf.format(new Date())+".xls";
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
		modelMap.addFlashAttribute("promptmsg","财务账单数据导出成功");
		return goUrl("accountwithdrawbill/list");
	}


	/**
	 * @author chenyi
	 * @Description 验证删除账单详情时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String bf_id){
		return false;
	}
	
	
	
}


/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-13 20:06:11  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: AccountWithdrawBillController.java 
 * Author:chenyi
 */
package com.aten.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aten.model.orm.Account;
import com.aten.model.vo.CashVo;
import com.aten.service.AccountService;
import com.communal.constants.Constant;
import com.communal.util.AmountUtil;
import com.communal.util.ExportExcel;
import com.communal.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.aten.model.orm.AccountWithdrawBill;
import com.aten.service.AccountWithdrawBillService;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @author chenyi
 * @Function 会员提现帐单  controller
 * @date 2017-07-13 20:06:11
 */
@Controller
@RequestMapping("admin/accountwithdrawbill")
public class AccountWithdrawBillController extends BaseController{

	//private static final Logger logger = Logger.getLogger(AccountWithdrawBillController.class);
	
	@Autowired
	private AccountWithdrawBillService accountWithdrawBillService;
	@Autowired
	private AccountService accountService;
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-13 20:06:11
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 会员提现帐单列表页方法
	 * @param
	 * @date 2017-07-13 20:06:11
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.accountWithdrawBillService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<AccountWithdrawBill> accountWithdrawBillList = this.accountWithdrawBillService.getList(paraMap);
		for(AccountWithdrawBill accountWithdrawBill:accountWithdrawBillList){
			accountWithdrawBill.setAmount(AmountUtil.formatMoney(accountWithdrawBill.getAmount()));
		}
		model.addAttribute("accountWithdrawBillList", accountWithdrawBillList);
		//获取统计金额
//		paraMap.clear();
		AccountWithdrawBill awb=accountWithdrawBillService.getTotalAmount(paraMap);
		String totalAmount="0";
		if(awb!=null){
			 totalAmount=AmountUtil.formatMoney(awb.getTotal_amount());
		}
		model.addAttribute("totalAmount",totalAmount);
		return "accountWithdrawBill/list";
	}
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加会员提现帐单页面方法
	 * @param
	 * @date 2017-07-13 20:06:11
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "accountWithdrawBill/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加会员提现帐单方法
	 * @param
	 * @date 2017-07-13 20:06:11
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(AccountWithdrawBill accountWithdrawBill,Model model){
		this.accountWithdrawBillService.insert(accountWithdrawBill);
		model.addAttribute("promptmsg","会员提现帐单添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改会员提现帐单页面方法
	 * @param
	 * @date 2017-07-13 20:06:11
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		AccountWithdrawBill accountWithdrawBill = this.accountWithdrawBillService.get(parameter_id);
		accountWithdrawBill.setAmount(AmountUtil.formatMoney(accountWithdrawBill.getAmount()));
		//该账号余额
		String account_id=accountWithdrawBill.getAccount_id();
		Account account=accountService.getById(new BigInteger(account_id));
		String earnings=AmountUtil.formatMoney(account.getEarnings()+"");
		account.setFormat_earnings(earnings);
		model.addAttribute("account",account);
		model.addAttribute("accountWithdrawBill", accountWithdrawBill);
		return "accountWithdrawBill/update";
	}

	/**
	 * @author chenyi
	 * @Description 跳转到详情页
	 * @param
	 * @date 2017-07-13 20:06:11
	 */
	@RequestMapping("view")
	@TokenAnnotation(needSaveToken = true)
	public String view(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		AccountWithdrawBill accountWithdrawBill = this.accountWithdrawBillService.get(parameter_id);
		accountWithdrawBill.setAmount(AmountUtil.formatMoney(accountWithdrawBill.getAmount()));
		//该账号余额
		String account_id=accountWithdrawBill.getAccount_id();
		Account account=accountService.getById(new BigInteger(account_id));
		String earnings=AmountUtil.formatMoney(account.getEarnings()+"");
		account.setFormat_earnings(earnings);
		model.addAttribute("account",account);
		model.addAttribute("accountWithdrawBill", accountWithdrawBill);
		return "accountWithdrawBill/view";
	}


	/**
	 * @author chenyi
	 * @Description 修改会员提现帐单方法
	 * @param
	 * @date 2017-07-13 20:06:11
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,AccountWithdrawBill accountWithdrawBill,Model model){
		//如果不同意  备注不能为空
		if("2".equals(accountWithdrawBill.getAudit_state())){
			if(accountWithdrawBill.getAudit_note().trim()==null||"".equals(accountWithdrawBill.getAudit_note().trim())){
				model.addAttribute("promptmsg","备注不能为空！");
				return edit(request,model);
			}
		}
		String user_name=(String)request.getSession().getAttribute(Constant.USER_NAME);
		String user_role=(String)request.getSession().getAttribute(Constant.USER_ROLE);
		accountWithdrawBill.setAudit_men_name(user_name);
		accountWithdrawBill.setAudit_men_role(user_role);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		accountWithdrawBill.setAudit_time(sdf.format(new Date()));
		accountWithdrawBillService.audit(accountWithdrawBill);
		model.addAttribute("promptmsg","审批成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除会员提现帐单方法
	 * @param
	 * @date 2017-07-13 20:06:11
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.accountWithdrawBillService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","会员提现帐单删除成功！");
		return list(request, model);
	}




	/**
	 * 导出
	 */
	@RequestMapping("/export")
	public String export(HttpServletRequest request, Model model, RedirectAttributesModelMap modelMap, HttpServletResponse response){

		ExportExcel<CashVo> ex = new ExportExcel<CashVo>();
		String[] headers =
				{ "会员账号", "提现金额", "提现时间", "提现备注", "审批状态"};
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		List<AccountWithdrawBill> list = this.accountWithdrawBillService.getList(paraMap);
		List<CashVo> data=new ArrayList<>();
		for (int i=0;i<list.size();i++){
			CashVo vo=new CashVo();
			vo.setAmount(AmountUtil.formatMoney(list.get(i).getAmount()));
			vo.setAudit_state(list.get(i).getAudit_state());
			vo.setCreate_time(list.get(i).getCreate_time());
			vo.setWithdraw_note(list.get(i).getWithdraw_note());
			vo.setLogin_name(list.get(i).getLogin_name());
			data.add(vo);
		}
		try
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String fileName="提现列表"+sdf.format(new Date())+".xls";
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
		modelMap.addFlashAttribute("promptmsg","提现数据导出成功");
		return goUrl("accountwithdrawbill/list");
	}

}


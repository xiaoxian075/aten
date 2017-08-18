/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-11 11:25:34  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: CouponController.java 
 * Author:chenyi
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;

import com.aten.model.orm.AccountCoupon;
import com.aten.model.orm.CouponUseCat;
import com.aten.model.orm.Goods;
import com.aten.service.AccountCouponService;
import com.aten.service.CouponUseCatService;
import com.aten.service.ShakeAwardsService;
import com.communal.util.AmountUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.communal.util.StringUtil;
import com.aten.model.orm.Coupon;
import com.aten.service.CouponService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 优惠券  controller
 * @date 2017-07-11 11:25:34
 */
@Controller
@RequestMapping("admin/coupon")
public class CouponController extends BaseController{

	private static final Logger logger = Logger.getLogger(CouponController.class);
	
	@Autowired
	private CouponService couponService;
	@Autowired
	private AccountCouponService accountCouponService;
	@Autowired
	private CouponUseCatService couponUseCatService;
	@Autowired
	private ShakeAwardsService shakeAwardsService;
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 优惠券列表页方法
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.couponService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<Coupon> couponList = this.couponService.getList(paraMap);
		for (Coupon coupon:couponList){
			coupon.setCoupon_amount(AmountUtil.formatMoney(coupon.getCoupon_amount()));
			coupon.setUse_amount(AmountUtil.formatMoney(coupon.getUse_amount()));
//			//设置状态
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			try {
//				Date now=new Date();
//				Date end_time=sdf.parse(coupon.getEnd_time());
//				if(now.getTime()>end_time.getTime()){
//					coupon.setState("0");//过期
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
		}
		model.addAttribute("couponList", couponList);
		return "coupon/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加优惠券页面方法
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "coupon/insert";
	}

	@RequestMapping("view")
	@TokenAnnotation(needSaveToken = true)
	public String view(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Coupon coupon = this.couponService.get(parameter_id);
		coupon.setCoupon_amount(AmountUtil.formatMoney(coupon.getCoupon_amount()));
		coupon.setUse_amount(AmountUtil.formatMoney(coupon.getUse_amount()));
		model.addAttribute("coupon", coupon);
		//获取该优惠券关联的账户优惠券
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap.put("is_del","0");
		paraMap.put("coupon_id",parameter_id);
		int account_get_num = this.accountCouponService.getCount(paraMap);
		//会员领取数量
		model.addAttribute("account_get_num", account_get_num);
		paraMap.clear();
		paraMap.put("is_del","0");
		paraMap.put("coupon_id",parameter_id);
		paraMap.put("state","1");
		//会员已使用数量
		int account_use_num = this.accountCouponService.getCount(paraMap);
		model.addAttribute("account_use_num", account_use_num);
		//如果是指定商品  需展示商品
		if("2".equals(coupon.getUse_type())){
			//获取该优惠券关联的账户优惠券
			HashMap<String, Object> getMap = new HashMap<String,Object>();
			getMap.put("coupon_id",coupon.getCoupon_id());
			List<CouponUseCat> goodsList=couponUseCatService.getList(getMap);
			model.addAttribute("goodsList",goodsList);
		}
		return "coupon/view";
	}
	
	/**
	 * @author chenyi
	 * @Description 添加优惠券方法
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(Coupon coupon,Model model,HttpServletRequest request) throws ParseException {
		//验证金额
		String regEx="(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
		if(!coupon.getCoupon_amount().matches(regEx)){
			model.addAttribute("promptmsg","请输入有效的优惠券面值金额！");
			return add(model);
		}
		if(Float.parseFloat(coupon.getCoupon_amount())==0){
			model.addAttribute("promptmsg","优惠券面值金额不能为0！");
			return add(model);
		}
		if(!coupon.getUse_amount().matches(regEx)){
			model.addAttribute("promptmsg","请输入有效的使用门槛金额！");
			return add(model);
		}

		if(!coupon.getUse_num().matches("^[0-9]*[1-9][0-9]*$")){
			model.addAttribute("promptmsg","每人限领数量必须为正整数！");
			return add(model);
		}
		if(!coupon.getCoupon_num().matches("^[0-9]*[1-9][0-9]*$")){
			model.addAttribute("promptmsg","优惠券数量必须为正整数！");
			return add(model);
		}
		//优惠券数量要大于每人限领数量
		if(Integer.parseInt(coupon.getCoupon_num())<Integer.parseInt(coupon.getUse_num())){
			model.addAttribute("promptmsg","优惠券数量要大于可领取数量！");
			return add(model);
		}
		//使用门槛金额要大于优惠券金额
		if(Double.valueOf(coupon.getUse_amount())<=Double.valueOf(coupon.getCoupon_amount())){
			model.addAttribute("promptmsg","使用门槛要大于优惠券面值！");
			return add(model);
		}
		if("".equals(coupon.getEnd_time())){
			model.addAttribute("promptmsg","截止有效期不能为空！");
			return add(model);
		}
		Date now= new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dt2 = df.parse(coupon.getEnd_time());
		if (now.getTime()> dt2.getTime()) {
			model.addAttribute("promptmsg","截止有效期不能小于当前日期！");
			return add(model);
		}
//		this.couponService.insert(coupon);
		couponService.saveInfo(coupon);
		model.addAttribute("promptmsg","优惠券添加成功！");
		return list(request,model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改优惠券页面方法
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		Coupon coupon = this.couponService.get(parameter_id);
		model.addAttribute("coupon", coupon);
		return "coupon/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改优惠券方法
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,Coupon coupon,Model model){
		this.couponService.update(coupon);
		model.addAttribute("promptmsg","优惠券修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除优惠券方法
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//摇一摇优惠券已关联进行中的活动不可删除
		boolean flag=false;
		if(checkCoupon(parameter_id)){
			flag = true;
		}else{
			this.couponService.deleteOne(parameter_id);
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分优惠券已被引用,被引用的优惠券删除失败！");
		}else{
			model.addAttribute("promptmsg","优惠券删除成功！");
		}
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 优惠券排序方法
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.couponService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 优惠券批量删除成功
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
				if(checkCoupon(id)){
					flag = true;
				}else{
					this.couponService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分优惠券已被引用,被引用的优惠券删除失败！");
		}else{
			model.addAttribute("promptmsg","优惠券删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用优惠券
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Coupon coupon  = this.couponService.get(parameter_id);
		coupon.setState("1");
		this.couponService.update(coupon);
		model.addAttribute("promptmsg","优惠券启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用优惠券
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		Coupon coupon  = this.couponService.get(parameter_id);
		coupon.setState("0");
		this.couponService.update(coupon);
		model.addAttribute("promptmsg","优惠券禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用优惠券
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Coupon coupon  = this.couponService.get(id);
			coupon.setState("1");
			this.couponService.update(coupon);
		}
		model.addAttribute("promptmsg","优惠券批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用优惠券
	 * @param
	 * @date 2017-07-11 11:25:34
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			Coupon coupon  = this.couponService.get(id);
			coupon.setState("0");
			this.couponService.update(coupon);
		}
		model.addAttribute("promptmsg","优惠券批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除优惠券时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkCoupon(String coupon_id){
		//摇一摇优惠券已关联进行中的活动不可删除
		Coupon coupon = couponService.get(coupon_id);
		if("1".equals(coupon.getCoupon_type())){
			//判断 是否有被t_account_coupon 引用
			HashMap<String, Object> paraMap = new HashMap<String,Object>();
			//搜索封装
			paraMap.put("coupon_id",coupon_id);
			int count = this.accountCouponService.getCount(paraMap);
			if (count>0){
				return true;
			}
		}
		//判断 是否有被t_account_coupon 引用
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap.put("coupon_id",coupon_id);
		int count = shakeAwardsService.getCount(paraMap);
		if (count>0){
			return true;
		}
		return false;
	}
	
	
	
}


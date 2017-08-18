/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-03 17:03:03  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: ShakeAwardsController.java 
 * Author:chenyi
 */
package com.aten.controller;

import javax.servlet.http.HttpServletRequest;

import com.aten.client.RedisClient;
import com.aten.function.CommparaFuc;
import com.aten.function.SysconfigFuc;
import com.aten.model.bean.ProbabilityBean;
import com.aten.model.orm.*;
import com.aten.service.CouponService;
import com.aten.service.ShakeWinningRecordService;
import com.communal.constants.RedisConstants;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aten.annotation.TokenAnnotation;
import com.communal.util.StringUtil;
import com.aten.service.ShakeAwardsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author chenyi
 * @Function 摇一摇优惠卷奖项设置  controller
 * @date 2017-08-03 17:03:03
 */
@Controller
@RequestMapping("admin/shakeawards")
public class ShakeAwardsController extends BaseController{

	private static final Logger logger = Logger.getLogger(ShakeAwardsController.class);
	
	@Autowired
	private ShakeAwardsService shakeAwardsService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private ShakeWinningRecordService shakeWinningRecordService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
		model.addAttribute("cfg_awards_level", CommparaFuc.getParaList("cfg_awards_level"));
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 摇一摇优惠卷奖项设置列表页方法
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		String shake_id = request.getParameter("parameter_id");
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("shake_id",shake_id);
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.shakeAwardsService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<ShakeAwards> shakeAwardsList = this.shakeAwardsService.getList(paraMap);
		for(int i=0;i<shakeAwardsList.size();i++){
			paraMap.clear();
			paraMap.put("awards_id",shakeAwardsList.get(i).getAwards_id());
			int getNum=shakeWinningRecordService.getCount(paraMap);
			shakeAwardsList.get(i).setGet_num(getNum);
		}
		//设置已领取奖品数量
		model.addAttribute("shakeAwardsList", shakeAwardsList);
		model.addAttribute("parameter_id", shake_id);
		return "shakeAwards/list";
	}



	@RequestMapping("winList")
	public String winList(HttpServletRequest request,Model model){
		String awards_id = request.getParameter("awards_id");
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("awards_id",awards_id);
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.shakeWinningRecordService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<ShakeWinningRecord> shakeWinningRecordList = this.shakeWinningRecordService.getList(paraMap);
		model.addAttribute("shakeWinningRecordList", shakeWinningRecordList);
		model.addAttribute("awards_id",awards_id);
		ShakeAwards shakeAwards=shakeAwardsService.get(awards_id);
		model.addAttribute("shake_id",shakeAwards.getShake_id());
		return "shakeAwards/winList";
	}


	/**
	 * @author chenyi
	 * @Description 跳转到添加摇一摇优惠卷奖项设置页面方法
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(HttpServletRequest request,Model model) {
		String parameter_id = request.getParameter("parameter_id");
		model.addAttribute("parameter_id", parameter_id);
		//获取优惠券列表
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("state", "1");
		params.put("coupon_type","1");
		List<Coupon> couponList= couponService.getList(params);
		model.addAttribute("couponList",couponList);
		return "shakeAwards/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加摇一摇优惠卷奖项设置方法
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(ShakeAwards shakeAwards,HttpServletRequest request,Model model){
		String shake_id=request.getParameter("parameter_id");
		shakeAwards.setShake_id(shake_id);
		//同一个活动  只能有一个相同等级的奖项
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("awards_level", shakeAwards.getAwards_level());
		params.put("shake_id",shake_id);
		List<ShakeAwards> shakeAwardsList= shakeAwardsService.getList(params);
		if (shakeAwardsList!=null&&shakeAwardsList.size()>0){
			model.addAttribute("promptmsg", "该级别奖项已存在！");
			return add(request,model);
		}
		String result=verifyForm(shakeAwards);
		if (!"success".equals(result)) {
			model.addAttribute("promptmsg", result);
			return add(request,model);
		}
		//如果是积分
		if("0".equals(shakeAwards.getPrize_type())){
			shakeAwards.setCoupon_id(null);
		}
		//如果是优惠券
		if("1".equals(shakeAwards.getPrize_type())){
			shakeAwards.setGive_integral(null);
		}
		//设置奖项等级名称
		List<Commpara> list=CommparaFuc.getParaList("cfg_awards_level");
		for (int i=0;i<list.size();i++){
			if(list.get(i).getPara_key().equals(shakeAwards.getAwards_level())){
				shakeAwards.setAwards_level_name(list.get(i).getPara_name());
			}
		}
		this.shakeAwardsService.insert(shakeAwards);
		model.addAttribute("promptmsg","摇一摇优惠卷奖项设置添加成功！");
		return add(request,model);
	}
	/**
	 * @param
	 * @author chenyi
	 * @Description 验证参数
	 * @date 2017-08-03 16:37:16
	 */
	private String verifyForm(ShakeAwards shakeAwards) {
		//如果是积分
		if("0".equals(shakeAwards.getPrize_type())){
			//验证是否是正整数
			if ("".equals(shakeAwards.getGive_integral())) {
				return "赠送积分值不能为空！";
			}
			//验证是否是正整数
			if (!shakeAwards.getGive_integral().matches("^[0-9]*[1-9][0-9]*$")) {
				return "赠送积分值必须为正整数！";
			}
		}
		//如果是优惠券
		if("1".equals(shakeAwards.getPrize_type())){
			//验证是否是正整数
			if ("".equals(shakeAwards.getCoupon_id())) {
				return "赠送优惠券不能为空！";
			}
		}
		if (!shakeAwards.getAwards_probability().matches("^(^[1-9][0-9]$)|(^100&)|(^[1-9]$)$")) {
			return "中奖概率必须是1-99！";
		}
		if (!shakeAwards.getAwards_num().matches("^[0-9]*[1-9][0-9]*$")) {
			return "奖品数量必须为正整数！";
		}
		return "success";

	}
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改摇一摇优惠卷奖项设置页面方法
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("edit/{awardsId}")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model, @PathVariable String awardsId){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		ShakeAwards shakeAwards = this.shakeAwardsService.get(awardsId);
		model.addAttribute("shakeAwards", shakeAwards);
		//获取优惠券列表
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("state", "1");
		params.put("coupon_type","1");
		List<Coupon> couponList= couponService.getList(params);
		model.addAttribute("couponList",couponList);
		model.addAttribute("parameter_id", parameter_id);
		return "shakeAwards/update";
	}

	@RequestMapping("view/{awardsId}")
	@TokenAnnotation(needSaveToken = true)
	public String view(HttpServletRequest request, Model model, @PathVariable String awardsId){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		ShakeAwards shakeAwards = this.shakeAwardsService.get(awardsId);
		model.addAttribute("shakeAwards", shakeAwards);
		//获取优惠券列表
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("state", "1");
		params.put("coupon_type","1");
		List<Coupon> couponList= couponService.getList(params);
		model.addAttribute("couponList",couponList);
		model.addAttribute("parameter_id", parameter_id);
		return "shakeAwards/view";
	}


	/**
	 * @author chenyi
	 * @Description 修改摇一摇优惠卷奖项设置方法
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,ShakeAwards shakeAwards,Model model){
		String shake_id=request.getParameter("parameter_id");
		shakeAwards.setShake_id(shake_id);
		//获取奖项级别原值
		String old_awards_level=request.getParameter("old_awards_level");
		if (!old_awards_level.equals(shakeAwards.getAwards_level())){
			//同一个活动  只能有一个相同等级的奖项
			HashMap<String, Object> params = new HashMap<String,Object>();
			params.put("awards_level", shakeAwards.getAwards_level());
			params.put("shake_id",shake_id);
			List<ShakeAwards> shakeAwardsList= shakeAwardsService.getList(params);
			if (shakeAwardsList!=null&&shakeAwardsList.size()>0){
				model.addAttribute("promptmsg", "该级别奖项已存在！");
				return edit(request,model,shakeAwards.getAwards_id());
			}
		}
		String result=verifyForm(shakeAwards);
		if (!"success".equals(result)) {
			model.addAttribute("promptmsg", result);
			return edit(request,model,shakeAwards.getAwards_id());
		}
		//如果是积分
		if("0".equals(shakeAwards.getPrize_type())){
			shakeAwards.setCoupon_id(null);
		}
		//如果是优惠券
		if("1".equals(shakeAwards.getPrize_type())){
			shakeAwards.setGive_integral(null);
		}
		//设置奖项等级名称
		List<Commpara> list=CommparaFuc.getParaList("cfg_awards_level");
		for (int i=0;i<list.size();i++){
			if(list.get(i).getPara_key().equals(shakeAwards.getAwards_level())){
				shakeAwards.setAwards_level_name(list.get(i).getPara_name());
			}
		}
		shakeAwards.setShake_id(null);
		this.shakeAwardsService.update(shakeAwards);
		model.addAttribute("promptmsg","摇一摇优惠卷奖项设置修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除摇一摇优惠卷奖项设置方法
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("delete/{awardsId}")
	public String delete(HttpServletRequest request,Model model,@PathVariable String awardsId){
		this.shakeAwardsService.deleteOne(awardsId);
		model.addAttribute("promptmsg","摇一摇优惠卷奖项设置删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 摇一摇优惠卷奖项设置排序方法
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.shakeAwardsService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 摇一摇优惠卷奖项设置批量删除成功
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
					this.shakeAwardsService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分摇一摇优惠卷奖项设置已被引用,被引用的摇一摇优惠卷奖项设置删除失败！");
		}else{
			model.addAttribute("promptmsg","摇一摇优惠卷奖项设置删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用摇一摇优惠卷奖项设置
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		ShakeAwards shakeAwards  = this.shakeAwardsService.get(parameter_id);
		//shakeAwards.setState("1");
		this.shakeAwardsService.update(shakeAwards);
		model.addAttribute("promptmsg","摇一摇优惠卷奖项设置启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用摇一摇优惠卷奖项设置
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		ShakeAwards shakeAwards  = this.shakeAwardsService.get(parameter_id);
		//shakeAwards.setState("0");
		this.shakeAwardsService.update(shakeAwards);
		model.addAttribute("promptmsg","摇一摇优惠卷奖项设置禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用摇一摇优惠卷奖项设置
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			ShakeAwards shakeAwards  = this.shakeAwardsService.get(id);
			//shakeAwards.setState("1");
			this.shakeAwardsService.update(shakeAwards);
		}
		model.addAttribute("promptmsg","摇一摇优惠卷奖项设置批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用摇一摇优惠卷奖项设置
	 * @param
	 * @date 2017-08-03 17:03:03
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			ShakeAwards shakeAwards  = this.shakeAwardsService.get(id);
			//shakeAwards.setState("0");
			this.shakeAwardsService.update(shakeAwards);
		}
		model.addAttribute("promptmsg","摇一摇优惠卷奖项设置批量禁用成功！");
		return list(request, model);
	}
	/**
 	 * @author chenyi
	 * * @Description 将中奖概率更新到缓存中
	 * @param
 	* @date 2017-8-7
 	*/
	@RequestMapping("refresh")
	private String refresh(HttpServletRequest request,Model model){
		String shake_id = request.getParameter("parameter_id");
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("shake_id",shake_id);
		List<ShakeAwards> shakeAwardsList= shakeAwardsService.getList(params);
		//中奖概率bean
		List<ProbabilityBean> probabilityBeans=new ArrayList<>();
		int probability;
		int  curr=0;
		int total=0;
		for (int i=0;i<shakeAwardsList.size();i++){
			total+=Integer.parseInt(shakeAwardsList.get(i).getAwards_probability());
			ProbabilityBean Probability=new ProbabilityBean();
			//中奖概率
			Probability.setAwardName(shakeAwardsList.get(i).getAwards_name());
			Probability.setAwardLevelName(shakeAwardsList.get(i).getAwards_level_name());
			Probability.setAwardId(shakeAwardsList.get(i).getAwards_id());
			Probability.setShakeId(shake_id);
			probabilityBeans.add(Probability);
			probability=Integer.parseInt(shakeAwardsList.get(i).getAwards_probability());
			Probability.setMinNum(curr+1);
			curr+=probability;
			Probability.setMaxNum(curr);
		}
		if(total>100){
			model.addAttribute("promptmsg","总概率大于100,请重新设置中奖概率！");
		}else{
			String json = JSONArray.fromObject(probabilityBeans).toString();
			//保存到redis中
			RedisClient.addStr(RedisConstants.SHAKE+shake_id,json);
			model.addAttribute("promptmsg","刷新成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 验证删除摇一摇优惠卷奖项设置时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String awards_id){
		return false;
	}
	
	
	
}


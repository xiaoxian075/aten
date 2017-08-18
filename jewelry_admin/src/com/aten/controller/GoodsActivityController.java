/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-07-14 20:04:06  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: GoodsActivityController.java 
 * Author:chenyi
 */
package com.aten.controller;

import com.aten.annotation.TokenAnnotation;
import com.aten.model.orm.GoodsActivity;
import com.aten.model.orm.GoodsActivityMap;
import com.aten.service.GoodsActivityMapService;
import com.aten.service.GoodsActivityService;
import com.communal.util.AmountUtil;
import com.communal.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 商品活动  controller
 * @date 2017-07-14 20:04:06
 */
@Controller
@RequestMapping("admin/goodsactivity")
public class GoodsActivityController extends BaseController {

	private static final Logger logger = Logger.getLogger(GoodsActivityController.class);
	
	@Autowired
	private GoodsActivityService goodsActivityService;
	@Autowired
	private GoodsActivityMapService goodsActivityMapService;
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@ModelAttribute
    public void populateModel(HttpServletRequest request, Model model) {
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 商品活动列表页方法
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model){
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.goodsActivityService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<GoodsActivity> goodsActivityList = this.goodsActivityService.getList(paraMap);
		for(GoodsActivity goodsActivity:goodsActivityList){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date now=new Date();
				Date start_time=sdf.parse(goodsActivity.getStart_time());
				Date end_time=sdf.parse(goodsActivity.getEnd_time());
				//开始时间大于当前时间  未开始
				if(start_time.getTime()>now.getTime()){
					goodsActivity.setState("0");//未开始
				}
				//开始时间小于当前时间  进行中
				if(start_time.getTime()<=now.getTime()){
					goodsActivity.setState("1");//进行中
				}
				//结束时间小于当前时间  已过期
				if(end_time.getTime()<now.getTime()){
					goodsActivity.setState("2");//已过期
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("goodsActivityList", goodsActivityList);
		return "goodsActivity/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加商品活动页面方法
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "goodsActivity/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加商品活动方法
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("insert")
	public void insert(HttpServletResponse response, GoodsActivity goodsActivity, Model model){
		boolean flag=true;
		// 折扣
		if("1".equals(goodsActivity.getActivity_type())) {
			String regEx = "^(\\d(\\.\\d)?|10)$";
			if (!goodsActivity.getDiscount().matches(regEx)) {
				//model.addAttribute("promptmsg", "折扣数值必须为0~10,精确到0.1！");
				//return "折扣数值必须为0~10,精确到0.1！";
				outPrint(response, "折扣数值必须为0~10,精确到0.1！");
				flag=false;
			}
		}
		//黄金特惠
		if("2".equals(goodsActivity.getActivity_type())) {
			String regEx = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
			if (!goodsActivity.getDiscount().matches(regEx)) {
				//model.addAttribute("promptmsg", "请输入正确的金额！");
				outPrint(response, "请输入正确的金额！");
				flag=false;
			}
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date start=sdf.parse(goodsActivity.getStart_time());
			Date end=sdf.parse(goodsActivity.getEnd_time());
			if(start.getTime()<new Date().getTime()){
				//model.addAttribute("promptmsg","活动开始时间要大于当前！");
				outPrint(response, "活动开始时间要大于当前！");
				flag=false;
			}
			if(start.getTime()>=end.getTime()){
				//model.addAttribute("promptmsg","结束时间要大于开始时间！");
				outPrint(response, "结束时间要大于开始时间！");
				flag=false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (flag){
			this.goodsActivityService.saveInfo(goodsActivity);
			outPrint(response, "success");
		}
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改商品活动页面方法
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request, Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		GoodsActivity goodsActivity = this.goodsActivityService.get(parameter_id);
		//分类关联的品牌
		List<GoodsActivityMap> goodsList = getGoodsList(request, model, goodsActivity);
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("isEdit",true);
		if("2".equals(goodsActivity.getActivity_type())){
			goodsActivity.setDiscount(AmountUtil.formatMoney(goodsActivity.getDiscount()));
		}
		model.addAttribute("goodsActivity", goodsActivity);
		return "goodsActivity/update";
	}

	private List<GoodsActivityMap> getGoodsList(HttpServletRequest request, Model model, GoodsActivity goodsActivity) {
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		//搜索封装
		paraMap = searchMap(request, paraMap, model);
		paraMap.put("activity_id", goodsActivity.getActivity_id());
		//分页工具
		List<GoodsActivityMap> goodsList = goodsActivityMapService.getList(paraMap);
		for(GoodsActivityMap goods:goodsList){
			//设置原价金额
			goods.setPrice(AmountUtil.formatMoney(goods.getFixed_price()));
			//设置活动价
			//如果是限时折扣
			if("1".equals(goodsActivity.getActivity_type())){
				//折扣
				float discount=Float.parseFloat(goodsActivity.getDiscount());
				String activity_price=Integer.parseInt(goods.getFixed_price())*discount/10+"";
				goods.setActivity_price(AmountUtil.formatMoney(activity_price));
			}
			//如果是黄金特惠
			if("2".equals(goodsActivity.getActivity_type())){
				//折扣
				float discount=Float.parseFloat(goodsActivity.getDiscount());
				float activity_price=Float.parseFloat(goods.getFixed_price())-discount;
				if(activity_price<0){
					activity_price=0;
				}
				goods.setActivity_price(AmountUtil.formatMoney(activity_price+""));
			}
            //如果是免手工费
			if("3".equals(goodsActivity.getActivity_type())){
				goods.setActivity_price("免手工费");
			}
		}
		return goodsList;
	}


	/**
	 * @author chenyi
	 * @Description 修改商品活动方法
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request, GoodsActivity goodsActivity, Model model){
//		String parameter_id = request.getParameter("parameter_id");
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		try {
//			Date start=sdf.parse(goodsActivity.getStart_time());
//			Date end=sdf.parse(goodsActivity.getEnd_time());
//			if(start.getTime()<new Date().getTime()){
//				model.addAttribute("promptmsg","活动开始时间要大于当前！");
//				return edit(request,model);
//			}
//			if(start.getTime()>=end.getTime()){
//				model.addAttribute("promptmsg","结束时间要大于开始时间！");
//				return edit(request,model);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		this.goodsActivityService.updateInfo(goodsActivity);
		model.addAttribute("promptmsg","商品活动修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除商品活动方法
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.goodsActivityService.delete(parameter_id);
		model.addAttribute("promptmsg","商品活动删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 商品活动排序方法
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request, Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.goodsActivityService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 商品活动批量删除成功
	 * @param
	 * @date 2017-5-3 下午4:09:55
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(HttpServletRequest request, Model model){
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
					this.goodsActivityService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分商品活动已被引用,被引用的商品活动删除失败！");
		}else{
			model.addAttribute("promptmsg","商品活动删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用商品活动
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request, Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		GoodsActivity goodsActivity  = this.goodsActivityService.get(parameter_id);
		goodsActivity.setActivity_state("1");
//		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		goodsActivity.setStart_time(sdf.format(new Date()));
		this.goodsActivityService.enableState(goodsActivity);
		model.addAttribute("promptmsg","启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用商品活动
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request, Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		GoodsActivity goodsActivity  = this.goodsActivityService.get(parameter_id);
		goodsActivity.setActivity_state("0");
//		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		goodsActivity.setEnd_time(sdf.format(new Date()));
		this.goodsActivityService.limitState(goodsActivity);
		model.addAttribute("promptmsg","禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用商品活动
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request, Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			GoodsActivity goodsActivity  = this.goodsActivityService.get(id);
			goodsActivity.setState("1");
			//goodsActivityService.update(goodsActivity);
			this.goodsActivityService.enableState(goodsActivity);
		}
		model.addAttribute("promptmsg","商品活动批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用商品活动
	 * @param
	 * @date 2017-07-14 20:04:06
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request, Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			GoodsActivity goodsActivity  = this.goodsActivityService.get(id);
			goodsActivity.setState("0");
			//goodsActivityService.update(goodsActivity);
			this.goodsActivityService.limitState(goodsActivity);
		}
		model.addAttribute("promptmsg","商品活动批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除商品活动时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String id){
		return false;
	}
	
	
	
}


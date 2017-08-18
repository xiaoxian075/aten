/*
 * 系统名称:艾腾云商珠宝平台
 * (C)  Copyright aten  2017-08-03 17:00:12  Corporation 
 * All rights reserved.
 * Package:com.aten.controller
 * FileName: ShakeWinningRecordController.java 
 * Author:chenyi
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
import com.communal.util.StringUtil;
import com.aten.model.orm.ShakeWinningRecord;
import com.aten.service.ShakeWinningRecordService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author chenyi
 * @Function 摇一摇优惠卷中奖记录  controller
 * @date 2017-08-03 17:00:12
 */
@Controller
@RequestMapping("admin/shakewinningrecord")
public class ShakeWinningRecordController extends BaseController{

	private static final Logger logger = Logger.getLogger(ShakeWinningRecordController.class);
	
	@Autowired
	private ShakeWinningRecordService shakeWinningRecordService;
	
	/**
	 * @author chenyi
	 * @Description 初始方法
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@ModelAttribute  
    public void populateModel(HttpServletRequest request,Model model) { 
		initialHiddenVal(request,model);
    }  
	
	
	/**
	 * @author chenyi
	 * @Description 摇一摇优惠卷中奖记录列表页方法
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		String shake_id = request.getParameter("parameter_id");
		HashMap<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("shake_id",shake_id);
		//搜索封装
		paraMap = searchMap(request,paraMap,model);
		int count = this.shakeWinningRecordService.getCount(paraMap);
		//分页工具
		paraMap = pageTool(request,count,model,paraMap);
		List<ShakeWinningRecord> shakeWinningRecordList = this.shakeWinningRecordService.getList(paraMap);
		model.addAttribute("shakeWinningRecordList", shakeWinningRecordList);
		model.addAttribute("parameter_id",shake_id);
		return "shakeWinningRecord/list";
	}
	
	
	
	
	 /**
	 * @author chenyi
	 * @Description 跳转到添加摇一摇优惠卷中奖记录页面方法
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("add")
	@TokenAnnotation(needSaveToken = true)
	public String add(Model model){
		return "shakeWinningRecord/insert";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 添加摇一摇优惠卷中奖记录方法
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("insert")
	@TokenAnnotation(needRemoveToken = true)
	public String insert(ShakeWinningRecord shakeWinningRecord,Model model){
		this.shakeWinningRecordService.insert(shakeWinningRecord);
		model.addAttribute("promptmsg","摇一摇优惠卷中奖记录添加成功！");
		return add(model);
	}
	
	
		
	/**
	 * @author chenyi
	 * @Description 跳转到修改摇一摇优惠卷中奖记录页面方法
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("edit")
	@TokenAnnotation(needSaveToken = true)
	public String edit(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//获取对象
		ShakeWinningRecord shakeWinningRecord = this.shakeWinningRecordService.get(parameter_id);
		model.addAttribute("shakeWinningRecord", shakeWinningRecord);
		return "shakeWinningRecord/update";
	}
	
	
	/**
	 * @author chenyi
	 * @Description 修改摇一摇优惠卷中奖记录方法
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("update")
	@TokenAnnotation(needRemoveToken = true)
	public String update(HttpServletRequest request,ShakeWinningRecord shakeWinningRecord,Model model){
		this.shakeWinningRecordService.update(shakeWinningRecord);
		model.addAttribute("promptmsg","摇一摇优惠卷中奖记录修改成功！");
		return list(request,model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 删除摇一摇优惠卷中奖记录方法
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		this.shakeWinningRecordService.deleteOne(parameter_id);
		model.addAttribute("promptmsg","摇一摇优惠卷中奖记录删除成功！");
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 摇一摇优惠卷中奖记录排序方法
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("sort")
	public String sort(HttpServletRequest request,Model model){
		String sort_id = request.getParameter("sort_id");
		String sort_val = request.getParameter("sort_val");
		if(sort_id==null || sort_val==null) return null;
		// 转成maplist
		List<Map<String, String>> sortMapList = sortStrToMapList(sort_id,sort_val);
		this.shakeWinningRecordService.updateBatch(sortMapList);
		return list(request, model);
	}
	
	
	
	/**
	 * @author chenyi
	 * @Description 摇一摇优惠卷中奖记录批量删除成功
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
					this.shakeWinningRecordService.deleteOne(id);
				}
			}
		}
		//判断标志提示
		if(flag){
			model.addAttribute("promptmsg","部分摇一摇优惠卷中奖记录已被引用,被引用的摇一摇优惠卷中奖记录删除失败！");
		}else{
			model.addAttribute("promptmsg","摇一摇优惠卷中奖记录删除成功！");
		}
		return list(request, model);
	}
	
	/**
	 * @author chenyi
	 * @Description 启用摇一摇优惠卷中奖记录
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("enableState")
	public String enableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		ShakeWinningRecord shakeWinningRecord  = this.shakeWinningRecordService.get(parameter_id);
		//shakeWinningRecord.setState("1");
		this.shakeWinningRecordService.update(shakeWinningRecord);
		model.addAttribute("promptmsg","摇一摇优惠卷中奖记录启用成功！");
		return list(request, model);
	}
	
	
	/**
	 * @author chenyi
	 * @Description 禁用摇一摇优惠卷中奖记录
	 * @param
	 * @date 2017-5-3 下午4:04:28
	 */
	@RequestMapping("limitState")
	public String limitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		ShakeWinningRecord shakeWinningRecord  = this.shakeWinningRecordService.get(parameter_id);
		//shakeWinningRecord.setState("0");
		this.shakeWinningRecordService.update(shakeWinningRecord);
		model.addAttribute("promptmsg","摇一摇优惠卷中奖记录禁用成功！");
		return list(request, model);
	}

	/**
	 * @author chenyi
	 * @Description 批量启用摇一摇优惠卷中奖记录
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("batchEnableState")
	public String batchEnableState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			ShakeWinningRecord shakeWinningRecord  = this.shakeWinningRecordService.get(id);
			//shakeWinningRecord.setState("1");
			this.shakeWinningRecordService.update(shakeWinningRecord);
		}
		model.addAttribute("promptmsg","摇一摇优惠卷中奖记录批量启用成功！");
		return list(request, model);
	}
	

	/**
	 * @author chenyi
	 * @Description 批量禁用摇一摇优惠卷中奖记录
	 * @param
	 * @date 2017-08-03 17:00:12
	 */
	@RequestMapping("batchLimitState")
	public String batchLimitState(HttpServletRequest request,Model model){
		String parameter_id = request.getParameter("parameter_id");
		if(parameter_id==null ||parameter_id.equals("")) return list(request,model);
		//转成数组更新
		String[] ids = parameter_id.split(",");
		for(String id:ids){
			ShakeWinningRecord shakeWinningRecord  = this.shakeWinningRecordService.get(id);
			//shakeWinningRecord.setState("0");
			this.shakeWinningRecordService.update(shakeWinningRecord);
		}
		model.addAttribute("promptmsg","摇一摇优惠卷中奖记录批量禁用成功！");
		return list(request, model);
	}
	
	
	
	
	/**
	 * @author chenyi
	 * @Description 验证删除摇一摇优惠卷中奖记录时是否被引用
	 * @param
	 * @return true 被引用  false 未引用
	 * @date 2017-5-9 下午8:53:58
	 */
	private boolean checkBrand(String wr_id){
		return false;
	}
	
	
	
}


/*
 * 系统名称:艾腾云返汽车平台
 * (C)  Copyright aten  Tue Jan 03 17:28:02 CST 2017  Corporation 
 * All rights reserved.
 * Package:com.admin.controller
 * FileName: MessageController.java 
 * Author:linjunqin
 */
package com.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.admin.model.BaseExample;
import com.admin.model.Business;
import com.admin.model.Message;
import com.admin.service.MessageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.mybatis.DataTablesPage;


@Controller
@RequestMapping(value = "/admin/message")
public class MessageController{

	private static final Logger logger = Logger.getLogger(MessageController.class);
	
	@Resource
	private MessageService messageService;
	
	/**
	 * @author linjunqin
	 * @Description 系统消息后台列表
	 * @param
	 * @date 2017-4-1 下午4:18:08
	 */ 
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String list(Model model){
		 return "admin/message/index";
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 跳转到新增页面
	 * @param
	 * @date 2017-1-4 上午9:16:39
	 */
	@RequestMapping("add")
	public String add(Model model){
		return "area/insert";
	}
	
	/**
	 * @author linjunqin
	 * @Description 获取消息列表的数据
	 * @param
	 * @date 2017-4-5 上午11:51:48
	 */
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    @ResponseBody
    public String getList(DataTablesPage<Message> page, Model model, Business business) {
        try {
            BaseExample baseExample = new BaseExample();
            BaseExample.Criteria criteria = baseExample.createCriteria();
            //搜索条件
            //criteria.andEqualTo("1", "1");
            //baseExample.setOrderByClause("CREATE_TIME desc ");
            messageService.selectByExampleAndPage(page, baseExample);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(page, SerializerFeature.WriteMapNullValue);
    }
	
	/**
	 * @author linjunqin
	 * @Description 添加消息
	 * @param
	 * @date 2017-1-4 上午9:17:49
	 */
	@RequestMapping("insert")
	public String insert(Message message,Model model){
		this.messageService.insert(message);
		return add(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 跳转到更新页面
	 * @param
	 * @date 2017-1-4 上午9:33:03
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		if(id==null) return null;
		//获取当前菜单的对象
		Message message = this.messageService.selectById(id);
		model.addAttribute("message",message);
		return "update";
	}
	
	/**
	 * @author linjunqin
	 * @Description 更新消息
	 * @param
	 * @date 2017-1-4 上午9:35:17
	 */
	@RequestMapping("update")
	public String update(Message message,Model model){
		this.messageService.update(message);
		return list(model);
	}
	
	/**
	 * @author linjunqin
	 * @Description 删除消息
	 * @param
	 * @date 2017-1-4 上午9:36:13
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		this.messageService.delete(id);
		return list(model);
	}
	
	
}


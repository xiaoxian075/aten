package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Business;
import com.admin.model.Message;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.HashMap;
import java.util.List;

/**
 * 功能描述：
 * 作者： linym
 * 时间：2016/2/25
 */
public interface MessageService  extends GenericService<Message,String> {
	
	/**
	 * @author linjunqin
	 * @Description 删除消息
	 * @param
	 * @date 2017-4-1 下午3:58:36
	 */
	public void deleteMessage(String msg_id);
	
	/**
	 * @author linjunqin
	 * @Description 搜索数据
	 * @param
	 * @date 2017-4-1 下午4:00:01
	 */
	public void selectMessageListByMap(HashMap<String, String> paraMap);
	
	/**
	 * @author linjunqin
	 * @Description 根据条件获取分页的数据
	 * @param
	 * @date 2017-4-5 上午11:55:10
	 */
	List<Message> selectByExampleAndPage(DataTablesPage<Message> page, BaseExample baseExample);
		
}


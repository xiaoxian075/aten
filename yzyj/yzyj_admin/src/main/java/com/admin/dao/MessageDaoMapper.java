package com.admin.dao;

import com.admin.model.*;
import com.admin.vo.UserVo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 功能描述：
 * 作者： linym
 * 时间：2016/2/25
 */
public interface MessageDaoMapper extends GenericDao<Message,String> {
   
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
	 * @Description 根据分页获取数据
	 * @param
	 * @date 2017-4-5 上午11:56:39
	 */
	List<Message> selectByExampleAndPage(DataTablesPage<Message> page, BaseExample baseExample);
}

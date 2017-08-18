package com.admin.service.impl;

import com.admin.dao.MessageDaoMapper;
import com.admin.dao.UserDaoMapper;
import com.admin.model.*;
import com.admin.service.MessageService;
import com.admin.service.UserService;
import com.admin.vo.MenuVo;
import com.admin.vo.UserVo;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 功能描述：
 * 作者： linym
 * 时间：2016/2/25
 */
@Service
public class MessageServiceImpl extends GenericServiceImpl<Message, String> implements MessageService {
    @Resource
    private MessageDaoMapper messageDaoMapper;

    @Override
    public GenericDao<Message, String> getDao() {
        return null;
    }

    /**
	 * @author linjunqin
	 * @Description 删除消息
	 * @param
	 * @date 2017-4-1 下午3:58:36
	 */
	@Override
	public void deleteMessage(String msg_id) {
		this.messageDaoMapper.deleteMessage(msg_id);
	}

	/**
	 * @author linjunqin
	 * @Description 搜索数据
	 * @param
	 * @date 2017-4-1 下午4:00:01
	 */
	@Override
	public void selectMessageListByMap(HashMap<String, String> paraMap) {
		this.selectMessageListByMap(paraMap);
	}

	/**
	 * @author linjunqin
	 * @Description 根据分页获取数据
	 * @param
	 * @date 2017-4-5 上午11:56:39
	 */
	@Override
	public List<Message> selectByExampleAndPage(DataTablesPage<Message> page,
			BaseExample baseExample) {
		return this.messageDaoMapper.selectByExampleAndPage(page, baseExample);
	}
}

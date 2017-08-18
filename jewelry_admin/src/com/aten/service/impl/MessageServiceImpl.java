package com.aten.service.impl;

import com.aten.model.orm.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.MessageDao;
import com.aten.service.MessageService;

@Service("messageService")
public class MessageServiceImpl extends CommonServiceImpl<Message,String> implements MessageService{

	private MessageDao messageDao;

	@Autowired
	public MessageServiceImpl(MessageDao messageDao) {
		super(messageDao);
		this.messageDao=messageDao;
	}

}





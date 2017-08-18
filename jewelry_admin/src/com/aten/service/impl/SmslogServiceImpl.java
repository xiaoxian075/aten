package com.aten.service.impl;

import com.aten.model.orm.Smslog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.SmslogDao;
import com.aten.service.SmslogService;

@Service("smslogService")
public class SmslogServiceImpl extends CommonServiceImpl<Smslog,String> implements SmslogService{

	@Autowired
	public SmslogServiceImpl(SmslogDao smslogDao) {
		super(smslogDao);
	}

}





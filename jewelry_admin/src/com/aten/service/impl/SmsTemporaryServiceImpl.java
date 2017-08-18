package com.aten.service.impl;

import com.aten.model.orm.SmsTemporary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.SmsTemporaryDao;
import com.aten.service.SmsTemporaryService;

@Service("smsTemporaryService")
public class SmsTemporaryServiceImpl extends CommonServiceImpl<SmsTemporary,String> implements SmsTemporaryService{

	@Autowired
	public SmsTemporaryServiceImpl(SmsTemporaryDao smsTemporaryDao) {
		super(smsTemporaryDao);
	}

}





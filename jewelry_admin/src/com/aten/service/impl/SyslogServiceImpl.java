package com.aten.service.impl;

import com.aten.model.orm.Syslog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.SyslogDao;
import com.aten.service.SyslogService;

@Service("syslogService")
public class SyslogServiceImpl extends CommonServiceImpl<Syslog,String> implements SyslogService{

	@Autowired
	public SyslogServiceImpl(SyslogDao syslogDao) {
		super(syslogDao);
	}


}





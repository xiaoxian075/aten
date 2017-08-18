package com.aten.service.impl;

import com.aten.model.orm.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.LoginLogDao;
import com.aten.service.LoginLogService;

@Service("loginlogService")
public class LoginLogServiceImpl extends CommonServiceImpl<LoginLog,String> implements LoginLogService{

	@Autowired
	public LoginLogServiceImpl(LoginLogDao loginlogDao) {
		super(loginlogDao);
	}

}





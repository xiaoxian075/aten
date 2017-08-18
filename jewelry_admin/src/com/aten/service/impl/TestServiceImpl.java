package com.aten.service.impl;

import com.aten.model.orm.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.TestDao;
import com.aten.service.TestService;

@Service("testService")
public class TestServiceImpl extends CommonServiceImpl<Test,String> implements TestService{

	private TestDao testDao;

	@Autowired
	public TestServiceImpl(TestDao testDao) {
		super(testDao);
		this.testDao=testDao;
	}

}





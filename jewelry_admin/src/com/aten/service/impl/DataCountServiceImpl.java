package com.aten.service.impl;

import com.aten.model.orm.DataCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.DataCountDao;
import com.aten.service.DataCountService;

@Service("dataCountService")
public class DataCountServiceImpl extends CommonServiceImpl<DataCount,String> implements DataCountService{

	private DataCountDao dataCountDao;

	@Autowired
	public DataCountServiceImpl(DataCountDao dataCountDao) {
		super(dataCountDao);
		this.dataCountDao=dataCountDao;
	}

}





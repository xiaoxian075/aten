package com.aten.service.impl;

import com.aten.model.orm.DivideRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.DivideRecordDao;
import com.aten.service.DivideRecordService;

@Service("divideRecordService")
public class DivideRecordServiceImpl extends CommonServiceImpl<DivideRecord,String> implements DivideRecordService{

	private DivideRecordDao divideRecordDao;

	@Autowired
	public DivideRecordServiceImpl(DivideRecordDao divideRecordDao) {
		super(divideRecordDao);
		this.divideRecordDao=divideRecordDao;
	}

}





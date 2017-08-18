package com.aten.service.impl;

import com.aten.model.orm.ShakeWinningRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.ShakeWinningRecordDao;
import com.aten.service.ShakeWinningRecordService;

@Service("shakeWinningRecordService")
public class ShakeWinningRecordServiceImpl extends CommonServiceImpl<ShakeWinningRecord,String> implements ShakeWinningRecordService{

	private ShakeWinningRecordDao shakeWinningRecordDao;

	@Autowired
	public ShakeWinningRecordServiceImpl(ShakeWinningRecordDao shakeWinningRecordDao) {
		super(shakeWinningRecordDao);
		this.shakeWinningRecordDao=shakeWinningRecordDao;
	}

}





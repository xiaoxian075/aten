package com.aten.service.impl;

import com.aten.model.orm.BillFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.BillFlowDao;
import com.aten.service.BillFlowService;
import org.springframework.transaction.annotation.Transactional;

@Service("billFlowService")
@Transactional
public class BillFlowServiceImpl extends CommonServiceImpl<BillFlow,String> implements BillFlowService{

	private BillFlowDao billFlowDao;

	@Autowired
	public BillFlowServiceImpl(BillFlowDao billFlowDao) {
		super(billFlowDao);
		this.billFlowDao=billFlowDao;
	}

}





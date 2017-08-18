package com.aten.service.impl;

import com.aten.model.orm.Brand;
import com.aten.model.orm.Supply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.communal.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.SupplyDao;
import com.aten.service.SupplyService;

@Service("supplyService")
public class SupplyServiceImpl extends CommonServiceImpl<Supply,String> implements SupplyService{

	private SupplyDao supplyDao;

	@Autowired
	public SupplyServiceImpl(SupplyDao supplyDao) {
		super(supplyDao);
		this.supplyDao=supplyDao;
	}

	@Override
	public int updateStatus(String supply_id, String status) {
		int iStatus = Integer.valueOf(status);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("supply_id", supply_id);
		param.put("status", iStatus);
		return supplyDao.updateStatus(param);		
	}

	@Override
	public List<Supply> queryList(Query query) {
		return supplyDao.queryList(query);
	}
	public List<Supply>selectByCatId(String catId){
		return supplyDao.selectByCatId(catId);
	}

}





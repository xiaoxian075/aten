package com.aten.service.impl;

import com.aten.dao.GoodsActivityMapDao;
import com.aten.model.orm.GoodsActivityMap;
import com.aten.service.GoodsActivityMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("goodsActivityMapService")
public class GoodsActivityMapServiceImpl extends CommonServiceImpl<GoodsActivityMap,String> implements GoodsActivityMapService {

	private GoodsActivityMapDao goodsActivityMapDao;

	@Autowired
	public GoodsActivityMapServiceImpl(GoodsActivityMapDao goodsActivityMapDao) {
		super(goodsActivityMapDao);
		this.goodsActivityMapDao=goodsActivityMapDao;
	}

	@Override
	public void deleteGoodsActivity(String activity_id) {
		goodsActivityMapDao.deleteGoodsActivity(activity_id);
	}

	@Override
	public List<GoodsActivityMap> findAll(String activity_id) {
		return goodsActivityMapDao.findAll(activity_id);
	}

	@Override
	public List<GoodsActivityMap>  findByActivityId(String activity_id) {
		return goodsActivityMapDao.findByActivityId(activity_id);
	}
}





package com.aten.service.impl;

import com.aten.model.orm.Sysconfig;
import com.aten.model.vo.GoodsUpdateVo;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.SysconfigDao;
import com.aten.service.SysconfigService;
import org.springframework.transaction.annotation.Transactional;

@Service("sysconfigService")
@Transactional
public class SysconfigServiceImpl extends CommonServiceImpl<Sysconfig,String> implements SysconfigService{

	@Autowired
	public SysconfigServiceImpl(SysconfigDao sysconfigDao) {
		super(sysconfigDao);
	}
	@Autowired
	private SysconfigDao sysconfigDao;
	@Override
	public void SaveIntegral(String sign_id, String sign_value, String share_id, String share_value) {
		//保存签到积分设置
		Sysconfig sign=get(sign_id);
		sign.setVar_value(sign_value);
		update(sign);
		//保存分享积分设置
		Sysconfig share=get(share_id);
		share.setVar_value(share_value);
		update(share);
	}

	@Override
	public void SaveGold(String price_id, String price_value,String gold_id, String gold_value) {
		//保存关联商品类目
		Sysconfig gold=get(gold_id);
		gold.setVar_value(gold_value);
		//保存当日金价
		Sysconfig price=get(price_id);
		price.setVar_value(price_value);
		update(price);
		update(gold);

	}
	
	@Override
	public List<GoodsUpdateVo> getGoldValueList(String goldValue) {
		return sysconfigDao.getGoldValueList(goldValue);
	}

	@Override
	public List<GoodsUpdateVo> getCustomValue(int goods_id) {
		return sysconfigDao.getCustomValue(goods_id);
	}

	@Override
	public void updateGoldsPrice(String custom_attr_value, String result_value,int goods_id) {
		sysconfigDao.updateGoldsPrice(custom_attr_value,result_value,goods_id);
	}
}





package com.aten.service.impl;

import com.aten.model.orm.CouponUseCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CouponUseCatDao;
import com.aten.service.CouponUseCatService;

@Service("couponUseCatService")
public class CouponUseCatServiceImpl extends CommonServiceImpl<CouponUseCat,String> implements CouponUseCatService{

	private CouponUseCatDao couponUseCatDao;

	@Autowired
	public CouponUseCatServiceImpl(CouponUseCatDao couponUseCatDao) {
		super(couponUseCatDao);
		this.couponUseCatDao=couponUseCatDao;
	}

}





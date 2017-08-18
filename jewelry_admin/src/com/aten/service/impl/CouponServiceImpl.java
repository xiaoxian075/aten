package com.aten.service.impl;

import com.aten.function.SysconfigFuc;
import com.aten.model.orm.*;
import com.aten.service.CouponUseCatService;
import com.aten.service.GoodsService;
import com.communal.util.ChineseToEnglishUtil;
import com.communal.util.Query;
import com.communal.util.RandomCharUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CouponDao;
import com.aten.service.CouponService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("couponService")
@Transactional
public class CouponServiceImpl extends CommonServiceImpl<Coupon,String> implements CouponService{

	private CouponDao couponDao;
	@Autowired
   private GoodsService goodsService;
	@Autowired
	private CouponUseCatService couponUseCatService;
	@Autowired
	public CouponServiceImpl(CouponDao couponDao) {
		super(couponDao);
		this.couponDao=couponDao;
	}

	@Override
	public void saveInfo(Coupon coupon) {
		Date now= new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		coupon.setCreate_time(sdf.format(now));
		coupon.setState("1");
		coupon.setLast_time(sdf.format(now));
		//面值转换
		coupon.setCoupon_amount(Double.valueOf(coupon.getCoupon_amount())*100+"");
		//使用门槛转换
		coupon.setUse_amount(Double.valueOf(coupon.getUse_amount())*100+"");
		insertGetPk(coupon);
		//使用类型  1 全部商品, 2 指定区域(商品)
		// 如果是指定区域(商品)
		if("2".equals(coupon.getUse_type())){
			//保存关联的数据
			SaveRelationDate(coupon);
		}
	}

	@Override
	public List<Coupon> queryList(Query query) {
		return couponDao.queryList(query);
	}

	public void SaveRelationDate(Coupon coupon){
		Date now= new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//添加前台分类关联的商品分类
		String[] goodsIds=coupon.getGoodsIds();
		if (goodsIds!=null&&goodsIds.length>0){
			for (String goodsId:goodsIds){
				CouponUseCat couponUseCat =new CouponUseCat();
				couponUseCat.setCoupon_id(coupon.getCoupon_id());
				couponUseCat.setCreate_time(sdf.format(now));
				couponUseCat.setUse_cat_id(goodsId);
				Goods goods=goodsService.get(goodsId);
				couponUseCat.setUse_cat_name(goods.getGoods_name());
				couponUseCatService.insert(couponUseCat);
			}
		}

	}

}





package com.aten.function;


import com.aten.model.vo.DiscountData;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author linjunqin
 * @Description  活动优惠价格计算
 * @param
 * @date 2017年7月15日 上午10:38:08 
 */
public class DiscountFuc {

	
	/**
	 * @author linjunqin
	 * @Description  根据价格与活动数据模型计算优惠后的价格
	 *   price不含手续费价格   salePrice 销售价
	 * @date 2017年7月15日 上午11:12:06
	 */
	public static Long dealDiscountPrice(DiscountData ds, Long salePrice){
		 return dealDiscountPrice(ds,salePrice,salePrice);
	}
	public static Long dealDiscountPrice(DiscountData ds,Long price,Long salePrice){
		Long disCountPirce = salePrice;
		//无活动
		if(ds.getActivityType()==0){
			//return disCountPirce;
		}else if(ds.getActivityType()==1){//1: 限时折扣 比例%
			String disCountAmount = String.valueOf(ds.getDiscountAmount());//暂时先用10代替 ds.getDiscountAmount();
			disCountPirce = (new BigDecimal(salePrice).multiply(new BigDecimal(disCountAmount).divide(new BigDecimal(10)))).longValue();
		}else if(ds.getActivityType()==2){//2.黄金特惠 减金额（小于商品价格）
			String disCountAmount = ds.getDiscountAmount();//暂时先用10代替 ds.getDiscountAmount();
			disCountPirce = (new BigDecimal(salePrice).subtract(new BigDecimal(disCountAmount))).longValue();
		}else if(ds.getActivityType()==3){//3.手工费
			disCountPirce =price;
		}
		return disCountPirce;
	}
	
	
}

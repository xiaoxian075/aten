package com.aten.service;

import com.aten.model.orm.Coupon;
import com.communal.util.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public  interface CouponService extends CommonService<Coupon, String>{


    void saveInfo(Coupon coupon);

    List<Coupon> queryList(Query query);
}


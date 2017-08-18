package com.aten.dao;
import com.aten.model.orm.Coupon;
import com.communal.util.Query;

import java.util.List;

public interface CouponDao extends CommonDao<Coupon, String>{

    List<Coupon> queryList(Query query);
}



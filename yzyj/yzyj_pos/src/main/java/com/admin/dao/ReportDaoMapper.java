package com.admin.dao;

import com.admin.model.Order;
import com.core.generic.GenericDao;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface ReportDaoMapper extends GenericDao<Order,String> {

    List<java.util.HashMap> selectPrintCollect(Order order);

    List<Order> selectTodayLastOne(Order order);
}

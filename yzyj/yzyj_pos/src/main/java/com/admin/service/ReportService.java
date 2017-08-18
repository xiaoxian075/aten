package com.admin.service;

import com.admin.model.Order;
import com.core.generic.GenericService;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface ReportService extends GenericService<Order,String> {


    List<java.util.HashMap> selectPrintCollect(Order order);

    List<Order> selectTodayLastOne(Order order);

}

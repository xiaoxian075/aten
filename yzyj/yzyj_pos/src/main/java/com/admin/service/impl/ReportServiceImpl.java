package com.admin.service.impl;

import com.admin.dao.ReportDaoMapper;
import com.admin.model.Order;
import com.admin.service.ReportService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class ReportServiceImpl extends GenericServiceImpl<Order, String> implements ReportService {
    @Resource
    private ReportDaoMapper reportDaoMapper;
    @Override
    public GenericDao<Order, String> getDao() {
        return reportDaoMapper;
    }

    public List<java.util.HashMap> selectPrintCollect(Order order){
        return reportDaoMapper.selectPrintCollect(order);
    }

    public List<Order> selectTodayLastOne(Order order){
        return reportDaoMapper.selectTodayLastOne(order);
    }
}

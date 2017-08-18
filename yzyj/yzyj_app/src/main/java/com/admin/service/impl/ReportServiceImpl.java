package com.admin.service.impl;

import com.admin.dao.ReportDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.CommModel;
import com.admin.model.DeviceList;
import com.admin.model.DictTable;
import com.admin.service.ReportService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class ReportServiceImpl extends GenericServiceImpl<CommModel, String> implements ReportService {
    @Resource
    private ReportDaoMapper reportDaoMapper;
    @Override
    public GenericDao<CommModel, String> getDao() {
        return reportDaoMapper;
    }

    public List<java.util.HashMap> selectDeviceBill(DataTablesPage<CommModel> page, BaseExample baseExample){
        return reportDaoMapper.selectDeviceBill(page, baseExample);
    }

}

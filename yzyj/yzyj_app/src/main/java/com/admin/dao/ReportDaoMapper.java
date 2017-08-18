package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.CommModel;
import com.admin.model.DeviceList;
import com.admin.model.DictTable;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface ReportDaoMapper extends GenericDao<CommModel,String> {
    List<java.util.HashMap> selectDeviceBill(DataTablesPage<CommModel> page, BaseExample baseExample);
}

package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.CommModel;
import com.admin.model.DeviceList;
import com.admin.model.DictTable;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface ReportService extends GenericService<CommModel,String> {
    List<java.util.HashMap> selectDeviceBill(DataTablesPage<CommModel> page, BaseExample baseExample);
}

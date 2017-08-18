package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.DeviceList;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface DeviceListService extends GenericService<DeviceList,String> {

    List<DeviceList> selectByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample);


    List<DeviceList> selectByExample(BaseExample baseExample);


    void updateState(String machineCode);


}

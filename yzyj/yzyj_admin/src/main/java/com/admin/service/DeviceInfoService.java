package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface DeviceInfoService extends GenericService<DeviceInfo,String> {

    List<DeviceInfo> selectByExampleAndPage(DataTablesPage<DeviceInfo> page, BaseExample baseExample);

    List<DeviceInfo> selectByExample(BaseExample baseExample);

    void deleteInfoDevice(String deviceUnique);

    Integer updateState(int state,String deviceUnique);
}

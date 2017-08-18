package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.DevicePayCardQuota;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface DevicePayCardQuotaService extends GenericService<DevicePayCardQuota,String> {

    List<DevicePayCardQuota> selectByExampleAndPage(DataTablesPage<DevicePayCardQuota> page, BaseExample baseExample);

    List<DevicePayCardQuota> selectByExample(BaseExample baseExample);

    void initPayCardQuotaData();

    java.util.HashMap selectDevicePaySumFee(java.util.HashMap map);

    java.util.HashMap selectDeviceExcessPaySumFee(java.util.HashMap map);
}

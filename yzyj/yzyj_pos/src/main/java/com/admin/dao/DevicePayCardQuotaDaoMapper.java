package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.DevicePayCardQuota;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface DevicePayCardQuotaDaoMapper extends GenericDao<DevicePayCardQuota,String> {

    List<DevicePayCardQuota> selectByExample(BaseExample baseExample);

    List<DevicePayCardQuota> selectByExampleAll(BaseExample baseExample);

    List<DevicePayCardQuota> selectByExampleAndPage(DataTablesPage<DevicePayCardQuota> page, BaseExample baseExample);

    java.util.HashMap selectDevicePaySumFee(java.util.HashMap map);

    java.util.HashMap selectDeviceExcessPaySumFee(java.util.HashMap map);
}

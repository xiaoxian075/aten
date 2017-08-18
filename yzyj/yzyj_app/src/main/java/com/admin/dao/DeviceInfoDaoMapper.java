package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.admin.model.DictTable;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface DeviceInfoDaoMapper extends GenericDao<DeviceInfo,String> {
    List<DeviceInfo> selectByExample(BaseExample baseExample);
    List<DeviceInfo> selectMachineInfo(BaseExample baseExample);
    List<DeviceInfo> selectByExampleAndPage(DataTablesPage<DeviceInfo> page, BaseExample baseExample);
    DeviceInfo selectToken(String token);
}

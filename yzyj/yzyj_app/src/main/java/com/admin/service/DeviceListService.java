package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Business;
import com.admin.model.DeviceList;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface DeviceListService extends GenericService<DeviceList,String> {

    List<DeviceList> selectByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample);

    List<DeviceList> selectByExample(BaseExample baseExample);

    List<DeviceList> getDeviceListByAgent(HashMap<Object,Object> mapPara);

    List<DeviceList> getMoneyByAgentUnique(String agentUnique);

    List<DeviceList> getMoneyByAgentUniqueAndDay(String agentUnique);

    List<DeviceList> getRepeatMerchant(HashMap<Object,Object> mapPara);

    List<Business> getBusinessInfo(String agentUnique);

}

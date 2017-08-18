package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Business;
import com.admin.model.DeviceList;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface DeviceListDaoMapper extends GenericDao<DeviceList,String> {

    List<DeviceList> selectByExample(BaseExample baseExample);

    List<DeviceList> selectByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample);

    List<DeviceList> getDeviceListByAgent(HashMap<Object,Object> mapPara);

    List<DeviceList> getMoneyByAgentUnique(@Param("agentUnique") String agentUnique);

    List<DeviceList> getMoneyByAgentUniqueAndDay(@Param("agentUnique") String agentUnique);

    List<DeviceList> getRepeatMerchant(HashMap<Object,Object> mapPara);

    List<Business> getBusinessInfo(@Param("agentUnique") String agentUnique);

}

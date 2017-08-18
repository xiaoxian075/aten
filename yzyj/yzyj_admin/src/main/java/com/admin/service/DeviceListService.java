package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.DeviceList;
import com.admin.model.User;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface DeviceListService extends GenericService<DeviceList,String> {

    List<DeviceList> selectByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample);

    List<DeviceList> selectAllByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample);

    List<DeviceList> getMoneyByAgentUnique(String  agentUnique,String type);

    List<DeviceList> selectByExample(BaseExample baseExample);

    List<DeviceList> getMoneyByAgentUniqueAndDay(String  agentUnique,String type);

    Integer deleteAgentDevice(DeviceList deviceList );

    Integer insertAgentDevice(User user, DeviceList deviceList );

    Long getCountMoneyByAgentUnique(String  agentUnique);

    void  updateApproval(int id,int approvalStatus,int state);

    void updateByMId(String agentUnique,String id);

    List<DeviceList> getDeviceListByYId(String loginName);

    void updateYunId(String merchantYunPayAccountUpdate,String merchantYunPayAccount);

    void updateOneKey(int state);

    DeviceList getDeviceListByDId(String id);

    void updateGroup(String devicUnique,String group);

    void updateSubsidyStatus(String id,String subsidyStatus);

    Integer updateStatusAndMoney(DeviceList deviceList,int money);

    List<DeviceList> searchDeviceList(DeviceList deviceList);

    int updateTyjStatusAndMoney(DeviceList deviceList,int money);

    int updateTyjStatus(DeviceList DeviceList,String status);

}

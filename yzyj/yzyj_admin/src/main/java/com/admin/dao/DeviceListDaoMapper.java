package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.DeviceList;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface DeviceListDaoMapper extends GenericDao<DeviceList,String> {

    List<DeviceList> selectByExample(BaseExample baseExample);

    List<DeviceList> selectByExampleAndPage(DataTablesPage<DeviceList> page, BaseExample baseExample);

    Integer selectMaxId();

    Integer deleteAgentDevice(DeviceList deviceList);

    List<DeviceList> getDeviceListByAgentUnique(@Param("agentUnique") String agentUnique);

    List<DeviceList> getMoneyByAgentUnique(@Param("agentUnique") String agentUnique,
                                           @Param("type") String type);

    List<DeviceList> getMoneyByAgentUniqueAndDay(@Param("agentUnique") String agentUnique,
                                                @Param("type") String type);

    Long getCountMoneyByAgentUnique(@Param("agentUnique") String  agentUnique);

    void  updateApproval(@Param("id")int id,
                         @Param("approvalStatus") int approvalStatus,
                         @Param("state") int state);

    void updateByMId(@Param("agentUnique")String agentUnique,
                     @Param("id")String id);

    void updateOneKey(@Param("state")int state);

    List<DeviceList> getDeviceListByYId(@Param("loginName") String loginName);

    void updateYunId(@Param("merchantYunPayAccountUpdate") String merchantYunPayAccountUpdate,
                     @Param("merchantYunPayAccount") String merchantYunPayAccount);


    DeviceList getDeviceListByDId(@Param("id") String id);

    void updateGroup(@Param("devicUnique")String devicUnique,
                     @Param("group")String group);

    Integer updateSubsidyStatus(@Param("id")String id,
                                @Param("subsidyStatus")String subsidyStatus,
                                @Param("time")String time);

    List<DeviceList> searchDeviceList(DeviceList deviceList);

    void updateTyjStatus(DeviceList deviceList);

    int updateTyjStatus1(DeviceList deviceList);

}

package com.admin.service;

import com.admin.model.AgencyPerson;
import com.admin.model.BaseExample;
import com.admin.model.DeviceList;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface AgencyPersonService extends GenericService<AgencyPerson,String> {

    List<AgencyPerson> selectByExampleAndPage(DataTablesPage<AgencyPerson> page, BaseExample baseExample);

    List<AgencyPerson> selectByExample(BaseExample baseExample);

    Integer insertAgentInfo(AgencyPerson agencyPerson);

    AgencyPerson selectByAgencyUnique(String  agencyUnique);

    void updateAgencyPerson(AgencyPerson agencyPerson);

    List<DeviceList> getDeviceListByAgentUnique(String agentUnique);

    List<AgencyPerson> getAgencyList();

    List<AgencyPerson> getMoneyByAgentUnique(String  agentUnique);

    Integer getAllRemainingSum();

}

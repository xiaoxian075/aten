package com.admin.dao;

import com.admin.model.AgencyPerson;
import com.admin.model.BaseExample;
import com.admin.model.InComeRecord;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface AgencyPersonDaoMapper extends GenericDao<AgencyPerson,String> {
    List<AgencyPerson> selectByExample(BaseExample baseExample);

    List<AgencyPerson> selectByExampleAndPage(DataTablesPage<AgencyPerson> page, BaseExample baseExample);

    AgencyPerson selectByPrimaryKey(String agencyUnique);

    void updateAgencyPersonMoney(@Param("incomeMoney") double incomeMoney,
                                 @Param("agent_unique") String agent_unique);

    void updateYzIsSendAgentInCome(@Param("agentUnique") String agentUnique,@Param("yzSendMoney") int yzSendMoney);

    void inserBatchInCome(List<InComeRecord> inComeRecord);

    Integer getSendByDay(@Param("day") String day);

    List<AgencyPerson> getCountAgent();

}

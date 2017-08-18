package com.admin.dao;

import com.admin.model.*;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface AgencyPersonDaoMapper extends GenericDao<AgencyPerson,String> {

    List<AgencyPerson> selectByExample(BaseExample baseExample);

    List<AgencyPerson> selectByExampleAndPage(DataTablesPage<AgencyPerson> page, BaseExample baseExample);

    Integer updateAgentTotalFee(AgencyPerson agencyPerson);

    List<InComeRecord> getInComeRecord(HashMap<Object,Object> mapPara);

    AgencyPerson getAgencyPersonByInfo(@Param("mobile")String mobile);

    Integer updatePass(@Param("agentUnique")String agentUnique,@Param("pass")String pass);

    AgencyPerson getAgencyInfo(@Param("agentUnique")String agentUnique);

    Integer updateHeadPic(@Param("fileName")String fileName,@Param("agentUnique")String agentUnique);

}

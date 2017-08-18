package com.admin.dao;

import com.admin.model.AgentTotalChange;
import com.admin.model.BaseExample;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface AgentTotalChangeDaoMapper extends GenericDao<AgentTotalChange,String> {

    List<AgentTotalChange> selectByExample(BaseExample baseExample);

    List<AgentTotalChange> selectByExampleAndPage(DataTablesPage<AgentTotalChange> page, BaseExample baseExample);

    java.util.HashMap selectAgentGrandTotal(@Param("agentUnique") String agentUnique);

    List<AgentTotalChange> getTotalChangList(HashMap map);
}

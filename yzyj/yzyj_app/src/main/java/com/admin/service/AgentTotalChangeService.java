package com.admin.service;

import com.admin.model.AgentTotalChange;
import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface AgentTotalChangeService extends GenericService<AgentTotalChange,String> {

    List<AgentTotalChange> selectByExampleAndPage(DataTablesPage<AgentTotalChange> page, BaseExample baseExample);

    List<AgentTotalChange> selectByExample(BaseExample baseExample);

    java.util.HashMap selectAgentGrandTotal(String agentUnique);

    List<AgentTotalChange> getTotalChangList(HashMap map);
}

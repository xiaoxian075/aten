package com.admin.service.impl;

import com.admin.dao.AgentTotalChangeDaoMapper;
import com.admin.dao.DictTableDaoMapper;
import com.admin.model.AgentTotalChange;
import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.admin.service.AgentTotalChangeService;
import com.admin.service.DictTableService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import com.core.util.CommConstant;
import com.core.util.CommDictList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class AgentTotalChangeServiceImpl extends GenericServiceImpl<AgentTotalChange, String> implements AgentTotalChangeService {
    @Resource
    private AgentTotalChangeDaoMapper agentTotalChangeDaoMapper;
    @Override
    public GenericDao<AgentTotalChange, String> getDao() {
        return agentTotalChangeDaoMapper;
    }


    public List<AgentTotalChange> selectByExampleAndPage(DataTablesPage<AgentTotalChange> page, BaseExample baseExample){
        return agentTotalChangeDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<AgentTotalChange> selectByExample(BaseExample baseExample){
        return agentTotalChangeDaoMapper.selectByExample(baseExample);
    }

    public java.util.HashMap selectAgentGrandTotal(String agentUnique){
        return agentTotalChangeDaoMapper.selectAgentGrandTotal(agentUnique);
    }

    @Override
    public List<AgentTotalChange> getTotalChangList(HashMap map) {
        return agentTotalChangeDaoMapper.getTotalChangList(map);
    }

}

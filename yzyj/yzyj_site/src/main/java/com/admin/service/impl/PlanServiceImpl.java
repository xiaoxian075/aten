package com.admin.service.impl;

import com.admin.dao.PlanDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Plan;
import com.admin.service.PlanService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ${huangdw} on 2017/3/25.
 */
@Service
public class PlanServiceImpl extends GenericServiceImpl<Plan, String> implements PlanService {
    @Resource
    private PlanDaoMapper planDaoMapper;
    @Override
    public GenericDao<Plan, String> getDao() {
        return planDaoMapper;
    }
    public List<Plan> selectByExampleAndPage(DataTablesPage<Plan> page, BaseExample baseExample){
        return planDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<Plan>selectByExample(BaseExample baseExample){
        return planDaoMapper.selectByExample(baseExample);
    }
}

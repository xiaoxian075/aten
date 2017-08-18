package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Plan;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by ${huangdw} on 2017/3/25.
 */
public interface PlanService extends GenericService<Plan,String> {
    List<Plan>selectByExampleAndPage(DataTablesPage<Plan> page,BaseExample baseExample);
    List<Plan>selectByExample(BaseExample baseExample);
}

package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Plan;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by ${huangdw} on 2017/3/25.
 */
public interface PlanDaoMapper extends GenericDao<Plan,String> {
    List<Plan> selectByExampleAndPage(DataTablesPage<Plan> page, BaseExample baseExample);
    List<Plan>selectByExample(BaseExample baseExample);
}

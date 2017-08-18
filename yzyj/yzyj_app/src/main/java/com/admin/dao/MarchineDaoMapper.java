package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Marchine;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface MarchineDaoMapper extends GenericDao<Marchine,String> {
    List<Marchine> selectByExample(BaseExample baseExample);
    List<Marchine> selectByExampleAndPage(DataTablesPage<Marchine> page, BaseExample baseExample);
}

package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Marchine;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface MarchineService extends GenericService<Marchine,String> {
    List<Marchine> selectByExampleAndPage(DataTablesPage<Marchine> page, BaseExample baseExample);
    List<Marchine> selectByExample(BaseExample baseExample);
}

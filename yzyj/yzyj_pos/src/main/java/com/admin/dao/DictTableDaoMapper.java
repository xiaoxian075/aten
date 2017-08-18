package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface DictTableDaoMapper extends GenericDao<DictTable,String> {

    List<DictTable> selectByExample(BaseExample baseExample);

    List<DictTable> selectByExampleAndPage(DataTablesPage<DictTable> page, BaseExample baseExample);

}

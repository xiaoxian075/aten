package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.DictTable;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by wjf on 2016/4/15.
 */
public interface InitDataService extends GenericService<DictTable,String> {
    List<DictTable> selectByExampleAndPage(DataTablesPage<DictTable> page, BaseExample baseExample);
    List<DictTable> selectByExample(BaseExample baseExample);
    void InitMemoryData();
}

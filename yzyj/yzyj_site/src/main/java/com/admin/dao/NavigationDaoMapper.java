package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Navigation;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface NavigationDaoMapper extends GenericDao<Navigation,String> {
    List<Navigation> selectByExampleAndPage(DataTablesPage<Navigation> page, BaseExample baseExample);
    List<Navigation> selectByExample(BaseExample baseExample);
}

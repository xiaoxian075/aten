package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.ServiceMark;
import com.admin.vo.DictVo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by ${huangdw} on 2017/3/25.
 */
public interface ServiceMarkDaoMapper extends GenericDao<ServiceMark,String> {
    List<ServiceMark> selectByExampleAndPage(DataTablesPage<ServiceMark> page, BaseExample baseExample);
    List<DictVo>selectService();
}

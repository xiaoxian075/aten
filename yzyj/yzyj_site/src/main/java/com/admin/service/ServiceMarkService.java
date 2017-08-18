package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.ServiceMark;
import com.admin.vo.DictVo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by ${huangdw} on 2017/3/25.
 */
public interface ServiceMarkService extends GenericService<ServiceMark,String> {
    List<ServiceMark> selectByExampleAndPage(DataTablesPage<ServiceMark> page, BaseExample baseExample);
    List<DictVo>selectService();
}

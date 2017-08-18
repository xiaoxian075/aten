package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Propaganda;
import com.admin.vo.DictVo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public interface PropagandaDaoMapper extends GenericDao<Propaganda,String> {
    List<Propaganda> selectByExampleAndPage(DataTablesPage<Propaganda> page, BaseExample baseExample);
    List<Propaganda> selectByExample( BaseExample baseExample);
    List<DictVo>selectVideo();
}

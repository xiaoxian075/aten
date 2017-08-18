package com.admin.dao;

import com.admin.dto.AdvertDto;
import com.admin.model.Advert;
import com.admin.model.BaseExample;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public interface AdvertDaoMapper extends GenericDao<Advert,String> {
    List<Advert> selectByTypeAndPage(DataTablesPage<Advert> page, BaseExample baseExample);
    List<Advert> selectByExample(BaseExample baseExample);
    List<Advert> selectByType(AdvertDto advertDto);
}

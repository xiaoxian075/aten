package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Quota;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
public interface QuotaService extends GenericService<Quota,String> {

    List<Quota> selectByExampleAndPage(DataTablesPage<Quota> page, BaseExample baseExample);

    Quota selectByQuotaId(int id);

    void  insertQuotaInfo(Quota quota);

    void  updateQuotaInfo(Quota quota);

    Integer selectMinGroup();
}

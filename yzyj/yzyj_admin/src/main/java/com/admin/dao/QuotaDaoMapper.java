package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Quota;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
public interface QuotaDaoMapper {

    List<Quota> selectByExampleAndPage(DataTablesPage<Quota> page, BaseExample baseExample);

    Quota selectByQuotaId(@Param("id") int id);

    void  insertQuotaInfo(Quota quota);

    void  updateQuotaInfo(Quota quota);

    Integer selectMinGroup();
}

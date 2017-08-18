package com.admin.dao;

import com.admin.model.*;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface LklBillDaoMapper extends GenericDao<TiXian,String> {

    List<Txn> selectTxnByExampleAndPage(DataTablesPage<Txn> page, BaseExample baseExample);

    List<TiXian> selectTiXianByExampleAndPage(DataTablesPage<TiXian> page, BaseExample baseExample);

    TiXian getTiXianByDay(BaseExample baseExample);

    void insertTiXian(TiXian tiXian);

    Integer deleteTixian(@Param("id") String id);

    Txn getTxnByDay(BaseExample baseExample);

    void insertTxn(Txn txn);

    Integer deleteTxn(@Param("id") String id);

}

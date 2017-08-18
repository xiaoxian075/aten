package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.TiXian;
import com.admin.model.Txn;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2017-03-31.
 */
public  interface LklBillService  extends GenericService<TiXian,String> {

    TiXian getTiXianByDay(BaseExample baseExample);

    Txn getTxnByDay(BaseExample baseExample);

    List<Txn> selectTxnByExampleAndPage(DataTablesPage<Txn> page, BaseExample baseExample);

    List<TiXian> selectTiXianByExampleAndPage(DataTablesPage<TiXian> page, BaseExample baseExample);

    void insertTiXian(TiXian tiXian);

    void updateTixian(TiXian tiXian,String id);

    void insertTxn(Txn txn);

    void updateTxn(Txn txn,String id);
}

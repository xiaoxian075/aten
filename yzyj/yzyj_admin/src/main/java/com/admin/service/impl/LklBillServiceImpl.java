package com.admin.service.impl;

import com.admin.dao.LklBillDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.TiXian;
import com.admin.model.Txn;
import com.admin.service.LklBillService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017-03-31.
 */
@Service
public class LklBillServiceImpl extends GenericServiceImpl<TiXian, String> implements LklBillService {

    @Resource
    private LklBillDaoMapper lklBillDaoMapper;

    @Override
    public TiXian getTiXianByDay(BaseExample baseExample) {
        return lklBillDaoMapper.getTiXianByDay(baseExample);
    }

    @Override
    public Txn getTxnByDay(BaseExample baseExample) {
        return lklBillDaoMapper.getTxnByDay(baseExample);
    }

    @Override
    public List<Txn> selectTxnByExampleAndPage(DataTablesPage<Txn> page, BaseExample baseExample) {
        return lklBillDaoMapper.selectTxnByExampleAndPage(page,baseExample);
    }

    @Override
    public List<TiXian> selectTiXianByExampleAndPage(DataTablesPage<TiXian> page, BaseExample baseExample) {
        return lklBillDaoMapper.selectTiXianByExampleAndPage(page,baseExample);
    }

    @Override
    public void insertTiXian(TiXian tiXian) {
        lklBillDaoMapper.insertTiXian(tiXian);
    }

    @Override
    public void updateTixian(TiXian tiXian,String id) {
        lklBillDaoMapper.deleteTixian(id);
        lklBillDaoMapper.insertTiXian(tiXian);
    }

    @Override
    public void insertTxn(Txn txn) {
        lklBillDaoMapper.insertTxn(txn);
    }

    @Override
    public void updateTxn(Txn txn,String id) {
        lklBillDaoMapper.deleteTxn(id);
        lklBillDaoMapper.insertTxn(txn);
    }

    @Override
    public GenericDao<TiXian, String> getDao() {
        return null;
    }
}

package com.admin.service.impl;

import com.admin.dao.CheckRecordDaoMapper;
import com.admin.model.*;
import com.admin.service.CheckRecordService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
@Service
public class CheckRecordServiceImpl extends GenericServiceImpl<CheckRecord, String> implements CheckRecordService {

    @Resource
    private CheckRecordDaoMapper checkRecordDaoMapper;

    @Override
    public List<CheckRecord> selectByExample(BaseExample baseExample) {
        return null;
    }

    @Override
    public CheckRecord getCheckRecordByDay(BaseExample baseExample) {
        return checkRecordDaoMapper.getCheckRecordByDay(baseExample);
    }


    @Override
    public List<InComeRecord> getInComeRecordDay(HashMap hashMap) {
        return checkRecordDaoMapper.getInComeRecordDay(hashMap);
    }

    @Override
    public List<InComeRecord> getInComeRecordMonth(HashMap hashMap) {
        return checkRecordDaoMapper.getInComeRecordMonth(hashMap);
    }

    @Override
    public List<InComeRecord> searchInComeList(InComeRecord inComeRecord) {
        return checkRecordDaoMapper.searchInComeList(inComeRecord);
    }

    @Override
    public GenericDao<CheckRecord, String> getDao() {
        return null;
    }

    public List<CheckRecord> selectByExampleAndPage(DataTablesPage<CheckRecord> page, BaseExample baseExample){
        return checkRecordDaoMapper.selectByExampleAndPage(page,baseExample);
    }

    public List<InComeRecord> selectByExampleAndPage1(DataTablesPage<InComeRecord> page, BaseExample baseExample){
        return checkRecordDaoMapper.selectByExampleAndPage1(page,baseExample);
    }
}

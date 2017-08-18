package com.admin.service.impl;

import com.admin.dao.WithdrawRecordDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.WithdrawRecord;
import com.admin.service.WithdrawRecordService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
@Service
public class WithdrawRecordServiceImpl extends GenericServiceImpl<WithdrawRecord, String> implements WithdrawRecordService {

    @Resource
    private WithdrawRecordDaoMapper withdrawRecordDaoMapper;

    @Override
    public GenericDao<WithdrawRecord, String> getDao() {
        return null;
    }

    @Override
    public List<WithdrawRecord> selectByExampleAndPage(DataTablesPage<WithdrawRecord> page, BaseExample baseExample) {
        return withdrawRecordDaoMapper.selectByExampleAndPage(page,baseExample);
    }

    @Override
    public List<WithdrawRecord> selectByExampleAndPage1(DataTablesPage<WithdrawRecord> page, BaseExample baseExample) {
        return withdrawRecordDaoMapper.selectByExampleAndPage1(page,baseExample);
    }

    @Override
    public Integer getCountWithdraw() {
        return withdrawRecordDaoMapper.getCountWithdraw();
    }

    @Override
    public Integer getCountWithdrawByAgent(String agentUnique) {
        return withdrawRecordDaoMapper.getCountWithdrawByAgent(agentUnique);
    }
}

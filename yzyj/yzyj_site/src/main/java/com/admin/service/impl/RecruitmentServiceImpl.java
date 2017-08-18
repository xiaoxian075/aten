package com.admin.service.impl;

import com.admin.dao.RecruitmentDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.Recruitment;
import com.admin.service.RecruitmentService;
import com.admin.vo.DictVo;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
@Service
public class RecruitmentServiceImpl extends GenericServiceImpl<Recruitment, String> implements RecruitmentService {
    @Resource
    private RecruitmentDaoMapper recruitmentDaoMapper;
    @Override
    public GenericDao<Recruitment, String> getDao() {
        return recruitmentDaoMapper;
    }
    public List<Recruitment> selectByExampleAndPage(DataTablesPage<Recruitment> page, BaseExample baseExample){
        return recruitmentDaoMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<DictVo>selectRecruitment(){
        return recruitmentDaoMapper.selectRecruitment();
    }
}

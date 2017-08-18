package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.Recruitment;
import com.admin.vo.DictVo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public interface RecruitmentDaoMapper extends GenericDao<Recruitment,String> {
    List<Recruitment> selectByExampleAndPage(DataTablesPage<Recruitment> page, BaseExample baseExample);
    List<DictVo>selectRecruitment();
}

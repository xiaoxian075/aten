package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.Recruitment;
import com.admin.vo.DictVo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public interface RecruitmentService extends GenericService<Recruitment,String> {
    List<Recruitment> selectByExampleAndPage(DataTablesPage<Recruitment> page,BaseExample baseExample);
    List<DictVo>selectRecruitment();
}

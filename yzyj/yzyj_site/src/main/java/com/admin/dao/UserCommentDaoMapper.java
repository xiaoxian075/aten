package com.admin.dao;

import com.admin.model.BaseExample;
import com.admin.model.UserComment;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface UserCommentDaoMapper extends GenericDao<UserComment,String> {
    List<UserComment> selectByExample(BaseExample baseExample);
    List<UserComment> selectByExampleAndPage(DataTablesPage<UserComment> page, BaseExample baseExample);
}

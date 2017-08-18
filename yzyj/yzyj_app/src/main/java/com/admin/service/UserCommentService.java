package com.admin.service;

import com.admin.model.BaseExample;
import com.admin.model.UserComment;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public interface UserCommentService extends GenericService<UserComment,String> {
    List<UserComment> selectByExampleAndPage(DataTablesPage<UserComment> page, BaseExample baseExample);
    List<UserComment> selectByExample(BaseExample baseExample);
}

package com.admin.service.impl;

import com.admin.dao.UserCommentDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.UserComment;
import com.admin.service.UserCommentService;
import com.core.generic.GenericDao;
import com.core.generic.GenericServiceImpl;
import com.core.mybatis.DataTablesPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class UserCommentServiceImpl extends GenericServiceImpl<UserComment, String> implements UserCommentService {
    @Resource
    private UserCommentDaoMapper userCommentDAOMapper;
    @Override
    public GenericDao<UserComment, String> getDao() {
        return userCommentDAOMapper;
    }


    public List<UserComment> selectByExampleAndPage(DataTablesPage<UserComment> page, BaseExample baseExample){
        return userCommentDAOMapper.selectByExampleAndPage(page,baseExample);
    }
    public List<UserComment> selectByExample(BaseExample baseExample){
        return userCommentDAOMapper.selectByExample(baseExample);
    }

}

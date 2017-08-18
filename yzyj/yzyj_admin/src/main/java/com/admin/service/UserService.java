package com.admin.service;

import com.admin.model.AgencyPerson;
import com.admin.model.User;
import com.admin.model.UserExample;
import com.admin.vo.UserVo;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * 功能描述：
 * 作者： linym
 * 时间：2016/2/25
 */
public interface UserService  extends GenericService<User,String> {
    /**
     * 根据用户名查询用户
     * @return
     */
    User selectByUserName(String username);
    /**
     * 查找用户的功能菜单
     * @param id
     * @return
     */
    java.util.HashMap selectUserMenu(String id);

    List<UserVo> selectByUserAndPage(DataTablesPage<UserVo> page, UserExample usersExample);

    Integer deleteAgentUser(String agentUnique);

    AgencyPerson getAgencyPerson(String agentUnique);

    void updateUserName(AgencyPerson agencyPerson);
}

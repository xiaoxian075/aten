package com.admin.dao;

import com.admin.model.*;
import com.admin.vo.UserVo;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：
 * 作者： linym
 * 时间：2016/2/25
 */
public interface UserDaoMapper extends GenericDao<User,String> {
    User selectUser(User user);
    /**
     * 根据用户名查询用户
     * @return
     */
    public User selectByUserName(String username);
    /**
     * 查找用户的功能菜单
     * @param id
     * @return
     */
    List<Menu> selectUserMenu(String id);

    List<UserVo> selectByUserAndPage(DataTablesPage<UserVo> page, UserExample usersExample);

    Integer deleteAgentUser(String agentUnique);

    AgencyPerson getAgencyPerson(@Param("agentUnique") String agentUnique);

    void updateUserName(AgencyPerson agencyPerson);
}

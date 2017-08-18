package com.admin.dao;

import com.admin.model.*;
import com.core.generic.GenericDao;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface RoleDaoMapper extends GenericDao<Role,String> {
    /**
     * 根据角色查询权限
     * @return
     */
     List<Menu> selectMenuByroleId(String roleId);
    List<Role> selectByExample(RoleExample roleExample);
    List<MenuHandleModule> selectMenuHandleModule(BaseExample baseExample);
    List<Role> selectByExampleAndPage(DataTablesPage<Role> page, RoleExample roleExample);
    void deleteFunctionByRoleId(String roleId);
    void insertFunction(RoleFunction roleFunction);

}

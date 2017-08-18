package com.admin.service;

import com.admin.model.Menu;
import com.admin.model.Role;
import com.admin.model.RoleExample;
import com.admin.model.RoleFunction;
import com.core.generic.GenericService;
import com.core.mybatis.DataTablesPage;

import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface RoleService  extends GenericService<Role,String> {
    /**
     * 根据角色查询权限
     * @return
     */
    List<Menu> selectMenuByroleId(String roleId);
    List<Role> selectByExample(RoleExample roleExample);
    List<Role> selectByExampleAndPage(DataTablesPage<Role> page, RoleExample roleExample);
    void updateRoleFunction(String roleId, String... menuIds);
    void deleteFunctionByRoleId(String roleId);
    void insertFunction(RoleFunction roleFunction);
}

package com.admin.dao;

import com.admin.model.Menu;
import com.admin.model.RoleFunction;
import com.core.generic.GenericDao;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public interface MenuDaoMapper extends GenericDao<Menu,String> {
    List<Menu> selectByExample();
    List<RoleFunction> selectRoleFunction(String roleId);
}

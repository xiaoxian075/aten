package com.admin.service;

import com.admin.model.Menu;
import com.admin.model.RoleFunction;
import com.admin.vo.NodeVo;
import com.core.generic.GenericService;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30.
 */
public interface MenuService extends GenericService<Menu,String> {
    /**
     * 获取菜单树
     * @return
     */
    List<NodeVo> selectMenuNode(String roleid);

    List<RoleFunction> selectRoleFunction(String roleId);
}

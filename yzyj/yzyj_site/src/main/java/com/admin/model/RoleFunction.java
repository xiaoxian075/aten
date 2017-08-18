package com.admin.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/24.
 */
public class RoleFunction implements Serializable {
    private String roleId;
    private String menuId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}

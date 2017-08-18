package com.admin.model;

import com.admin.vo.MenuVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 功能描述：
 * 作者： linym
 * 时间：2016/2/25
 */
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private Date createtime;
    private Date updatetime;
    private Date lastTime;
    private String createaccount;
    private String updateaccount;
    private String rid;
    private String nickname;
    private String email;
    private String status;
    private String agentUnique;
    private String headUrl;
    private Role role;
    private List<MenuVo> menuList;

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public List<MenuVo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuVo> menuList) {
        this.menuList = menuList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreateaccount() {
        return createaccount;
    }

    public void setCreateaccount(String createaccount) {
        this.createaccount = createaccount;
    }

    public String getUpdateaccount() {
        return updateaccount;
    }

    public void setUpdateaccount(String updateaccount) {
        this.updateaccount = updateaccount;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }
}

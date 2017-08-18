package com.admin.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24.
 */
public class MenuVo implements Serializable {
    private String id;
    private String pid;
    private String url;
    private String name;
    private String handleModule;
    private List<MenuVo> children;

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandleModule() {
        return handleModule;
    }

    public void setHandleModule(String handleModule) {
        this.handleModule = handleModule;
    }
}

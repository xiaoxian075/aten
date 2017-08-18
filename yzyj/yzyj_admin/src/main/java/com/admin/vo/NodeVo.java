package com.admin.vo;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/9.
 */
public class NodeVo implements Serializable {

    private String id;

    private String pId;

    private String name;

    private boolean open;

    private boolean checked;

    public NodeVo(){

    }

    public NodeVo(String id, String pId, String name, boolean open, boolean checked) {
        this.id = id;
        this.pId = (null==pId || "".equals(pId))?"0":pId;
        this.name = name;
        this.open = open;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

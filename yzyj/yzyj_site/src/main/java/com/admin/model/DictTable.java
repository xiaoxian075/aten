package com.admin.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/2.
 */
public class DictTable implements Serializable {
    private String sysId;
    private String dictTableBh;
    private String dictTableMc;
    private String dictBh;
    private String dictMc;

    public String getDictTableBh() {
        return dictTableBh;
    }

    public void setDictTableBh(String dictTableBh) {
        this.dictTableBh = dictTableBh;
    }

    public String getDictTableMc() {
        return dictTableMc;
    }

    public void setDictTableMc(String dictTableMc) {
        this.dictTableMc = dictTableMc;
    }

    public String getDictBh() {
        return dictBh;
    }

    public void setDictBh(String dictBh) {
        this.dictBh = dictBh;
    }

    public String getDictMc() {
        return dictMc;
    }

    public void setDictMc(String dictMc) {
        this.dictMc = dictMc;
    }


    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}

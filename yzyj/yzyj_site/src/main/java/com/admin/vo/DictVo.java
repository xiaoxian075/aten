package com.admin.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/14.
 */
public class DictVo {
    private String dictBh;//字典ID
    private String dictMc;//字典名称
    private List list;//字典内容

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

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}

package com.core.mybatis;

import java.util.List;

/**
 * 功能描述：根据jquery.datatables封装的分页插件，继承PAGE，支持普通分页
 * 作者： luocj
 * 时间：2015/11/13.
 */
public class DataTablesPage<T> extends Page {

    private Integer draw;

    private Integer recordsTotal;

    private Integer recordsFiltered;

    private List<T> data;

    private Integer length;

    private Integer start;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return super.getTotalCount();
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return super.getTotalCount();
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return super.getResult();
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
        super.setPageSize(length);
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
        this.setPageNo((start/length)+1);
    }
}

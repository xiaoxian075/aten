package com.aten.model.orm;

import java.io.Serializable;
import java.util.List;

public class AttrVo implements Serializable {
	private static final long serialVersionUID = 7984695358071534886L;
	private String text;		//属性名称
    private String attr_id;		//属性ID
    private String type;		//0：文本框 1：select框 2：多选框 3：单选框  4:图片类型 5:多填文本框',
    private String unit;
    private String is_alisa;		//暂不用
    private String is_custom_value;
    private String is_index;
    private String is_must;
    private String show_type;		//'0:文字 1:图片'
	private String manual_fee;		//手工费
	private List<AttrValue> data;
	

    public String getManual_fee() {
		return manual_fee;
	}

	public void setManual_fee(String manual_fee) {
		this.manual_fee = manual_fee;
	}

	

    public String getIs_alisa() {
        return is_alisa;
    }

    public void setIs_alisa(String is_alisa) {
        this.is_alisa = is_alisa;
    }

    public String getIs_custom_value() {
        return is_custom_value;
    }

    public void setIs_custom_value(String is_custom_value) {
        this.is_custom_value = is_custom_value;
    }

    public String getIs_index() {
        return is_index;
    }

    public void setIs_index(String is_index) {
        this.is_index = is_index;
    }

    public String getIs_must() {
        return is_must;
    }

    public void setIs_must(String is_must) {
        this.is_must = is_must;
    }

    public String getShow_type() {
        return show_type;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<AttrValue> getData() {
        return data;
    }

    public void setData(List<AttrValue> data) {
        this.data = data;
    }
}

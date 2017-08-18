package com.core.generic;

/**
 * 功能描述：所有自定义枚举类型实现该接口
 * 作者： huangdw
 * 时间：2015/11/13.
 */
public interface GenericEnum {

    /**
     * value: 为保存在数据库中的值
     */
    public String getValue();

    /**
     * text : 为前端显示值
     */
    public String getText();

}

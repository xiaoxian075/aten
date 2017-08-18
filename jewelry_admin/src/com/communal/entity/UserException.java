package com.communal.entity;

/**
 * 功能描述：用户自定义异常
 * 作者： luocj
 * 时间：2015/11/13.
 */
public class UserException extends RuntimeException {

    /**
     * 异常发生时间
     */
    private long date = System.currentTimeMillis();

    public long getDate() {
        return date;
    }
}

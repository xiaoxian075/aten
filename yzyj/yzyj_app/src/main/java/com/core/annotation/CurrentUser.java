package com.core.annotation;


import java.lang.annotation.*;

/**
 * 功能描述：绑定当前登录的用户
 * 作者： luocj
 * 时间：2015/11/20.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     *
     *
     * @return 当前用户对象
     */
    String value() default "user";
}

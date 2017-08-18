package com.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述：日志自定义注解
 * 作者： luocj
 * 时间：2015/11/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface MethodLog {
    /**
     * 名称
     * @return
     */
    String name() default "";

    /**
     * 日志类别
     * @return
     */
    String category() default "0";

    /**
     * 操作类型
     * @return
     */
    String type();

    /**
     * 操作描述
     * @return
     */
    String description() default "";
}

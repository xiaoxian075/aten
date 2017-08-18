package com.aten.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author linjunqin
 * @Description 定义Token注解类
 * @param
 * @date 2016-12-29 上午10:59:42
 */
@Target(ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface TokenAnnotation {
	 
	 boolean needSaveToken() default false ;
	 
     boolean needRemoveToken() default false ;
}

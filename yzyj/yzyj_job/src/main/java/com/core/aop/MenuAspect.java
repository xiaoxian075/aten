package com.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 功能描述：功能点缓存切面
 * 作者： luocj
 * 时间：2015/11/16.
 */
//@Component
//@Aspect
public class MenuAspect extends BaseCacheAspect{
//
//    /**
//     * 菜单缓存前缀
//     */
//    private String menusKeyPrefix = "menus-";
//
//    /**
//     * 切入点
//     */
//    @Pointcut(value = "target(com.eliteams.quick4j.web.service.FunctionService)")
//    private void menuServicePointcut() {
//    }
//
//    /**
//     * 切入点
//     * @param user
//     */
//    @Pointcut(value = "execution(* selectMenus(*)) && args(user)", argNames = "user")
//    private void menuCacheablePointcut(User user) {
//    }
//
//    @Around(value = "menuServicePointcut() && menuCacheablePointcut(user)", argNames = "pjp,user")
//    public Object cacheableAdvice(ProceedingJoinPoint pjp,User user) throws Throwable {
//
////        String methodName = pjp.getSignature().getName();
//        String key = menusKey(user.getYhdm());
//
//        Object object = get(key);
//        //缓存命中
//        if (object != null) {
//            return object;
//        }
//        //无缓存执行查询
//        object = pjp.proceed();
//
//        //缓存结果
//        put(key,object);
//        return object;
//    }
//
//    private String menusKey(String yhdm) {
//        return this.menusKeyPrefix + yhdm;
//    }

}

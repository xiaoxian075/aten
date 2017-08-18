package com.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by luocj on 2015/11/11.
 */
@Component
@Aspect
public class DicAspect extends BaseCacheAspect {


    /**
     * 切入点
     */
    @Pointcut(value ="(execution(* selectCodeByexample(*)))")
    private void cachePoint() {
    }

    /**
     * 切入/返回
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "cachePoint()")
    public Object cacheableAdvice(ProceedingJoinPoint pjp) throws Throwable {

//        String methodName = pjp.getSignature().getName();
        String key = pjp.getTarget().getClass().getSimpleName();

        Object object = get(key);
        //cache hit
        if (object != null) {
            return object;
        }
        //cache miss
        object = pjp.proceed();

        //put cache
        put(key,object);
        return object;
    }
}

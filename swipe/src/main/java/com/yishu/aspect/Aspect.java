package com.yishu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务组件Aspect切面Bean
 * Created by admin on 2017/7/4.
 */
//声明这是一个组件
@Component
//声明这是一个切面Bean
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    private final static Logger log = LoggerFactory.getLogger(Aspect.class);

    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* com.yishu.controller.*.*(..))")
    public void aspect(){	}

//    /*
//     * 配置前置通知,使用在方法aspect()上注册的切入点
//     * 同时接受JoinPoint切入点对象,可以没有该参数
//     */
//    @Before("aspect()")
//    public void before(JoinPoint joinPoint){
//        if(log.isInfoEnabled()){
//            log.info("before " + joinPoint);
//        }
//    }
//
//    //配置后置通知,使用在方法aspect()上注册的切入点
//    @After("aspect()")
//    public void after(JoinPoint joinPoint){
//        if(log.isInfoEnabled()){
//            log.info("after " + joinPoint);
//        }
//    }
//
//    //配置后置返回通知,使用在方法aspect()上注册的切入点
//    @AfterReturning("aspect()")
//    public void afterReturn(JoinPoint joinPoint){
//        if(log.isInfoEnabled()){
//            log.info("afterReturn " + joinPoint);
//        }
//    }
//
//    //配置抛出异常后通知,使用在方法aspect()上注册的切入点
//    @AfterThrowing(pointcut="aspect()", throwing="ex")
//    public void afterThrow(JoinPoint joinPoint, Exception ex){
//        if(log.isInfoEnabled()){
//            log.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
//        }
//    }

    //配置环绕通知,使用在方法aspect()上注册的切入点
    @Around("aspect()")
    public Object around(JoinPoint joinPoint){
        long start = System.currentTimeMillis();
        Object result=null;
        try {
            result=((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            if(log.isInfoEnabled()){
                log.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            }
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if(log.isInfoEnabled()){
                log.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            }
        }
        return result;
    }
    //对controller层扫描，只需要在advice上添加如:@Around("within(com.yishu.controller.*+) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
}
package com.kim.springframework.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author huangjie
 * @description   xml形式切面增强类
 * @date 2022-01-15
 */
public class XmlAdvice {

    //前置增强
    public void before(JoinPoint joinPoint){
        System.out.println("业务逻辑执行之前执行:方法参数:"+joinPoint.getArgs());
    }

    //后置增强,无论是否抛出异常都执行
    public void afterAlways(JoinPoint joinPoint){
        System.out.println("业务逻辑执行之后执行，无论是否抛出了异常:");
    }

    //后置增强，抛出异常时则不执行，不抛出异常时才会执行
    public void afterReturning(JoinPoint joinPoint,Object result){
        System.out.println("业务逻辑执行之后执行，如果业务逻辑抛出了异常就不执行:"+result);
    }

    //异常增强
    public void throwing(JoinPoint joinPoint,Exception ex){
        System.out.println("业务逻辑抛出了异常就执行:"+ex.getMessage());
    }

    //环绕增强
    public void around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("环绕增强：业务逻辑执行之前执行");
        point.proceed();
        System.out.println("环绕增强：业务逻辑执行之后执行");
    }



}

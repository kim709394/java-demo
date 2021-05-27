package com.kim.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author kim
 * @Since 2021/5/27
 */
@Aspect //声明这是一个切面增强类
@Component   //作为一个bean注入容器
public class UserAdvice {

    /**
     * 切点说明
     * 公有的返回值不限指定包名指定类方法名不限参数不限的所有方法
     * */
    @Pointcut("execution(public * com.kim.spring.service.impl.UserServiceImpl.*(..))")
    public void userPoint(){}



}

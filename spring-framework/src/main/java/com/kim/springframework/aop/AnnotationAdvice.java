package com.kim.springframework.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author huangjie
 * @description 业务方法正常执行的五种增强切面
 * @date 2022-01-16
 */
@Aspect //声明这是一个切面增强类
@Component   //作为一个bean注入容器
public class AnnotationAdvice {


    @Pointcut("execution(public void com.kim.springframework.aop.BusinessHandler.handle(java.lang.String))")
    public void handlePointcut(){}




    /**
     * 前置增强
     * */
    @Before(value="handlePointcut()")
    public void before(JoinPoint joinPoint){
        System.out.println("前置增强");
        System.out.println(joinPoint);
    }

    /**
     * 后置增强
     * 不管方法是否抛异常都执行
     */
    @After(value="handlePointcut()")
    public void after(JoinPoint joinPoint){
        System.out.println("后置增强不管方法是否抛异常都执行");
        System.out.println(joinPoint);
    }

    /**
     *后置增强
     * 方法出异常将不执行,
     * rtn是定义传入的返回值参数
     */
    @AfterReturning(value="handlePointcut()",returning = "rtn")
    public void afterReturning(JoinPoint joinPoint, Object rtn){

        System.out.println("后置增强出异常将不执行："+rtn);
        System.out.println(joinPoint);

    }

    /**
     * 环绕增强,方法执行前后执行，还可以控制方法是否执行
     * ProceedingJoinPoint只能用于环绕增强
     */
    @Around("handlePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("环绕增强,方法执行前后执行，还可以控制方法是否执行");
        //获取方法名
        System.out.println("方法名:"+joinPoint.getSignature().getName());
        //获取参数列表
        Object[] args = joinPoint.getArgs();
        //转换参数为class类型
        Class<?>[] argTypes=new Class<?>[args.length];
        for (int i=0;i<args.length;i++) {
            System.out.println(args[i]);
            argTypes[i]=args[i].getClass();
        }
        //获取目标类
        Object target = joinPoint.getTarget();
        System.out.println(target);
        String kind = joinPoint.getKind();
        System.out.println(kind);
        Class declaringType = joinPoint.getSignature().getDeclaringType();
        System.out.println("目标类:"+declaringType);
        //获取目标方法
        Method method = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(), argTypes);
        System.out.println(method.getName());
        //执行目标方法，可控制目标方法是否执行
        Object rtn = joinPoint.proceed();
        System.out.println("按原目标方法执行,返回值:"+rtn);
        Object rtn2 = joinPoint.proceed(new String[]{"newParam"});
        System.out.println("修改原目标方法传入参数执行，返回值:"+rtn2);
        //目标方法执行后执行代码
        System.out.println(joinPoint);
        //修改原目标方法的返回值

        return "newResult";
    }

    /**
     * 异常增强
     * ex是定义传入的异常对象
     */
    @AfterThrowing(value="handlePointcut()",throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint,Exception ex){
        System.out.println("异常增强");
        System.out.println(joinPoint);
    }

}

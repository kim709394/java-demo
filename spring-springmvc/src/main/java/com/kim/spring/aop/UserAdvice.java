package com.kim.spring.aop;

import com.kim.spring.entity.UserDO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

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
    @Pointcut("execution(public * com.kim.spring.service.impl.UserServiceImpl.before(..))")
    public void before(){}

    @Pointcut("execution(public * com.kim.spring.service.impl.UserServiceImpl.after(..))")
    public void after(){}

    @Pointcut("execution(public * com.kim.spring.service.impl.UserServiceImpl.afterReturning(..))")
    public void afterReturning(){}

    @Pointcut("execution(public * com.kim.spring.service.impl.UserServiceImpl.around(..))")
    public void around(){}

    @Pointcut("execution(public * com.kim.spring.service.impl.UserServiceImpl.afterThrowing(..))")
    public void afterThrowing(){}


    /**
     * 前置增强
     * */
    @Before(value="before()")
    public void before(JoinPoint joinPoint){
        System.out.println("前置增强");
        System.out.println(joinPoint);
    }

    /**
     * 后置增强
     * 不管方法是否抛异常都执行
     */
    @After(value="after()")
    public void after(JoinPoint joinPoint){
        System.out.println("后置增强不管方法是否抛异常都执行");
        System.out.println(joinPoint);
    }

    /**
     *后置增强
     * 方法出异常将不执行,
     * rtn是定义传入的返回值参数
     */
    @AfterReturning(value="afterReturning()",returning = "rtn")
    public void afterReturning(JoinPoint joinPoint, Object rtn){

        System.out.println("后置增强出异常将不执行");
        System.out.println(joinPoint);

    }

    /**
     * 环绕增强,方法执行前后执行，还可以控制方法是否执行
     * ProceedingJoinPoint只能用于环绕增强
     */
    @Around("around()")
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
        //获取目标方法上的注解
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        System.out.println(requestMapping);
        //执行目标方法，可控制目标方法是否执行
        Object rtn = joinPoint.proceed();
        System.out.println("按原目标方法执行,返回值:"+rtn);
        Object rtn2 = joinPoint.proceed(new String[]{"kim"});
        System.out.println("修改原目标方法传入参数执行，返回值:"+rtn2);
        //目标方法执行后执行代码
        System.out.println(joinPoint);
        //修改原目标方法的返回值
        UserDO userDO = new UserDO();
        userDO.setName("helen");
        userDO.setAge(36);
        return userDO;
    }

    /**
     * 异常增强
     * ex是定义传入的异常对象
     */
    @AfterThrowing(value="afterThrowing()",throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint,Exception ex){
        System.out.println("异常增强");
        System.out.println(joinPoint);
    }

}

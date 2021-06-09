package com.kim.common.annotation;

import java.lang.annotation.*;

/**
 * @Author kim
 * 自定义注解默认继承Annotation.java这个接口。并且里面的成员变量不能是包装类。
 * @Since 2021/6/9
 */
@Target({ElementType.TYPE, ElementType.METHOD})//作用于类和方法
@Retention(RetentionPolicy.RUNTIME)//始终运行于jvm
@Documented//注解加入javadocsource
@Inherited//允许子类继承父类中的注解
public @interface MyAnnotation {

    /**
     * 注解中的变量
     * */
    int num() default 0;
    String name() default "";
}

package com.kim.springframework.ioc.annotation.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author huangjie
 * @description
 * @date 2021/12/9
 */
@Data
@ToString(callSuper = true)
//将该对象注册为一个bean  ,@Service @Repository 功能等同
@Component
//设置作用域
@Scope("singleton")
//设置懒加载
//@Lazy
public class Car {

    //从properties配置文件中取值注入
    @Value("${id}")
    private Integer id;
    @Value("${name}")
    private String name;
    //直接注入
    @Value("100")
    private Integer size;
    @Value("bmw")
    private String brand;
    /**
     * 根据类型优先注入，推荐使用
     * required 设置是否必须注入
     *  @Qualifier(value="engine")指定注入bean的名称
     */
    @Autowired(required = true)
    @Qualifier(value="engine")
    private Engine engine;
    /**
     * 根据名称优先注入，不推荐使用
     * 在jdk11之后已经被移除，如果要使用需要加入以下依赖
     * <dependency>
     *  <groupId>javax.annotation</groupId>
     *  <artifactId>javax.annotation-api</artifactId>
     * </dependency>
     *
     * 如果同时指定了name和type，则根据name和type查找bean，找不到则抛出异常
     * 如果只指定了name，则根据name查找，找不到则抛出异常
     * 如果只指定了type，则根据type查找，找不到或者找到多个就抛出异常
     * 如果都没指定，则根据name查找，name值就是该属性值。
     * */
    @Resource(name="wheel",type = Wheel.class)
    private Wheel wheel;


    //初始化调用方法
    @PostConstruct
    public void initMethod(){
        System.out.println("car init");
    }

    //bean销毁前调用方法
    @PreDestroy
    public void destoryMethod(){
        System.out.println("car destory");
    }

}

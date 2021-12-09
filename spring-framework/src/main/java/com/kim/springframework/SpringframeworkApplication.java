package com.kim.springframework;

import com.kim.springframework.ioc.annotation.bean.Wheel;
import com.kim.springframework.ioc.annotation.config.BeanConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;

/**
 * @author huangjie
 * @description  注解方式进行spring开发
 * @date 2021/12/9
 */
//声明这是一个配置类
@Configuration
//引入其他xml配置文件
@ImportResource(value = "classpath:application-context.xml")
//引入其他配置类
@Import(value = BeanConfig.class)
//引入properties文件
@PropertySource(value = "classpath:example.properties")
//设置注解扫描包路径，扫描此包和此包的子包的所有spring注解
@ComponentScan(basePackages = "com.kim.springframework")
public class SpringframeworkApplication implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        applicationContext = app;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }



}

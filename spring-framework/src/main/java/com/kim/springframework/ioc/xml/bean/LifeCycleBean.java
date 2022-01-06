package com.kim.springframework.ioc.xml.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author huangjie
 * @description  spring生命周期bean
 * @date 2021/12/9
 */
public class LifeCycleBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware , InitializingBean, DisposableBean {


    private static ApplicationContext applicationContext = null;



    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);

    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz){
        return getApplicationContext().getBeansOfType(clazz);
    }



    /**
     * bean实现BeanNameAware接口重写setBeanName方法
     * 获得当前bean的name值
     * */
    @Override
    public void setBeanName(String s) {
        System.out.println("1、当前生命周期bean的name值:"+s);
    }

    /**
     * bean实现BeanFactoryAware接口重写setBeanFactory方法
     * 获得bean工厂对象，这个对象是ioc容器applicationContext的父接口
     * */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("2、当前生命周期bean工厂对象:"+beanFactory.toString());
    }

    /**
     * bean实现ApplicationContextAware接口重写setApplicationContext方法
     * 获得ioc容器applicationContext对象，这个对象可以获取bean对象
     * */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {
        System.out.println("3、获取ioc容器上下文对象");
        if(LifeCycleBean.applicationContext == null){
            LifeCycleBean.applicationContext  = applicationContext;
        }
    }

    /**
     * bean实现InitializingBean接口重写afterPropertiesSet方法
     * bean在属性注入之后调用的方法
     * */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("4、当前生命周期bean的属性注入之后调用的方法");
    }

    /**
     * bean实现DisposableBean接口重写destroy方法
     * 容器销毁的时候调用此方法
     * */
    @Override
    public void destroy() throws Exception {
        System.out.println("5、当前生命周期bean的销毁方法");
    }



}

package com.kim.springframework.ioc.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author huangjie
 * @description    bean初始化方法后置处理器
 * @date 2022-01-01
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * spring的所有bean的init-method执行前要执行的方法，不包含当前bean(MyBeanPostProcessor)
     * 发生在springbean实例化和依赖装配之后
     * */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBefore:"+beanName);
        return bean;
    }

    /**
     * spring的所有bean的init-method执行后要执行的方法,不包含当前bean(MyBeanPostProcessor)
     * 发生在springbean实例化和依赖装配之后
     * */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfter:"+beanName);
        return bean;
    }
}

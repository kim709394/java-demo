package com.kim.springframework.ioc.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author huangjie
 * @description   BeanDefinition初始化完成时的后置处理器
 * @date 2022-01-01
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    /**
     * 调用此方法时，bean还没有实例化，只是bean都被封装成BeanDefinition对象时调用
     * BeanDefinition对象：封装了bean的各种参数和属性的对象，spring第一步先将bean
     * 的所有参数和属性封装成BeanDefinition对象，再进行后续操作
     * */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory config) throws BeansException {
        BeanDefinition car = config.getBeanDefinition("car");
        //拿到某个BeanDefinition对象时，可以对其进行属性值等进行修改
        System.out.println(car);

    }
}

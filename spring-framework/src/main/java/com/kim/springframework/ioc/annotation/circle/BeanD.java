package com.kim.springframework.ioc.annotation.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author huangjie
 * @description
 * @date 2022/1/13
 */
@Component
public class BeanD {

    public BeanD(){
        System.out.println("beanD init");
    }

    @Autowired
    @Lazy
    private BeanD beanD;


}

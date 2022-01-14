package com.kim.springframework.ioc.annotation.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author huangjie
 * @description
 * @date 2022/1/13
 */
@Component
@Scope("prototype")
public class BeanB {

    @Autowired
    private BeanC beanC;
}

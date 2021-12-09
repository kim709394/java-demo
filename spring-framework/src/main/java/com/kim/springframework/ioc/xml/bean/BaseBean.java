package com.kim.springframework.ioc.xml.bean;

import lombok.Data;

/**
 * @author huangjie
 * @description
 * @date 2021/12/8
 */
@Data
public abstract class BaseBean {

    protected Integer id;
    protected String name;
    protected Integer age;

}

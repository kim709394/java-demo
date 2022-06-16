package com.kim.common.service.impl;

/**
 * @author huangjie
 * @description 反射例子父类
 * @date 2022/6/17
 */
public abstract class AbstractReflectService {

    protected Integer sex;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}

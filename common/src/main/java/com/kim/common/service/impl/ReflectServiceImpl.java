package com.kim.common.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huangjie
 * @description 反射实战例子
 * @date 2022/6/16
 */
@Service
public class ReflectServiceImpl extends AbstractReflectService {

    private String name;
    private Integer age;

    public ReflectServiceImpl() {

    }

    private ReflectServiceImpl(String name,Integer age){
        this.name = name;
        this.age = age;

    }
    @Transactional
    public @ResponseBody Integer addAge(Integer age){
        return this.age+=age;
    }

    private Integer subtractAge(Integer age) throws IllegalArgumentException{
        if(this.age < age){
            throw new IllegalArgumentException("age must not be negative");
        }
        return this.age-=age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

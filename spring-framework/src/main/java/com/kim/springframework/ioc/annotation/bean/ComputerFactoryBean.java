package com.kim.springframework.ioc.annotation.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author huangjie
 * @description   电脑工厂bean，用来生产电脑
 * @date 2021/12/10
 */
//工厂bean本身需要注册为一个bean
@Repository(value="computerFactoryBean")
public class ComputerFactoryBean implements FactoryBean<Computer> {


    /**
     * bean的生产实现
     * */
    @Override
    public Computer getObject() throws Exception {
        Computer computer=new Computer();
        computer.setBrand("联想");
        computer.setCpu("8核");
        computer.setId(1);
        computer.setName("笔记本电脑");
        computer.setRam("16G");
        computer.setRom("500G");
        return computer;
    }

    /**
     * 返回生产的bean的类型
     * */
    @Override
    public Class<?> getObjectType() {
        return Computer.class;
    }

    /**
     * 是否设置为单例
     * */
    @Override
    public boolean isSingleton() {
        return true;
    }
}

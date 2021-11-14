package com.kim.mybatis.objectfactory;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.List;
import java.util.Properties;

/**
 * @author huangjie
 * @description
 * @date 2021-11-14
 */
public class MyObjectFactory extends DefaultObjectFactory {

    //无参实例化方法
    @Override
    public <T> T create(Class<T> type) {
        return super.create(type);
    }

    //有参实例化方法
    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        return super.create(type, constructorArgTypes, constructorArgs);
    }

    //配置文件配置的属性在这个方法获取
    @Override
    public void setProperties(Properties properties) {
        System.out.println(properties);
    }
}

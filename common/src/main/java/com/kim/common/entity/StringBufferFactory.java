package com.kim.common.entity;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * @author huangjie
 * @description
 * @date 2020/3/4
 */
public class StringBufferFactory extends BasePooledObjectFactory<StringBuffer> {

    //对象被创建的时候调用
    @Override
    public StringBuffer create() throws Exception {
        System.out.println("创建方法被调用");
        return new StringBuffer();
    }

    //对象被创建的时候调用
    @Override
    public PooledObject<StringBuffer> wrap(StringBuffer obj) {
        System.out.println("池化方法被调用");
        return new DefaultPooledObject<StringBuffer>(obj);
    }

    //对象被还回的时候调用,相当于将对象初始化的方法
    @Override
    public void passivateObject(PooledObject<StringBuffer> p) throws Exception {
        System.out.println("钝化方法被调用");
        p.getObject().setLength(0);
    }

    //对象被移除的时候调用，销毁对象，关闭流等操作
    @Override
    public void destroyObject(PooledObject<StringBuffer> p) throws Exception {
        System.out.println("销毁执行");
    }
}

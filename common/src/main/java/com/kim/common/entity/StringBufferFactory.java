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
    @Override
    public StringBuffer create() throws Exception {
        System.out.println("创建方法被调用");
        return new StringBuffer();
    }

    @Override
    public PooledObject<StringBuffer> wrap(StringBuffer obj) {
        System.out.println("池化方法被调用");
        return new DefaultPooledObject<StringBuffer>(obj);
    }

    @Override
    public void passivateObject(PooledObject<StringBuffer> p) throws Exception {
        System.out.println("钝化方法被调用");
        p.getObject().setLength(0);
    }

    @Override
    public void destroyObject(PooledObject<StringBuffer> p) throws Exception {

        System.out.println("空闲等待后销毁执行");
    }
}

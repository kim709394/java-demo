package com.kim.common.designmode.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author huangjie
 * @description     jdk动态代理构建类
 * @date 2021/10/29
 */
public class JdkDynamicProxyBuilder<T> {    //泛型T是要代理的对象实现的接口类型


    private T t;

    //传入被代理的对象
    public JdkDynamicProxyBuilder(T t){
        this.t = t;
    }

    //构建代理对象
    public T build(InvocationHandler invocationHandler){
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),t.getClass().getInterfaces(),invocationHandler);

    }


}

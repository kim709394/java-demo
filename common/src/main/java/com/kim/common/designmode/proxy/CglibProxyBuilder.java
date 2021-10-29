package com.kim.common.designmode.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author huangjie
 * @description   cglib代理，原理:在编译过程中通过二进制字节码生成方式生成被代理对象的子类作为代理对象，性能
 * 优于jdk动态代理，不要求目标对象有实现接口，同时要求目标对象不能是final，也不能代理static方法
 * 泛型T是被代理的对象所在的类的类型
 * @date 2021/10/29
 */
public class CglibProxyBuilder<T> {

    private T t;

    //通过构造方法注入被代理的对象
    public CglibProxyBuilder(T t){
        this.t = t;
    }

    public T build(MethodInterceptor methodInterceptor){
        //实例化子类字节码生成器工具类
        Enhancer enhancer=new Enhancer();
        //设置父类
        enhancer.setSuperclass(t.getClass());
        //设置目标类执行方法时的方法拦截器
        enhancer.setCallback(methodInterceptor);
        return (T)enhancer.create();

    }


}

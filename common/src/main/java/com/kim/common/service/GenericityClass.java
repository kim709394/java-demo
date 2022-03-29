package com.kim.common.service;

/**
 * @author huangjie
 * @description
 * @date 2022/3/29
 */
public abstract class GenericityClass<T,K> {

    protected abstract K invoke(T msg);

    protected abstract void invoke2(K k);
}

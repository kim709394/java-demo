package com.kim.common.service;

/**
 * @Author kim
 * @Since 2021/4/21
 * 泛型接口
 */
public interface GenericityInterface<T,K> {

    K invoke(T msg);

    void invoke2(K k);

}

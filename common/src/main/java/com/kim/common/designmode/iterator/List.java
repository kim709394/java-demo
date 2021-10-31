package com.kim.common.designmode.iterator;

/**
 * @author huangjie
 * @description  自定义集合接口
 * @date 2021-10-30
 */
public interface List<T> {

    void add(T t);

    int size();

    T get(int index);

    Iterator<T> iterator();



}

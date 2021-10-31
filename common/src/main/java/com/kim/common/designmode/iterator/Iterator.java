package com.kim.common.designmode.iterator;

/**
 * @author huangjie
 * @description   迭代器模式，为了在不知道集合的具体细节的情况下对集合进行遍历
 * 自定义一个迭代器
 * @date 2021-10-30
 */
public interface Iterator<T> {

    boolean hasNext();

    T next();

}

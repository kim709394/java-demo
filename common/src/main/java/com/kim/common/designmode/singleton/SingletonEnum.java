package com.kim.common.designmode.singleton;

/**
 * @author huangjie
 * @description   单例模式枚举写法，保证单例和线程安全，但是无法在初始化进行一些代码逻辑的操作
 * @date 2021-10-30
 */
public enum SingletonEnum {

    INSTANCE;

    public String foo;

    public void setFoo(String foo){
        this.foo = foo;
    }
}

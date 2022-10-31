package com.kim.common.designmode.observe;

/**
 * @author huangjie
 * @description
 * @date 2022-10-31
 */
public class MyObserver2 implements Observer {
    @Override
    public void observe(String event) {
        System.out.println("观察者2----"+event);
    }
}

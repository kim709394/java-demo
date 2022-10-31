package com.kim.common.designmode.observe;

/**
 * @author huangjie
 * @description   观察者主题接口
 * @date 2022-10-31
 */
public interface Subject {

    /**
     *  注册观察者
     * */
    void registerObsderver(Observer observer);


    /**
     * 注销观察者
     * */
    void unregisterObserver(Observer observer);


    /**
     * 发起事件
     * */
    void notifyObservers(String event);


}

package com.kim.common.designmode.observe;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author huangjie
 * @description
 * @date 2022-10-31
 */
public class MySubject implements Subject {

    //观察者集合，线程安全
    private Set<Observer> observers=new CopyOnWriteArraySet<>();

    @Override
    public void registerObsderver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        observers.stream().forEach(observer -> observer.observe(event));
    }
}

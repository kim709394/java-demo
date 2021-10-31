package com.kim.common.designmode.adapter;

/**
 * @author huangjie
 * @description  适配器模式，给目标接口的所有方法提供一种默认的实现
 * 具体的业务类可以继承该适配器，然后根据需要进行不同方法的自定义实现
 * 适配器模式的作用就是业务类不必完全重写目标接口的所有方法，可以达到按需获取目标接口的方法
 * @date 2021-10-30
 */
public abstract class ConfigAdapter implements Config {

    @Override
    public void operation1() {
        System.out.println("默认实现方法1");
    }

    @Override
    public void operation2() {
        System.out.println("默认实现方法2");
    }

    @Override
    public void operation3() {
        System.out.println("默认实现方法3");
    }
}

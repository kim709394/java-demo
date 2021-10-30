package com.kim.common.designmode.singleton;

/**
 * @author huangjie
 * @description  饿汉式,类加载的时候就初始化，由于加了final所以是线程安全且保证单例
 * 但是在需要传入参数进行实例化的场景无法使用
 * @date 2021-10-30
 */
public class HungrySingle {


    private static final HungrySingle INSTANCE=new HungrySingle();

    public static HungrySingle getInstance(){

        return INSTANCE;
    }

}

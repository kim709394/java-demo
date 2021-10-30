package com.kim.common.designmode.singleton;

/**
 * @author huangjie
 * @description     内部类模式:线程安全，保证单例，懒加载，但是无法带参初始化
 * @date 2021-10-30
 */
public class InternalClassSingle {


    private static class SingletonHolder {

        private static final InternalClassSingle INSTANCE=new InternalClassSingle();
    }

    public static InternalClassSingle getInstance(){
        return SingletonHolder.INSTANCE;
    }

}

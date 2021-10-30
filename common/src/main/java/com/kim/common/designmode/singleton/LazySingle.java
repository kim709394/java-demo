package com.kim.common.designmode.singleton;

/**
 * @author huangjie
 * @description   单例模式，懒汉式：
 * 博客参考: http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/
 * @date 2021-10-30
 */
public class LazySingle {


    private static LazySingle lazySingle;

    //线程不安全写法
    public static LazySingle getInstance(){
        if(lazySingle == null){
            return new LazySingle();
        }
        return lazySingle;
    }

    //线程安全写法，每次调用都会被锁
    public static synchronized LazySingle getInstance2(){
        if(lazySingle == null){
            return new LazySingle();
        }
        return lazySingle;
    }


}

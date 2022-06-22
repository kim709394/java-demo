package com.kim.common.designmode.singleton;

/**
 * @author huangjie
 * @description  双重检验锁模式  double checked locking pattern；推荐
 * @date 2021-10-30
 */
public class DclpSingle {

    //加上volatile防止指令重排
    private static volatile DclpSingle dclpSingle;

    /**保证线程安全、单例、懒加载，实例化时可传入参数进行一些逻辑操作；
    *同时在第二次之后调用getInstance()方法不会被锁
     * */
    public static DclpSingle getInstance(){
        if(dclpSingle == null){
            synchronized (DclpSingle.class){
                if(dclpSingle ==null){
                    /**
                     * 这里执行步骤有三步：
                     * 1、给对象分配内存空间
                     * 2、初始化对象
                     * 3、讲对象内存地址赋值给dclpSingle变量
                     * 此时jdk有可能会发生指令重排，比如执行顺序1、3、2，当并发时线程A执行1、3时，
                     * 线程B执行判断dclpSingle变量不为空直接返回，但是线程A并未执行2，所以线程B
                     * 利用dclpSingle变量调用方法时会报空指针异常。所以依然存在线程安全问题
                     * 因此要给变量加上volatile关键字告诉jdk不进行指令重排
                     *
                     * */
                    return new DclpSingle();
                }
            }
        }
        return dclpSingle;
    }


}

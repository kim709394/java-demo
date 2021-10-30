package com.kim.common.designmode.singleton;

/**
 * @author huangjie
 * @description  双重检验锁模式  double checked locking pattern；推荐
 * @date 2021-10-30
 */
public class DclpSingle {

    private static volatile DclpSingle dclpSingle;

    /**保证线程安全、单例、懒加载，实例化时可传入参数进行一些逻辑操作；
    *同时在第二次之后调用getInstance()方法不会被锁
     * */
    public static DclpSingle getInstance(){
        if(dclpSingle == null){
            synchronized (DclpSingle.class){
                if(dclpSingle ==null){
                    return new DclpSingle();
                }
            }
        }
        return dclpSingle;
    }


}

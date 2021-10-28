package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description   静态方法工厂模式
 * @date 2021/10/28
 */
public class StaticMethodGunFactory {

    public static Gun createAk47(){
        return new Ak47();
    }

    public static Gun createM16(){
        return new M16();
    }

    public static Gun createG36(){
        return new G36();
    }

}

package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description   多个工厂方法模式
 * @date 2021/10/28
 */
public class MultiMethodGunFactory {


    public Gun createAk47() {
        return new Ak47();
    }

    public Gun createM16() {
        return new M16();
    }

    public Gun createG36() {
        return new G36();
    }
}

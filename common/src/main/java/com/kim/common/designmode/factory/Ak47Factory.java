package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description  ak47工厂
 * @date 2021/10/28
 */
public class Ak47Factory implements GunFactory {
    @Override
    public Gun createGun() {
        return new Ak47();
    }
}

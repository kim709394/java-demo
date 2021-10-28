package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description  m16工厂
 * @date 2021/10/28
 */
public class M16Factory implements GunFactory {
    @Override
    public Gun createGun() {
        return new M16();
    }
}

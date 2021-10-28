package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description  g36工厂
 * @date 2021/10/28
 */
public class G36Factory implements GunFactory {
    @Override
    public Gun createGun() {
        return new G36();
    }
}

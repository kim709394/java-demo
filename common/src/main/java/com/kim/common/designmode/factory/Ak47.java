package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description  ak47自动步枪
 * @date 2021/10/28
 */
public class Ak47 implements Gun {

    @Override
    public void fire() {
        System.out.println("ak47射击");
    }
}

package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description  美国M16步枪
 * @date 2021/10/28
 */
public class M16 implements Gun {
    @Override
    public void fire() {
        System.out.println("m16 发射");
    }
}

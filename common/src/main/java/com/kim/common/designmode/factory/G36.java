package com.kim.common.designmode.factory;

/**
 * @author huangjie
 * @description 德国G36步枪
 * @date 2021/10/28
 */
public class G36 implements Gun {
    @Override
    public void fire() {
        System.out.println("g36 射击");
    }
}

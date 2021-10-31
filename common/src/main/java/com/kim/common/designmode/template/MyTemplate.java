package com.kim.common.designmode.template;

/**
 * @author huangjie
 * @description
 * @date 2021-10-31
 */
public class MyTemplate extends AbstractTemplate {


    @Override
    public void abstractMethod() {
        System.out.println("个性化逻辑");
    }

    @Override
    public void doHook() {
        System.out.println("钩子逻辑");
    }



}

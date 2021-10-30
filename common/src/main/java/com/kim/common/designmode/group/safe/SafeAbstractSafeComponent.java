package com.kim.common.designmode.group.safe;

/**
 * @author huangjie
 * @description  公共组件实现
 * @date 2021-10-30
 */
public abstract class SafeAbstractSafeComponent implements SafeComponent {

    protected String name;

    public SafeAbstractSafeComponent(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

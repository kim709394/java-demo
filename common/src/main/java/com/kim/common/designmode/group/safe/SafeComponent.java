package com.kim.common.designmode.group.safe;

/**
 * @author huangjie
 * @description   公共组件
 * @date 2021-10-30
 */
public interface SafeComponent {

    //获取名字
    String getName();

    //业务方法
    public void operation(String prefix);
}

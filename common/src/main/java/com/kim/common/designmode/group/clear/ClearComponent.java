package com.kim.common.designmode.group.clear;

import java.util.List;

/**
 * @author huangjie
 * @description   公共组件
 * @date 2021-10-30
 */
public interface ClearComponent {

    //获取名字
    String getName();

    //业务方法
    void operation(String prefix);

    //新增子节点
    void addChild(ClearComponent clearComponent);

    //删除子节点
    void removeChild(int index);

    //获取子节点
    ClearComponent getChild(int index);

    //获取全部子节点
    List<ClearComponent> getChildren();

    //获取父节点
    ClearComponent getParent();

}

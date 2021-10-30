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
    public void operation(String prefix);

    public void addChild(ClearComponent clearComponent);

    //删除子节点
    public void removeChild(int index);

    //获取子节点
    public ClearComponent getChild(int index);

    //获取全部子节点
    public List<ClearComponent> getChildren();

    //获取父节点
    public ClearComponent getParent();

}

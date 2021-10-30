package com.kim.common.designmode.group.clear;

/**
 * @author huangjie
 * @description 树叶节点
 * @date 2021-10-30
 */
public class ClearLeaf extends ClearAbstractClearComponent {

    public ClearLeaf(String name, ClearComponent parent) {
        super(name);
        this.parent = parent;
    }


    @Override
    public void operation(String prefix) {
        System.out.println(prefix+name);
    }
}

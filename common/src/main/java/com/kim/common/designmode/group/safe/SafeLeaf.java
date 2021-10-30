package com.kim.common.designmode.group.safe;

/**
 * @author huangjie
 * @description 树叶节点
 * @date 2021-10-30
 */
public class SafeLeaf extends SafeAbstractSafeComponent {

    private SafeComponent parent;

    public SafeLeaf(String name, SafeComponent parent) {
        super(name);
        this.parent = parent;
    }


    //获取父节点
    public SafeComponent getParent(){
        return parent;
    }

    @Override
    public void operation(String prefix) {
        System.out.println(prefix+name);
    }
}

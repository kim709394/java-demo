package com.kim.common.designmode.group.safe;

/**
 * @author huangjie
 * @description 树枝节点，比根节点多了一个父节点
 * @date 2021-10-30
 */
public class SafeBranch extends SafeRoot {

    private SafeComponent parent;

    public SafeBranch(String name, SafeComponent parent) {
        super(name);
        this.parent = parent;
    }


    //获取父节点
    public SafeComponent getParent(){
        return parent;
    }

}

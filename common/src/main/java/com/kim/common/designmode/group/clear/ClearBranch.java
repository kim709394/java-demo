package com.kim.common.designmode.group.clear;

/**
 * @author huangjie
 * @description 树枝节点，比根节点多了一个父节点
 * @date 2021-10-30
 */
public class ClearBranch extends ClearRootAndBranch {



    public ClearBranch(String name, ClearComponent parent) {
        super(name,parent);
    }


    //获取父节点
    @Override
    public ClearComponent getParent(){
        return parent;
    }



}

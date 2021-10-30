package com.kim.common.designmode.group.clear;

import java.util.List;

/**
 * @author huangjie
 * @description 根节点和树枝节点的父类
 * @date 2021-10-30
 */
public abstract class ClearRootAndBranch extends ClearAbstractClearComponent {

    public ClearRootAndBranch(String name){
        super(name);
    }

    public ClearRootAndBranch(String name, ClearComponent parent) {
        super(name);
        this.parent = parent;
    }

    //新增子节点
    @Override
    public void addChild(ClearComponent clearComponent){
        children.add(clearComponent);
    }

    //删除子节点
    @Override
    public void removeChild(int index){
        children.remove(index);
    }

    //获取子节点
    @Override
    public ClearComponent getChild(int index){
        return children.get(index);
    }

    //获取全部子节点
    @Override
    public List<ClearComponent> getChildren(){
        return children;
    }

    //业务方法
    @Override
    public void operation(String prefix){



        prefix+=this.name;
        System.out.println(prefix);
        if(children.size()>0){
            prefix+="=>";
        }
        for (ClearComponent clearComponent : children
        ) {
            clearComponent.operation(prefix);
        }


    }

}

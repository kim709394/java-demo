package com.kim.common.designmode.group.clear;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjie
 * @description  公共组件实现
 * @date 2021-10-30
 */
public abstract class ClearAbstractClearComponent implements ClearComponent {

    protected String name;

    protected ClearComponent parent;

    protected List<ClearComponent> children = new ArrayList<>();

    public ClearAbstractClearComponent(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }


    //获取父节点
    public ClearComponent getParent(){
        throw new ClearGroupException("根节点无法获取父节点");
    }

    //新增子节点
    public void addChild(ClearComponent clearComponent){
        throw new ClearGroupException("叶子节点无法添加子节点");
    }

    //删除子节点
    public void removeChild(int index){
        throw new ClearGroupException("叶子节点无法删除子节点");
    }

    //获取子节点
    public ClearComponent getChild(int index){
        throw new ClearGroupException("叶子节点无法获取子节点");
    }

    //获取全部子节点
    public List<ClearComponent> getChildren(){
        throw new ClearGroupException("叶子节点无法获取子节点");
    }

    //业务方法
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

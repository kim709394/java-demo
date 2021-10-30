package com.kim.common.designmode.group.safe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangjie
 * @description  组合模式，其实就是树形结构；
 * 安全形式写法，安全与透明是针对调用方而言，即调用方是否需要区分根节点和树枝节点以及叶子节点
 * 安全形式：需要区分，；透明模式：不需要区分；
 * @date 2021-10-30
 */
public class SafeRoot extends SafeAbstractSafeComponent {

    private List<SafeComponent> children = new ArrayList<>();

    public SafeRoot(String name) {
        super(name);
    }

    //新增子节点
    public void addChild(SafeComponent safeComponent){
        children.add(safeComponent);
    }

    //删除子节点
    public void removeChild(int index){
        children.remove(index);
    }

    //获取子节点
    public SafeComponent getChild(int index){
        return children.get(index);
    }

    //获取全部子节点
    public List<SafeComponent> getChildren(){
        return children;
    }

    //业务方法
    public void operation(String prefix){



        prefix+=this.name;
        System.out.println(prefix);
        if(children.size()>0){
            prefix+="=>";
        }
        for (SafeComponent safeComponent : children
             ) {
            safeComponent.operation(prefix);
        }


    }






}

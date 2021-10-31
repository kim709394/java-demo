package com.kim.common.designmode.template;

/**
 * @author huangjie
 * @description   模板方法模式，提供一个抽象类，作为基本的模板
 * 这个模板包括了基本方法(子类不能重写)，钩子方法(空方法，子类可以实现可不实现)，以及一些已经实现的私有方法，
 * 抽象方法：子类必须实现。子类通过继承这个模板方法，进行一些遵循这个模板提供个性化实现的逻辑
 * @date 2021-10-31
 */
public abstract class AbstractTemplate {
    //抽象方法
    protected abstract void abstractMethod();

    //钩子方法,命名通常以do开头
    protected void doHook(){}

    //私有方法
    private void defMethod(){
        System.out.println("私有逻辑，不必实现");
    }

    //基本方法
    public final void base(){
        abstractMethod();
        doHook();
        defMethod();
        System.out.println("基本逻辑");
    }


}

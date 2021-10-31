package com.kim.common.designmode.decorator;

/**
 * @author huangjie
 * @description  装饰者模式，有点类似静态代理模式，实现目标接口，对目标实现类进行装饰
 * 然后添加一些装饰的实现
 * @date 2021-10-30
 */
//南航CF1000航班运行情况
public class SouthAirlinesCF1000Decorator extends AbstractSouthAirlines {



    public SouthAirlinesCF1000Decorator(Airplane southAirlines){
        super(southAirlines);
    }

    @Override
    public void fly() {
        super.up();
        southAirlines.fly();
        super.down();
    }







}

package com.kim.common.designmode.decorator;

/**
 * @author huangjie
 * @description
 * @date 2021-10-30
 */
public abstract class AbstractSouthAirlines implements Airplane{


    protected Airplane southAirlines;

    public AbstractSouthAirlines(Airplane southAirlines){
        this.southAirlines = southAirlines;
    }

    protected void up(){
        System.out.println("南航起飞");
    }

    protected void down(){
        System.out.println("南航降落");
    }

}

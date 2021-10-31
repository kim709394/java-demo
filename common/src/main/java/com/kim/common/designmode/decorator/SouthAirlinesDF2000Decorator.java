package com.kim.common.designmode.decorator;

/**
 * @author huangjie
 * @description  南航DF2000航班运行情况
 * @date 2021-10-30
 */

public class SouthAirlinesDF2000Decorator extends AbstractSouthAirlines {

    public SouthAirlinesDF2000Decorator(Airplane southAirlines){
        super(southAirlines);
    }

    @Override
    public void fly() {
        super.up();
        southAirlines.fly();
        this.addOil();
        super.down();
    }

    //空中加油
    private void addOil(){
        System.out.println("空中加油");
    }
}

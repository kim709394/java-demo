package com.kim.common.designmode.decorator;

/**
 * @author huangjie
 * @description
 * @date 2021-10-30
 */
public class SouthAirlines implements Airplane {

    @Override
    public void fly() {
        System.out.println("南方航空飞行中");
    }
}

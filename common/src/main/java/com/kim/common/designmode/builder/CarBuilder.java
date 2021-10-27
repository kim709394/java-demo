package com.kim.common.designmode.builder;

/**
 * @author huangjie
 * @description
 * @date 2021/10/27
 */
public class CarBuilder {

    private Car car;

    public CarBuilder(){
        car=new Car();
    }

    public CarBuilder engine(String engine){
        car.setEngine(engine);
        return this;
    }


    public CarBuilder body(String body){
        car.setBody(body);
        return this;
    }
    public CarBuilder wheel(String wheel){
        car.setWheel(wheel);
        return this;
    }
    public CarBuilder electronic(String electronic){
        car.setElectronic(electronic);
        return this;
    }

    public Car build(){
        return this.car;
    }



}

package com.kim.common.designmode.proxy;

import com.kim.common.designmode.builder.CarBuilder;
import com.kim.common.designmode.pojo.Car;

/**
 * @author huangjie
 * @description
 * @date 2021/10/28
 */
public class CarServiceImpl implements CarService {


    @Override
    public Car get(Integer id) {
        System.out.println("目标方法执行");
        return new CarBuilder().id(id).engine("发动机").wheel("车轮").electronic("电器设备").body("车身").build();
    }
}

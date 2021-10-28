package com.kim.common.designmode.proxy;

import com.kim.common.designmode.pojo.Car;

/**
 * @author huangjie
 * @description    静态代理，即编译前硬编码进行对目标对象的代理
 * @date 2021/10/28
 */
public class CarServiceStaticProxyImpl implements CarService {

    private CarService carService;

    public CarServiceStaticProxyImpl(CarService carService){
        this.carService = carService;
    }


    @Override
    public Car get(Integer id) {
        System.out.println("前置增强");
        Car car = null;
        try {
             car = carService.get(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("异常增强");
        } finally {
            System.out.println("后置增强");
        }

        return car;
    }
}

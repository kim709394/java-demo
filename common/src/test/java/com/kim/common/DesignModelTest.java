package com.kim.common;

import com.kim.common.designmode.factory.*;
import com.kim.common.designmode.pojo.Car;
import com.kim.common.designmode.builder.CarBuilder;
import com.kim.common.designmode.pojo.Computer;
import com.kim.common.designmode.proxy.CarService;
import com.kim.common.designmode.proxy.CarServiceImpl;
import com.kim.common.designmode.proxy.CarServiceStaticProxyImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huangjie
 * @description
 * @date 2021/10/27
 */
@DisplayName("设计模式测试")
public class DesignModelTest {


    @Test
    @DisplayName("构建者模式")
    public void builder() {
        //构建汽车
        Car car = new CarBuilder().id(1).engine("发动机").body("车身").wheel("轮胎").electronic("电器设备").build();
        System.out.println(car);
        //构建电脑
        System.out.println("------利用lombok @Builder注解实现构建者生产对象");
        Computer computer = Computer.builder().cpu("英特尔").brand("lenovo").memory("16G").name("笔记本电脑").size("15寸").videoCard("独显").build();
        System.out.println(computer);
    }

    @Test
    @DisplayName("简单工厂模式")
    public void simpleFactory(){
        SimpleGunFactory simpleGunFactory=new SimpleGunFactory();
        Gun ak47 = simpleGunFactory.createGun("ak47");
        ak47.fire();
        Gun m16 = simpleGunFactory.createGun("m16");
        m16.fire();
        Gun g36 = simpleGunFactory.createGun("g36");
        g36.fire();
    }

    @Test
    @DisplayName("静态方法工厂模式")
    public void staticMethodFactory(){
        Gun ak47 = StaticMethodGunFactory.createAk47();
        ak47.fire();
        Gun m16 = StaticMethodGunFactory.createM16();
        m16.fire();
        Gun g36 = StaticMethodGunFactory.createG36();
        g36.fire();
    }

    @Test
    @DisplayName("多个工厂方法模式")
    public void multiMethodFactory(){

        MultiMethodGunFactory multiMethodGunFactory=new MultiMethodGunFactory();
        Gun ak47 = multiMethodGunFactory.createAk47();
        ak47.fire();
        Gun m16 = multiMethodGunFactory.createM16();
        m16.fire();
        Gun g36 = multiMethodGunFactory.createG36();
        g36.fire();
    }

    @Test
    @DisplayName("抽象方法工厂模式")
    public void abstractMethodFactory(){
        GunFactory gunFactory1=new Ak47Factory();
        Gun gun1 = gunFactory1.createGun();
        GunFactory gunFactory2=new M16Factory();
        Gun gun2=gunFactory2.createGun();
        GunFactory gunFactory3=new G36Factory();
        Gun gun3 = gunFactory3.createGun();
        gun1.fire();
        gun2.fire();
        gun3.fire();

    }


    @Test
    @DisplayName("静态代理模式")
    public void staticProxy(){

        CarService carService=new CarServiceImpl();
        CarService carServiceProxy = new CarServiceStaticProxyImpl(carService);
        System.out.println("--------------原生方法---------");
        Car car = carService.get(1);
        System.out.println("--------------代理方法---------");
        Car car2 = carServiceProxy.get(1);
    }

}

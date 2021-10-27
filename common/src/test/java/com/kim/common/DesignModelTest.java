package com.kim.common;

import com.kim.common.designmode.builder.Car;
import com.kim.common.designmode.builder.CarBuilder;
import com.kim.common.designmode.builder.Computer;
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
        Car car = new CarBuilder().engine("发动机").body("车身").wheel("轮胎").electronic("电器设备").build();
        System.out.println(car);
        //构建电脑
        System.out.println("------利用lombok @Builder注解实现构建者生产对象");
        Computer computer = Computer.builder().cpu("英特尔").brand("lenovo").memory("16G").name("笔记本电脑").size("15寸").videoCard("独显").build();
        System.out.println(computer);
    }

}

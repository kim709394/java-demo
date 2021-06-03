package com.kim.common.entity;

import lombok.Data;

/**
 * @Author kim
 * @Since 2021/6/3
 */
@Data
public class PersonB implements Cloneable{

    private String name;   //名字
    private Integer age;   //年龄
    private CarB car;       //座驾

    //clone方法默认是浅拷贝，因此成员变量也要进行clone再重新set，才能实现整体的深拷贝
    @Override
    public PersonB clone() throws CloneNotSupportedException {
        PersonB personB = (PersonB)super.clone();
        CarB carB = this.car.clone();
        personB.setCar(carB);
        return personB;
    }
}

package com.kim.common.entity;

import lombok.Data;

/**
 * @Author kim
 * @Since 2021/6/3
 */
/**
 * 实现Cloneable接口，重写clone()方法实现浅拷贝，因为clone()方法是父类Object的，而且是protected的，所以需要重写成public
 */
@Data
public class PersonA implements Cloneable {


    private String name;   //名字
    private Integer age;   //年龄
    private CarA car;       //座驾


    //重写clone方法，实现浅拷贝
    @Override
    public PersonA clone() throws CloneNotSupportedException {
        return (PersonA) super.clone();
    }
}

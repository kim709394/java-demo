package com.kim.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author kim
 * @Since 2021/6/3
 */
@Data
public class PersonC implements Serializable {

    private static final long serialVersionUID = -8287629760171152688L;
    private String name;   //名字
    private Integer age;   //年龄
    private CarC car;       //座驾



}

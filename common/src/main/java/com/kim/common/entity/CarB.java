package com.kim.common.entity;

import lombok.Data;

/**
 * @Author kim
 * @Since 2021/6/3
 */
@Data
public class CarB implements Cloneable {

    private String brand;   //品牌

    private Integer size;   //大小

    @Override
    public CarB clone() throws CloneNotSupportedException {
        return (CarB)super.clone();
    }
}

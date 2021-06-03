package com.kim.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author kim
 * @Since 2021/6/3
 */
@Data
public class CarC implements Serializable {


    private static final long serialVersionUID = -4584455743969090363L;
    private String brand;   //品牌

    private Integer size;   //大小

}

package com.kim.springframework.ioc.annotation.bean;

import lombok.Data;

/**
 * @author huangjie
 * @description
 * @date 2021/12/10
 */
@Data
public class Computer {

    private Integer id;
    private String name;
    private String brand;
    private String cpu;
    private String ram;
    private String rom;
}

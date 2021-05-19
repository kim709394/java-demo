package com.kim.common.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author kim
 * @Since 2021/5/19
 */
@Data
public class A extends C{

    private String name;
    private Integer id;
    private List<String> colors;


}

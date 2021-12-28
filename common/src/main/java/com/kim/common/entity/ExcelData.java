package com.kim.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author huangjie
 * @description  excel模板类
 * @date 2021/12/28
 */
@Data
public class ExcelData {

    private Integer id;
    private String name;
    private Double price;
    private Date time;



}

package com.kim.springframework.transaction;

import lombok.Data;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2022/1/18
 */
@Data
public class Goods {

    private Integer id;
    private String name;
    private Double price;
    private Date createdAt;
    private Date deletedAt;


}

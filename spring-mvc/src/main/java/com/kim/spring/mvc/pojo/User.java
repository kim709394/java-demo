package com.kim.spring.mvc.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2022/3/10
 */
@Data
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private Date createTime;

}

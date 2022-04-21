package com.kim.spring.security.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2022/4/21
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String password;
    private Integer age;
    private Date createdAt;


}

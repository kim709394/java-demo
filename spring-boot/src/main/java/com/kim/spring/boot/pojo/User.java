package com.kim.spring.boot.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2022-03-19
 */
@Data
public class User {

    private Integer id;
    private String name;
    private Date createTime;


    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", createTime=" + createTime + '}';
    }
}

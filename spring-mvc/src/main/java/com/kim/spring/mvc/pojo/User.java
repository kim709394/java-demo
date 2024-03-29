package com.kim.spring.mvc.pojo;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2022/3/10
 */
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

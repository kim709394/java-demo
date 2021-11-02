package com.kim.mybatis.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2021/11/2
 */
@Data
public abstract class BasePojo {

    protected Integer id;
    protected String name;
    protected Date createdAt;
    protected Date deletedAt;
}

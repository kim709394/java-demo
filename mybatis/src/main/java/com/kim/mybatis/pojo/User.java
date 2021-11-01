package com.kim.mybatis.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author huangjie
 * @description  用户实体
 * @date 2021-10-31
 */
@Data
@Builder
@ToString
public class User {

    private Integer id;
    private String name;
    private String password;
    private Date createdAt;
    private Date deletedAt;


}

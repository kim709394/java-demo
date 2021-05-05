package com.kim.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
@Data
@Document(collation = "user")  //对应mongodb的集合名称
public class UserDO {

    @Id   //指定主键
    private String id;
    private String name;
    private Integer age;
    private String telephone;
    @Indexed(direction = IndexDirection.DESCENDING)  //为该字段创建索引
    private Date createdTime;
    private Boolean deleted;  //逻辑删除字段

}

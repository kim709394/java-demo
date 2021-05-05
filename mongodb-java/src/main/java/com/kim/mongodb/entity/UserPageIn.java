package com.kim.mongodb.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
@Data
public class UserPageIn {

    private String name;

    private Date createdTimeStart;

    private Date createdTimeEnd;

    private Integer pageNo;

    private Integer pageSize;

}

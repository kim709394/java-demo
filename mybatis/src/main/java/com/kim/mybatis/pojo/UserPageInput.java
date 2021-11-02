package com.kim.mybatis.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2021/11/2
 */
@Data
@Builder
@ToString
public class UserPageInput {

    private Integer pageNo;         //第几页

    private Integer pageSize;       //数据容量

    private String name;        //分页条件

    private String password;        //密码

    private Date createdAtStart;    //起始创建时间

    private Date createdAtEnd;      //结束创建时间

    private Integer limit;


}

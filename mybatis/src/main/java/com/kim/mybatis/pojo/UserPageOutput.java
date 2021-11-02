package com.kim.mybatis.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/2
 */
@Data
@ToString
public class UserPageOutput<T> {

    private List<T> records;    //分页数据集合

    private Integer recordCount;    //总记录数

    private Integer total;      //总页数


}

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
@ToString
public class User extends BasePojo{


    private String password;



}

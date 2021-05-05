package com.kim.mongodb.entity;

import lombok.Data;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
@Data
public class UserPageOut {

    private List<UserDO> userDOs;

    private Integer count;


}

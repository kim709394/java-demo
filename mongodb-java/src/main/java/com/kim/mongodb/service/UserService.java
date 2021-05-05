package com.kim.mongodb.service;

import com.kim.mongodb.entity.UserDO;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */

public interface UserService {

    //新增
    String add(UserDO userDO);

    //修改
    void update(UserDO userDO);

    //删除
    void delete(String id);

    //逻辑删除
    void deleteByLogic(String id);

    //查询单个
    UserDO get(String id);

    //查询全部
    List<UserDO> list();

    //example用法
    List<UserDO> examleLists();

}

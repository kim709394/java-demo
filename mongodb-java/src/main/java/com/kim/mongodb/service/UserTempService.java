package com.kim.mongodb.service;

import com.kim.mongodb.entity.UserDO;
import com.kim.mongodb.entity.UserPageIn;
import com.kim.mongodb.entity.UserPageOut;

import java.util.List;
import java.util.Map;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
public interface UserTempService {

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

    //分页查询
    UserPageOut doPage(UserPageIn userPageIn);

    //以名称字段进行分组查询
    void groupBy();

}

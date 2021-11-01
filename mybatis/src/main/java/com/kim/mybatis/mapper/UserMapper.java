package com.kim.mybatis.mapper;

import com.kim.mybatis.pojo.User;

import java.util.List;

/**
 * @author huangjie
 * @description
 * @date 2021/11/1
 */
public interface UserMapper {

    int addUser(User user);

    List<User> findAll();

    int updateUser(User user);

    int deleteUser(Integer id);

    User findById(Integer id);

}

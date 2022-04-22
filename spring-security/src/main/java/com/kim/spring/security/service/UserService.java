package com.kim.spring.security.service;

import com.kim.spring.security.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huangjie
 * @description
 * @date 2022/4/21
 */
public interface UserService {

    Map<Integer,User> getUsers();

    //登录认证
    User getUserByName(String username);

    List<User> list();

    void add( User user);


    void update(User user);

    User get(Integer id);

    void del(Integer id);

    User getCurrent();

    //添加验证码
    void putVerifyCode(String key,String verifyCode);


    //获取验证码
    Map<String,String> getVerifyCode(String key);

    //删除验证码
    void removeVerifyCode(String key);
}

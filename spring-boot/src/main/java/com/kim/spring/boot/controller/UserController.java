package com.kim.spring.boot.controller;

import com.kim.spring.boot.pojo.User;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author huangjie
 * @description
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/user")
@Profile("dev")     //dev环境下生效
public class UserController {


    @GetMapping("/get")
    public User get(){
        User user=new User();
        user.setId(1);
        user.setName("mike");
        user.setCreateTime(new Date());
        return user;
    }




}

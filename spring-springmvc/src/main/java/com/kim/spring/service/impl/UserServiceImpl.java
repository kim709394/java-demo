package com.kim.spring.service.impl;

import com.kim.spring.entity.UserDO;
import com.kim.spring.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author kim
 * @Since 2021/5/27
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    public UserDO before(String name) {
        UserDO userDO=new UserDO();
        userDO.setAge(18);
        userDO.setName(name);
        System.out.println("目标方法执行");
        return userDO;
    }

    @Override
    public UserDO after(String name) {
        UserDO userDO=new UserDO();
        userDO.setAge(18);
        userDO.setName(name);
        System.out.println("目标方法执行");
        return userDO;
    }

    @Override
    public UserDO afterReturning(String name) {
        UserDO userDO=new UserDO();
        userDO.setAge(18);
        userDO.setName(name);
        System.out.println("目标方法执行");
        return userDO;
    }

    @Override
    @RequestMapping("/hello")
    public UserDO around(String name) {
        UserDO userDO=new UserDO();
        userDO.setAge(18);
        userDO.setName(name);
        System.out.println("目标方法执行");
        return userDO;
    }

    @Override
    public UserDO afterThrowing(String name) {
        UserDO userDO=new UserDO();
        userDO.setAge(18);
        userDO.setName(name);
        System.out.println("目标方法执行");
        throw new RuntimeException();
    }
}

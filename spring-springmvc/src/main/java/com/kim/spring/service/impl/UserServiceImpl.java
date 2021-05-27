package com.kim.spring.service.impl;

import com.kim.spring.entity.UserDO;
import com.kim.spring.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author kim
 * @Since 2021/5/27
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    public UserDO get() {
        UserDO userDO=new UserDO();
        userDO.setAge(18);
        userDO.setName("mike");
        return userDO;
    }
}

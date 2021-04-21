package com.kim.common.service.impl;

import com.kim.common.entity.User;
import com.kim.common.service.GenericityInterface;

import java.util.List;
import java.util.Map;

/**
 * @Author kim
 * @Since 2021/4/21
 * 泛型实现类
 */
public class Genericity implements GenericityInterface<List<User<User.Car>>, Map<String,Object>> {


    @Override
    public Map<String, Object> invoke(List<User<User.Car>> msg) {
        return null;
    }

    @Override
    public void invoke2(Map<String, Object> stringObjectMap) {

    }
}

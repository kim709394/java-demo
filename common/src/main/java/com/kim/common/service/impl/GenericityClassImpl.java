package com.kim.common.service.impl;

import com.kim.common.entity.User;
import com.kim.common.service.GenericityClass;

import java.util.List;
import java.util.Map;

/**
 * @author huangjie
 * @description
 * @date 2022/3/29
 */
public class GenericityClassImpl extends GenericityClass<List<User<User.Car>>, Map<String,Object>> {
    @Override
    protected Map<String, Object> invoke(List<User<User.Car>> msg) {
        return null;
    }

    @Override
    protected void invoke2(Map<String, Object> stringObjectMap) {

    }
}

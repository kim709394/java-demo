package com.kim.spring.data.redis.service.impl;

import com.kim.spring.data.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author huangjie
 * @description
 * @date 2022-12-16
 */
public class RedisServiceImpl implements RedisService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void stringRedisTemplateExecute() {

    }

    @Override
    public void redisTemplateExecute() {

    }
}

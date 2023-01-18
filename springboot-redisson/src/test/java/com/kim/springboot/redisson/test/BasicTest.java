package com.kim.springboot.redisson.test;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BasicTest {

    @Autowired
    private RedissonClient client;


    @DisplayName("分布式锁")
    @Test
    public void distributedLock(){

    }
}

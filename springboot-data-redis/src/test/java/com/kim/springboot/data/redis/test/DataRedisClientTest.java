package com.kim.springboot.data.redis.test;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author huangjie
 * @description
 * @date 2023-01-09
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DataRedisClientTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Serializable,Serializable> redisTemplate;

    @Test
    @DisplayName("字符串类型")
    public void string(){

        //设置值
        stringRedisTemplate.opsForValue().set("k1:001","v1");
        //获取值
        String v1 = stringRedisTemplate.opsForValue().get("k1:001");
        System.out.println("key k1:001取出来的值："+v1);
        String v2 = stringRedisTemplate.opsForValue().getAndSet("k1:002", "v2");
        System.out.println("get and set取出来的值是："+v2);
        //当key不存在的时候才会赋值,setnx
        stringRedisTemplate.opsForValue().setIfAbsent("k1:003", "v3");
        //incr递增数字+1
        stringRedisTemplate.opsForValue().increment("k1:004");
        //decr递减数字-1
        stringRedisTemplate.opsForValue().decrement("k1:004");
        //incrby递增指定的整数
        stringRedisTemplate.opsForValue().increment("k1:005",2);
        //decrby递减指定的整数
        stringRedisTemplate.opsForValue().decrement("k1:005",2);
        //append向尾部增加值
        stringRedisTemplate.opsForValue().append("k1:003","v3");
        //strlen获取字符串长度
        Long size = stringRedisTemplate.opsForValue().size("k1:003");
        System.out.println("k1:003的值的字符串长度："+size);

    }

    @DisplayName("查找key")
    @Test
    public void keys(){
        Set<String> k1 = stringRedisTemplate.keys("k1:*");
        k1.stream().forEach(s -> System.out.println(s));
        Set<String> k003 = stringRedisTemplate.keys("*:003");
        k003.stream().forEach(s -> System.out.println(s));
    }

    @DisplayName("ttl，设置和获取key的过期时间")
    @Test
    public void ttl(){
        //设置key的过期时间为5分钟
        stringRedisTemplate.expire("k1:001",5, TimeUnit.MINUTES);
        Long expire = stringRedisTemplate.getExpire("k1:001");
        System.out.println("k1:001的过期时间："+expire);
    }

    @DisplayName("删除key")
    @Test
    public void deleteKey(){
        stringRedisTemplate.delete("k1:001");
    }







}

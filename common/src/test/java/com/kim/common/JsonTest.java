package com.kim.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.common.entity.User;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @Author kim
 * @Since 2021/4/21
 * json转换测试
 */
public class JsonTest {

    /**
     *
     * jackson 带泛型的实体类转换
     */
    @Test
    public void jsonConvert() throws Exception{
        User<User.Car> user=new User();
        user.setAge(31);
        user.setName("盖茨");
        User.Car car=new User.Car();
        car.setColor("yellow");
        car.setWeight("50kg");
        List<User.House> houses=new ArrayList<>();
        for(int i=0;i<10;i++){
            User.House house=new User.House();
            house.setArea(i+0.0);
            house.setPrice(i+0.0);
            houses.add(house);
        }
        user.setT(car);
        user.setHouses(houses);
        ObjectMapper objectMapper=new ObjectMapper();
        List<User<User.Car>> users=new ArrayList<>();
        users.add(user);
        String s = objectMapper.writeValueAsString(users);
        //Map<String,Object> newUser=objectMapper.readValue(s,Map.class);
        List<User<User.Car>> newUser=objectMapper.readValue(s,new TypeReference<List<User<User.Car>>>(){});
        System.out.println(s);
        System.out.println(newUser);
        System.out.println(newUser.get(0).getT().getColor());

    }

    @Data
    static class Foo{
        private Date time;
    }

    @Test
    public void fastJsonTimeZone(){
        //TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
        ParserConfig.getGlobalInstance().setSafeMode(true);
        Foo foo = JSON.parseObject("{\"time\":\"2023-10-16\"}",Foo.class);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(foo.getTime()));


    }




}

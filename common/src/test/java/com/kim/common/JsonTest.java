package com.kim.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.common.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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




}

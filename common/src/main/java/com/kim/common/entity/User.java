package com.kim.common.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author kim
 * @Since 2021/4/21
 */
@Data
public class User<T> {

    private String name;
    private Integer age;
    private Car car;
    private List<House> houses;
    private T t;

    @Data
    public static class Car{
        private String color;
        private String weight;
    }
    @Data
    public static class House{
        private Double area;
        private Double price;

    }


}

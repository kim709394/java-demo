package com.kim.common.designmode.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author huangjie
 * @description
 * @date 2021/10/27
 */
@Data
@ToString
public class Car {
    private Integer id;
    private String engine;  //发动机
    private String body;    //车身
    private String wheel;   //轮子
    private String electronic;  //电子设备



}

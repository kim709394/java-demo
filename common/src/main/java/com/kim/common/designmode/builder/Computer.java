package com.kim.common.designmode.builder;

import lombok.Builder;
import lombok.Data;

/**
 * @author huangjie
 * @description
 * @date 2021/10/27
 */
@Builder
@Data
public class Computer {

    private String name;    //电脑名字
    private String brand;   //品牌
    private String size;    //尺寸
    private String cpu;     //cpu
    private String memory;  //内存
    private String videoCard;   //显卡


}

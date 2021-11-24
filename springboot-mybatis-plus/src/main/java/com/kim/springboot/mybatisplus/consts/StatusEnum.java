package com.kim.springboot.mybatisplus.consts;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @Author kim
 * 工单状态
 * @Since 2021/6/10
 */
public enum StatusEnum implements IEnum<Integer> {

    PROCESSING(0,"处理中"),
    SOLVED(1,"已解决"),
    CLOSED(2,"已关闭");


    public final Integer code;
    public final String name;

    StatusEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }
}

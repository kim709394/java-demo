package com.kim.springboot.mybatisplus.consts;

/**
 * kim
 * 2021/4/7
 */
public enum ErrorEnum {

    CODE_2000(2000,"成功"),

    CODE_5001(5001,"服务端错误"),
    CODE_4001(4001,"参数异常");

    public final Integer code;

    public final String msg;

    ErrorEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

}

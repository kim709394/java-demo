package com.kim.spring.security.common;

/**
 * kim
 * 2021/4/7
 */
public enum ErrorEnum {

    CODE_2000(2000,"成功"),

    CODE_5001(5001,"服务端错误"),
    CODE_3003(3003,"用户名或密码不正确"),
    CODE_3004(3004,"您未登录"),
    CODE_3005(3005,"验证码过期或不正确");

    public final Integer code;

    public final String msg;

    ErrorEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

}

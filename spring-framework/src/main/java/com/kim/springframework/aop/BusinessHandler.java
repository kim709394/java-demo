package com.kim.springframework.aop;

import org.springframework.stereotype.Service;

/**
 * @author huangjie
 * @description  业务逻辑处理类
 * @date 2022-01-15
 */
@Service
public class BusinessHandler {

    //正常业务逻辑处理
    public void handle(String name){
        System.out.println("业务逻辑处理完成"+name);
    }

    //抛出异常的业务逻辑
    public void throwExceptionHandle(String name){
        System.out.println("抛出异常业务逻辑执行"+name);
        int num = 1/0;
    }



}

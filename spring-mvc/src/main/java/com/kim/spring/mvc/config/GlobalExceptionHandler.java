package com.kim.spring.mvc.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * kim
 * 全局异常类
 * 2021/4/7
 */
//controller aop异常增强，当controlller出现异常则被该类捕捉，用于全局异常处理
@RestControllerAdvice(basePackages = "com.kim.spring.mvc.controller")
public class GlobalExceptionHandler {

    /**
     * 服务异常
     * */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected String handleGlobalException(Exception exception) {
        System.out.println("exception : " + exception.getMessage());

        return exception.getMessage();
    }



}

package com.kim.springframework.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangjie
 * @description
 * @date 2022/1/21
 */
@Service
public class BusinessHandler2 {
    @Autowired
    private BusinessHandler businessHandler;

    public BusinessHandler getBusinessHandler() {
        return businessHandler;
    }

    public void setBusinessHandler(BusinessHandler businessHandler) {
        this.businessHandler = businessHandler;
    }

    //抛出异常的业务逻辑
    public void throwExceptionHandle(String name){
        System.out.println("抛出异常业务逻辑执行"+name);
        int num = 1/0;
    }

}

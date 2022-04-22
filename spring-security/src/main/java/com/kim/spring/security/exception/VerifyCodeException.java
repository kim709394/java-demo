package com.kim.spring.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author huangjie
 * @description
 * @date 2022/4/22
 */
public class VerifyCodeException extends AuthenticationException {

    public VerifyCodeException(String msg){
        super(msg);

    }
}

package com.kim.truelicense.server.exception;

/**
 * @author huangjie
 * @description  证书认证异常
 * @date 2019/12/3
 */
public class LicenseException extends RuntimeException {

    public LicenseException(String message){
        super(message);
    }

}

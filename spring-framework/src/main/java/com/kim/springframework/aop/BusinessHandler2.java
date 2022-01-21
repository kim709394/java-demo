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

}

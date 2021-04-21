package com.kim.common.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author kim
 * @Since 2021/4/21
 * spring 线程
 */
@Service
public class SpringRun {

    @Async
    public void execute(Integer i){
        System.out.println(i);
    }



}

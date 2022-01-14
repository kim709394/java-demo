package com.kim.common.service.impl;

import com.kim.common.service.SpringRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author kim
 * @Since 2021/4/21
 * spring 线程
 */
@Service
public class SpringRunImpl implements SpringRun {


    @Autowired
    @Lazy
    private SpringRun springRun;

    @Async
    public void execute(Integer i){
        System.out.println(i);
    }

    @Override
    public void call(Integer i) {
        springRun.execute(i);
    }


}

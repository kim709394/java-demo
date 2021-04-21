package com.kim.common;

import com.kim.common.service.impl.SpringRun;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author kim
 * @Since 2021/4/21
 * spring线程池测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CommonApplication.class)
public class SpringThreadPoolTest {

    @Autowired
    private SpringRun SpringRun;

    /***
     * spring线程池
     * */
    @Test
    public void springThreadPool() throws InterruptedException {


        for(int i=0;i<100;i++){
            SpringRun.execute(i);
        }
        Thread.sleep(60*1000);
        System.out.println("ok");

    }


}

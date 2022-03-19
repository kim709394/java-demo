package com.kim.spring.boot.test;

import com.kim.spring.boot.SpringbootDemoApplication;
import com.kim.spring.boot.config.MyConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huangjie
 * @description
 * @date 2022-03-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class)
public class SpringbootDemoTest {

    @Autowired
    private MyConfiguration configuration;


    @Test
    public void configurationTest(){
        System.out.println(configuration);
    }


}

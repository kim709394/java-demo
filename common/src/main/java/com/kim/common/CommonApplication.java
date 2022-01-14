package com.kim.common;

import com.kim.common.annotation.EnableMyAnnotation;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author kim
 * @Since 2021/4/21
 * 通用工具类、api、demo
 */
@SpringBootApplication
@EnableMyAnnotation
@EnableAsync
public class CommonApplication implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

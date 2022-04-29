package com.kim.spring.security.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huangjie
 * @description
 * @date 2021/12/7
 */
@SpringBootConfiguration
public class InterceptorConfig implements WebMvcConfigurer {


    //默认跳转到登录页
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }
}

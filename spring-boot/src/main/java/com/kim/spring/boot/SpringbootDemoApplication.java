package com.kim.spring.boot;

import com.kim.spring.boot.config.MyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author huangjie
 * @description springboot启动类
 * @date 2022-03-19
 */
@SpringBootApplication
@EnableConfigurationProperties(value= MyConfiguration.class)
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        /**
         * springboot启动后在以下路径依次寻找主配置文件:  application.yml  或 application.properties
         * –file:./config/
         * –file:./ –
         * classpath:/config/
         * –classpath:/
         * */
        SpringApplication.run(SpringbootDemoApplication.class);
    }
}

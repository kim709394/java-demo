package com.kim.springboot.mybatisplus;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huangjie
 * @description   mybatis-plus整合sringboot的demo
 * @date 2021/11/23
 */
@SpringBootApplication
@EnableSwagger2Doc
@MapperScan(basePackages="com.kim.springboot.mybatisplus.mapper")
public class MpApplication {


    public static void main(String[] args) {
        SpringApplication.run(MpApplication.class);
    }
}

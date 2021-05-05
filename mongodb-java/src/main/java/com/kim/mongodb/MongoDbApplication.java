package com.kim.mongodb;

import com.spring4all.mongodb.EnableMongoPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
@SpringBootApplication
@EnableMongoPlus  //支持特殊配置
@EnableMongoRepositories(basePackages = "com.kim.mongodb.dao") //扫描数据访问层的dao
public class MongoDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDbApplication.class);
    }


}

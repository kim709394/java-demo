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

    /**
     * 特殊配置
     * private Integer minConnectionPerHost = 0;
     *     private Integer maxConnectionPerHost = 100;
     *     private Integer threadsAllowedToBlockForConnectionMultiplier = 5;
     *     private Integer serverSelectionTimeout = 30000;
     *     private Integer maxWaitTime = 120000;
     *     private Integer maxConnectionIdleTime = 0;
     *     private Integer maxConnectionLifeTime = 0;
     *     private Integer connectTimeout = 10000;
     *     private Integer socketTimeout = 0;
     *     private Boolean socketKeepAlive = false;
     *     private Boolean sslEnabled = false;
     *     private Boolean sslInvalidHostNameAllowed = false;
     *     private Boolean alwaysUseMBeans = false;
     *     private Integer heartbeatFrequency = 10000;
     *     private Integer minHeartbeatFrequency = 500;
     *     private Integer heartbeatConnectTimeout = 20000;
     *     private Integer heartbeatSocketTimeout = 20000;
     *     private Integer localThreshold = 15;
     * */
}

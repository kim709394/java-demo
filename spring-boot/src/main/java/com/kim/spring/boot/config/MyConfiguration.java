package com.kim.spring.boot.config;

import com.kim.spring.boot.pojo.User;
import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author huangjie
 * @description  ConfigurationProperties 快速注入自定义配置
 * @date 2022-03-19
 */
//@SpringBootConfiguration      ，启动类加了@EnableConfigurationProperties指定该配置类为bean，则不需要再添加此注解
@ConfigurationProperties(prefix = "myconfig")
@Data       //注意一定要有setter和getter方法
public class MyConfiguration {

    private String myName;
    private List<String> friends;
    private List<String> colors;
    private List<String> frameworks;
    private Map<String,String> cars;
    private Map<String,String> map;
    private User user;

}

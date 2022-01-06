package com.kim.springframework.ioc.annotation.config;

import com.kim.springframework.ioc.annotation.bean.Wheel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangjie
 * @description
 * @date 2021/12/9
 */
@Configuration
public class BeanConfig {

    /**
     * 手动实例化bean
     * */
    @Bean
    public Wheel wheel(){
        Wheel wheel=new Wheel();
        wheel.setId(1);
        wheel.setSize(13);
        return wheel;
    }

}

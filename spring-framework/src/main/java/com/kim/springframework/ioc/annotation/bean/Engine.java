package com.kim.springframework.ioc.annotation.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author huangjie
 * @description
 * @date 2021/12/9
 */
@Data
@ToString(callSuper = true)
@Component
public class Engine {

    private Integer id;
    private String name;
    private Integer power;

}

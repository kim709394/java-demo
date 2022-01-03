package com.kim.springframework.ioc.annotation.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author huangjie
 * @description
 * @date 2021/12/9
 */
@Data
@ToString(callSuper = true)
//实现InitializingBean接口重写afterPropertiesSet方法
public class Wheel implements InitializingBean {


    private Integer id;

    private Integer size;

    /**
     * 属性注入之后调用的方法
     * */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet method of Wheel has been called ");
        System.out.println(toString());
    }




}

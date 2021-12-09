package com.kim.springframework.ioc.annotation.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author huangjie
 * @description
 * @date 2021/12/9
 */
@Data
@ToString(callSuper = true)
public class Wheel {


    private Integer id;

    private Integer size;
}

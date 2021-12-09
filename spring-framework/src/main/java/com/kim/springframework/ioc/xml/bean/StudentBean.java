package com.kim.springframework.ioc.xml.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author huangjie
 * @description
 * @date 2021/12/8
 */
@Data
@ToString(callSuper = true)
public class StudentBean extends BaseBean  {



    public void initMethod(){
        System.out.println("student init");
    }


    public void destoryMethod(){
        System.out.println("student destory");
    }

}

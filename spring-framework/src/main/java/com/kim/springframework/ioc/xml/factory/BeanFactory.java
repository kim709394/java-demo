package com.kim.springframework.ioc.xml.factory;

import com.kim.springframework.ioc.xml.bean.WorkersBean;

/**
 * @author huangjie
 * @description
 * @date 2021/12/8
 */
public class BeanFactory {

    public WorkersBean buildWorkers(){
        WorkersBean worker=new WorkersBean();
        worker.setId(2);
        worker.setAge(18);
        worker.setName("职工");
        return worker;
    }

}

package com.kim.springframework.ioc.xml.factory;

import com.kim.springframework.ioc.xml.bean.TeacherBean;

/**
 * @author huangjie
 * @description   静态方法工厂bean
 * @date 2021/12/8
 */
public class StaticBeanFactory {

    public static TeacherBean buildTeacher(){
        TeacherBean teacher=new TeacherBean();
        teacher.setName("吴老师");
        teacher.setAge(30);
        teacher.setId(1);
        return teacher;
    }

}

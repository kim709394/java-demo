package com.kim.springframework.bean.factory;

import com.kim.springframework.bean.TeacherBean;

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

package com.kim.springframework.ioc.xml.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author huangjie
 * @description
 * @date 2021/12/9
 */
@Data
@ToString(callSuper = true)
public class SchoolBean extends BaseBean {

    //学生
    private List<StudentBean> studentBeans;

    //教师
    private TeacherBean teacherBean;

    //教职工
    private List<WorkersBean> workersBeans;

    //学校历史
    private Map<String,Object> history;

    //学校年级
    private Set<String> grades;

    //班主任信息
    private Properties leaderInfos;

    public SchoolBean(){

    }

    public SchoolBean(Integer id,String name,Integer age,TeacherBean teacherBean){
        super.id=id;
        super.name=name;
        super.age=age;
        this.teacherBean=teacherBean;
    }
}

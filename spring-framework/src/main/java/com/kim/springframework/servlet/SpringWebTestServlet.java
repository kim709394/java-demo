package com.kim.springframework.servlet;


import com.kim.springframework.SpringframeworkApplication;
import com.kim.springframework.ioc.annotation.bean.Car;
import com.kim.springframework.ioc.xml.bean.LifeCycleBean;
import com.kim.springframework.ioc.xml.bean.TeacherBean;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author huangjie
 * @description
 * @date 2021/12/9
 */
public class SpringWebTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherBean teacher = LifeCycleBean.getBean(TeacherBean.class);
        System.out.println(teacher);
        System.out.println(teacher.getName());
        Car car=LifeCycleBean.getBean(Car.class);
        System.out.println(car);
       /* ApplicationContext applicationContext = SpringframeworkApplication.getApplicationContext();
        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);*/
        try (PrintWriter writer = resp.getWriter()){
            writer.write("ok");
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

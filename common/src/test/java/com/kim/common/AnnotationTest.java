package com.kim.common;

import com.kim.common.annotation.MyAnnotation;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Author kim
 * 注解测试
 * @Since 2021/6/9
 */

@MyAnnotation(num=0,name="myAnnotation in class")
public class AnnotationTest {

    @MyAnnotation(num=1,name="myAnnotation in method")
    public void myAnnotation(){

    }


    @Test
    public void myAnnotationTest(){
        getAnnotationMsg(AnnotationTest.class);
    }

    /**
     * 通过反射获取注解信息
     * */
    private static void getAnnotationMsg(Class<?> clazz){
        //获取类中的注解
        MyAnnotation myAnnotationInClass=clazz.getAnnotation(MyAnnotation.class);
        if(null!=myAnnotationInClass){
            System.out.println("类中的注解是："+myAnnotationInClass.num()+","+myAnnotationInClass.name());
        }
        //获取方法中的注解,这里忽略父类的方法
        for (Method method:clazz.getDeclaredMethods()
        ) {
            MyAnnotation myAnnotationInMethod=method.getAnnotation(MyAnnotation.class);
            if(null!=myAnnotationInMethod){
                System.out.println("方法中的注解是："+myAnnotationInMethod.num()+","+myAnnotationInMethod.name());
            }
        }
    }
}

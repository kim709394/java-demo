package com.kim.common;

import com.kim.common.service.GenericityClass;
import com.kim.common.service.GenericityInterface;
import com.kim.common.service.impl.Genericity;
import com.kim.common.service.impl.GenericityClassImpl;
import com.kim.common.service.impl.ReflectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * @Author kim
 * @Since 2021/4/21
 * 反射测试
 */
public class ReflectTest {


    @Test
    @DisplayName("获取Class对象")
    public void getClassObj() throws ClassNotFoundException {
        //知道具体某个类的情况下：
        Class<ReflectServiceImpl> reflectServiceClass = ReflectServiceImpl.class;
        //Class.forName()方法
        Class<?> forName = Class.forName("com.kim.common.service.impl.ReflectServiceImpl");
        //通过对象实例获取
        ReflectServiceImpl reflectService=new ReflectServiceImpl();
        Class<? extends ReflectServiceImpl> serviceClass = reflectService.getClass();
        //通过类加载器获取
        Class<?> loadClass = ClassLoader.getSystemClassLoader().loadClass("com.kim.common.service.impl.ReflectServiceImpl");

    }

    @Test
    @DisplayName("获取类的属性和方法、注解等信息")
    public void testGetClassInfo() throws Exception {

        getClassInfo(ReflectServiceImpl.class);

    }

    public void getClassInfo(Class<? extends ReflectServiceImpl> clazz) throws Exception {

        //获取private构造方法

        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, Integer.class);
        //调用private构造方法
        constructor.setAccessible(true);    //不进行安全检查以至于能调用private方法
        ReflectServiceImpl mike = (ReflectServiceImpl)constructor.newInstance("mike", 18);
        System.out.println(mike.getName()+","+mike.getAge());
        //通过无参构造实例化
        ReflectServiceImpl obj = clazz.getConstructor().newInstance();
        System.out.println(obj);
        //获取类上面的注解
        Service service = clazz.getDeclaredAnnotation(Service.class);
        System.out.println(service);
        //获取所有方法,declare前缀的是获取本类的方法，无前缀的是获取本类及父类或父接口的所有方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method:declaredMethods
             ) {
            if (method.getName().equals("addAge")) {
                //获取返回值类型
                String returnType = method.getReturnType().getName();
                System.out.println(returnType);
                //获取方法上面的注解，包括返回值上的注解
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                for (Annotation annotation:declaredAnnotations
                     ) {
                    System.out.println(annotation);
                }
                //调用方法
                Integer age = (Integer) method.invoke(mike, 2);
                System.out.println(age);
            } else if (method.getName().equals("subtractAge")) {
                //获取方法上会抛出的异常
                Class<?>[] exceptionTypes = method.getExceptionTypes();
                for (Class<?> exception:exceptionTypes
                     ) {
                    System.out.println(exception);
                }
                //取消安全检查以调用私有方法
                method.setAccessible(true);
                Integer age2 =(Integer) method.invoke(mike, 3);
                System.out.println(age2);
            }
        }
        //获取除Object父类以外的所有父类的所有属性
        Class<?> superClass=clazz;
        while(!(superClass=superClass.getSuperclass()).isAssignableFrom(Object.class)){
            Field[] fields = getFileds(superClass);
            for (Field field:fields
                 ) {
                System.out.println(field.getName());
            }
        }
        //获取本类的所有属性
        Field[] fields = getFileds(clazz);
        for (Field field:fields
        ) {
            System.out.println(field.getName());
        }
        //修改私有属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(mike,"lucy");
        System.out.println(mike.getName());


    }

    //获取类的所有属性
    private Field[] getFileds(Class<?> clazz){
        return clazz.getDeclaredFields();

    }


    /**
     * 扫描某个包路径获取这个包路径及递归扫描所有子包路径下的所有类的信息(注解和方法名)
     * */
    @Test
    public void testGetClassInfoRecursion() throws IOException, ClassNotFoundException {
        String packagePath="com.kim.common.controller";

        getClassInfoRecursion(packagePath);

    }
    public void getClassInfoRecursion(String packagePath) throws IOException, ClassNotFoundException {
        String package2=packagePath.replace(".","/");
        //获取包名下的类文件
        Enumeration<URL> resources = this.getClass().getClassLoader().getResources(package2);
        while(resources.hasMoreElements()){
            URL url = resources.nextElement();
            String protocol=url.getProtocol();
            String filePath= URLDecoder.decode(url.getFile(),"UTF-8");
            if("file".equalsIgnoreCase(protocol)){

                File examples=new File(filePath);
                System.out.println(examples.getName());
                File[] files = examples.listFiles();
                for (File file:files
                ) {
                    if(file.isFile()){
                        String clazz=packagePath+"."+file.getName().replace(".class","");
                        Class<?> clazz2=Thread.currentThread().getContextClassLoader().loadClass(clazz);
                        clazzHanlde(clazz2);
                    }else if(file.isDirectory()){
                        //递归获取目录下的类文件
                        getClassInfoRecursion(packagePath+"."+file.getName());
                    }
                }
            }
        }
    }

    private void clazzHanlde(Class<?> clazz){

        Api api = clazz.getAnnotation(Api.class);
        System.out.println("接口分类说明："+api.description());
        String parentUtl=clazz.getAnnotation(RequestMapping.class).name().toString();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods
        ) {
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            System.out.println("接口说明："+apiOperation.value());
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            System.out.println("url："+parentUtl+getMapping.value().toString());
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter:parameters
            ) {
                System.out.println("参数:"+parameter.getType());
            }
            System.out.println("返回值:"+method.getReturnType());

        }
    }


    /**
     * 通过反射获取类实现接口的泛型
     * */
    @Test
    public void testGenericity() throws Exception {
        GenericityInterface genericity=new Genericity();

        Type genericInterface = genericity.getClass().getGenericInterfaces()[0];
        ParameterizedType parameterizedType=(ParameterizedType)genericInterface;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        //获取第一个泛型
        Type gen1=actualTypeArguments[0];
        String typeName = gen1.getTypeName();
        System.out.println(typeName);
        //Class<?> clazz=Class.forName(typeName);

        //获取第二个泛型
        Type gen2=actualTypeArguments[1];
        System.out.println(gen2.getTypeName());

    }

    /**
     * 通过反射获取类继承的父类的泛型
     * */
    @Test
    public void testGenericityClass(){
        GenericityClass genericityClass=new GenericityClassImpl();
        Type genericSuperclass = genericityClass.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType=(ParameterizedType)genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        //获取第一个泛型
        Type gen1=actualTypeArguments[0];
        String typeName = gen1.getTypeName();
        System.out.println(typeName);
        //Class<?> clazz=Class.forName(typeName);

        //获取第二个泛型
        Type gen2=actualTypeArguments[1];
        System.out.println(gen2.getTypeName());


    }

    /**
     * 获取类实现的接口
     * */
    @Test
    public void getInterfaces(){
        Genericity genericity = new Genericity();
        //获取实现的接口集合，不带泛型
        Class<?>[] interfaces =genericity.getClass().getInterfaces();
        //获取实现的接口集合，带泛型
        Type[] genericInterfaces = genericity.getClass().getGenericInterfaces();
        for (Class<?> c:interfaces
             ) {
            System.out.println(c);
        }
        System.out.println("---------------------------");
        for (Type t:genericInterfaces
             ) {
            System.out.println(t);
        }

    }






}

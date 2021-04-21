package com.kim.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.common.service.GenericityInterface;
import com.kim.common.service.impl.Genericity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * @Author kim
 * @Since 2021/4/21
 * 反射测试
 */
public class ReflectTest {

    /**
     * 根据类的包名获取类的信息(注解和方法名)
     * */
    @Test
    public void getClassInfo() throws IOException, ClassNotFoundException {
        String package1="com.kim.common.controller";
        String package2=package1.replace(".","/");
        //获取包名下的类文件
        Enumeration<URL> resources = this.getClass().getClassLoader().getResources(package2);
        while(resources.hasMoreElements()){
            URL url = resources.nextElement();
            String protocol=url.getProtocol();
            if("file".equalsIgnoreCase(protocol)){
                String filePath= URLDecoder.decode(url.getFile(),"UTF-8");
                File examples=new File(filePath);
                System.out.println(examples.getName());
                File[] clazzes = examples.listFiles();
                for (File clazz:clazzes
                ) {
                    //
                    String clazz1=package1+"."+clazz.getName().replace(".class","");
                    Class<?> clazz2=Thread.currentThread().getContextClassLoader().loadClass(clazz1);
                    clazzHanlde(clazz2);
                }
            }
        }
    }

    private void clazzHanlde(Class<?> clazz){

        Api api = clazz.getAnnotation(Api.class);
        System.out.println("接口分类说明："+api.description());
        String parentUtl=clazz.getAnnotation(RequestMapping.class).name().toString();
        Method[] methods = clazz.getMethods();
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
     * 通过反射获取类的泛型
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







}

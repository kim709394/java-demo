package com.kim.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.common.entity.*;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

/**
 * @Author kim
 * 对象拷贝测试
 * @Since 2021/6/3
 */
public class CopyObjectTest {

    private ObjectMapper mapper=new ObjectMapper();

    /**
     * 浅拷贝
     * */
    @Test
    public void shallowCopy() throws Exception {
        PersonA personA=new PersonA();
        personA.setName("mike");
        personA.setAge(18);
        CarA carA=new CarA();
        carA.setSize(300);
        carA.setBrand("宝马");
        personA.setCar(carA);
        PersonA personB = personA.clone();
        System.out.println("两对象内存地址是否相等:"+(personA==personB));
        System.out.println("原对象值:"+personA);
        System.out.println("新对象值:"+personB);
        System.out.println("两对象的引用类型成员变量内存地址是否相等:"+(personA.getCar()==personB.getCar()));
        //修改引用类型成员变量的值再对两个对象进行比较
        carA.setBrand("奔驰");
        carA.setSize(200);
        System.out.println("--------------------------改变成员变量值后-----------------------------");
        System.out.println("两对象内存地址是否相等:"+(personA==personB));
        System.out.println("原对象内存地址:"+personA);
        System.out.println("新对象内存地址:"+personB);
        System.out.println("两对象的引用类型成员变量内存地址是否相等:"+(personA.getCar()==personB.getCar()));

    }

    /**
     * 通过clone()方法实现深拷贝
     * */
    @Test
    public void deepCopy() throws Exception{
        PersonB personB=new PersonB();
        personB.setName("helen");
        personB.setAge(18);
        CarB carB=new CarB();
        carB.setSize(500);
        carB.setBrand("凯迪拉克");
        personB.setCar(carB);
        PersonB personC=personB.clone();
        System.out.println("两对象内存地址是否相等:"+(personB==personC));
        System.out.println("原对象值:"+personB);
        System.out.println("新对象值:"+personC);
        System.out.println("两对象的引用类型成员变量内存地址是否相等:"+(personB.getCar()==personC.getCar()));
        //修改引用类型成员变量的值再对两个对象进行比较
        carB.setBrand("劳斯莱斯");
        carB.setSize(800);
        System.out.println("--------------------------改变成员变量值后-----------------------------");
        System.out.println("两对象内存地址是否相等:"+(personB==personC));
        System.out.println("原对象内存地址:"+personB);
        System.out.println("新对象内存地址:"+personC);
        System.out.println("两对象的引用类型成员变量内存地址是否相等:"+(personB.getCar()==personC.getCar()));

    }


    /**
     * 对象-流方式序列化进行深拷贝
     * */
    @Test
    public void serializableCopy(){
        PersonC personC=new PersonC();
        personC.setName("ann");
        personC.setAge(25);
        CarC carC=new CarC();
        carC.setSize(900);
        carC.setBrand("特斯拉");
        personC.setCar(carC);
        PersonC personD = SerializationUtils.clone(personC);
        System.out.println("两对象内存地址是否相等:"+(personC==personD));
        System.out.println("原对象值:"+personC);
        System.out.println("新对象值:"+personD);
        System.out.println("两对象的引用类型成员变量内存地址是否相等:"+(personC.getCar()==personD.getCar()));
        //修改引用类型成员变量的值再对两个对象进行比较
        carC.setBrand("蔚来");
        carC.setSize(1000);
        System.out.println("--------------------------改变成员变量值后-----------------------------");
        System.out.println("两对象内存地址是否相等:"+(personC==personD));
        System.out.println("原对象内存地址:"+personC);
        System.out.println("新对象内存地址:"+personD);
        System.out.println("两对象的引用类型成员变量内存地址是否相等:"+(personC.getCar()==personD.getCar()));

    }

    /**
     * jackson方式序列化进行深拷贝
     * */
    @Test
    public void jacksonCopy() throws Exception{
        //对实体类没有要求要实现Serializable接口，只要有set/get方法即可
        PersonC personC=new PersonC();
        personC.setName("ann");
        personC.setAge(25);
        CarC carC=new CarC();
        carC.setSize(900);
        carC.setBrand("特斯拉");
        personC.setCar(carC);
        String json = mapper.writeValueAsString(personC);
        PersonC personD = mapper.readValue(json, PersonC.class);
        System.out.println("两对象内存地址是否相等:"+(personC==personD));
        System.out.println("原对象值:"+personC);
        System.out.println("新对象值:"+personD);
        System.out.println("两对象的引用类型成员变量内存地址是否相等:"+(personC.getCar()==personD.getCar()));
        //修改引用类型成员变量的值再对两个对象进行比较
        carC.setBrand("蔚来");
        carC.setSize(1000);
        System.out.println("--------------------------改变成员变量值后-----------------------------");
        System.out.println("两对象内存地址是否相等:"+(personC==personD));
        System.out.println("原对象内存地址:"+personC);
        System.out.println("新对象内存地址:"+personD);
        System.out.println("两对象的引用类型成员变量内存地址是否相等:"+(personC.getCar()==personD.getCar()));


    }

}

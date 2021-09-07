package com.kim.common;

import com.kim.common.entity.A;
import com.kim.common.entity.B;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @Author kim
 * idea generateO2O插件例子
 * @Since 2021/5/19
 */
public class GenerateO2OTest {

    //利用generateO2O插件生成属性拷贝方法
    public static B a2b(A a) {
        if (a == null) {
            return null;
        }
        B b = new B();
        b.setName(a.getName());
        b.setId(a.getId());
        b.setColors(a.getColors());
        return b;
    }

    @Test
    public void copyTest(){
        A a=new A();
        a.setId(1);
        a.setName("mike");
        a.setColors(Arrays.asList("blue","yellow","red"));
        B b = a2b(a);
        System.out.println(b.getId());
        System.out.println(b.getName());
        b.getColors().stream().forEach(color -> System.out.println(color));
    }

    /**
     * 对于有父类的实体属性拷贝方法生成，无法将父类的属性拷贝方法生成出来，解决方法:
     * 1. 手动编写父类的属性set/get拷贝方法，这种方法适用于父类属性比较少的情况。
     * 2. 在父类生成一个copy方法，然后把生成的set/get拷贝方法代码赋值到实体属性拷贝方法里面再进行修改，这种方法适用于父类属性比较多的情况。
     * */
    public static B a2bWithC(A a) {
        if (a == null) {
            return null;
        }
        B b = new B();
        b.setName(a.getName());
        b.setId(a.getId());
        b.setColors(a.getColors());
        b.setAge(a.getAge());
        return b;
    }

    @Test
    public void a2bWithCTest(){
        A a=new A();
        a.setId(1);
        a.setName("mike");
        a.setColors(Arrays.asList("blue","yellow","red"));
        a.setAge(18);
        B b = a2bWithC(a);
        System.out.println(b.getId());
        System.out.println(b.getName());
        System.out.println(b.getAge());
        b.getColors().stream().forEach(color -> System.out.println(color));
    }

}

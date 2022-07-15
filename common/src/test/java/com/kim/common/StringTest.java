package com.kim.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author huangjie
 * @description  字符串处理
 * @date 2021/11/29
 */
public class StringTest {


    @Test
    @DisplayName("字符串分隔符")
    public void split(){
        String str="刘德华　刘德华　\\n张学友　\\n张学友　";
        String[] arr = str.split("\\\\n");
        System.out.println(arr);
    }

    @Test
    @DisplayName("字符串常量池")
    public void testFinal(){

        final String str1 = "str";
        //运行时才能知道值，所以编译时不会初始化进字符串常量池
        final String str2 = getStr();
        String c = "str" + "ing";// 常量池中的对象
        String d = str1 + str2; // 在堆上创建的新的对象
        System.out.println(c == d);// false

    }
    public static String getStr() {
        return "ing";
    }

    @Test
    @DisplayName("字符串常量池2")
    public void testFinal2(){

        String a="ab";
        String b="ab";
        String c=new String("ab");

        System.out.println(a==b);//true
        System.out.println(a==c);//false

        String d=new String("cd");
        String e="cd";
        String f="cd";
        System.out.println(d==e);
        System.out.println(e==f);

    }



}

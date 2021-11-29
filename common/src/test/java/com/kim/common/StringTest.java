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

}

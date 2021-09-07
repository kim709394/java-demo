package com.kim.common;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author kim
 * @Since 2021/4/21
 * 集合框架测试
 */
public class CollectionTest {


    /**
     * 测试LinkedHashMap有序
     * */
    @Test
    public void testLinkedHashMap(){

        Map<String,Object> linkedHashmap=new LinkedHashMap<>();
        for(int i=0;i<100;i++){
            linkedHashmap.put(i+"",i);
        }

        for (Map.Entry<String,Object> entry:linkedHashmap.entrySet()
        ) {
            System.out.println(entry.getKey());
        }
        System.out.println("hashMap");
        /**********************hashMap***************************/
        Map<String,Object> hashMap=new HashMap<>();
        for(int i=0;i<100;i++){
            hashMap.put(i+"",i);
        }
        for (Map.Entry<String,Object> entry:hashMap.entrySet()
        ) {
            System.out.println(entry.getKey().hashCode());
            System.out.println(entry.getKey());
        }

    }

    /**
     * 合并数组
     * */
    @Test
    public void mergeArr(){

        String[] arr1=new String[]{"1","2"};
        String[] arr2=new String[]{"3"};
        String[] arr3 = ArrayUtils.addAll(arr1, arr2);
        Arrays.stream(arr3).forEach(System.out::println);
    }


}

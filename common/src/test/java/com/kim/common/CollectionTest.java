package com.kim.common;

import org.junit.Test;

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


}

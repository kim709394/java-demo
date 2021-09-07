package com.kim.common;

import com.kim.common.entity.HashCode;
import org.junit.jupiter.api.Test;

/**
 * @Author kim
 * @Since 2021/4/21
 * 哈希值比较测试
 */
public class HashCodeTest {


    @Test
    public void testHashCode(){
        String str="abcd";
       /* System.out.println(str.hashCode());
        System.out.println(System.identityHashCode(str));
        System.out.println(System.identityHashCode(str));*/
        HashCode h1=new HashCode();
        h1.setName("name1");
        System.out.println(h1.hashCode());
        System.out.println(System.identityHashCode(h1));
        h1.setName("name2");
        System.out.println(h1.hashCode());
        System.out.println(System.identityHashCode(h1));
    }

}

package com.kim.common;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;

/**
 * @Author kim
 * @Since 2021/5/25
 */
public class IpTest {


    /**
     * 验证ip是否能ping通
     * */
    @Test
    public void testIpReachabled() throws Exception{
        String[] split = "1111 222 ".split("\\n");
        System.out.println(split);
        System.out.println("O".equals("0"));
        InetAddress baidu = InetAddress.getByName("www.baidu.com");
        System.out.println(baidu.isReachable(3000));
    }

}

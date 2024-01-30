package com.kim.common;

import com.kim.common.util.CRC16Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Kim Huang
 * @description
 * @date 2024-01-30
 */
public class CRCTest {




    @Test
    @DisplayName("测试crc16校验")
    public void testCrc16(){
        //十进制byte数组
        byte[] b = {0,3,15,0,0,0};
        byte[] crc16 = CRC16Util.appendCrc16(b);
        //十六进制字符串数组
        String[] b16 = {"00","03","0F","00","00","00"};
        byte[] crc16From16 = CRC16Util.appendCrc16(b16);
        //后两位为crc16校验码，低前高后
        System.out.println(crc16);
        System.out.println(crc16From16);


    }
}

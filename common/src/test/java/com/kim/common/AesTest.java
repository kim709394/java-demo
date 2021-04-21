package com.kim.common;

import com.kim.common.util.AesUtil;
import org.junit.Test;

/**
 * @Author kim
 * @Since 2021/4/21
 * Aes算法对称加密测试
 */
public class AesTest {

    @Test
    public void aes(){
        String encryptContent= AesUtil.encrypt("123456",AesUtil.PUBLIC_KEY);
        System.out.println(encryptContent);
        String decryptContent=AesUtil.decrypt(encryptContent,AesUtil.PUBLIC_KEY);
        System.out.println(decryptContent);
    }


}

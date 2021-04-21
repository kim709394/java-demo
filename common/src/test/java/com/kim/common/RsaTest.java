package com.kim.common;

import com.kim.common.util.RsaUtil;
import org.junit.Test;

import java.util.Map;

/**
 * @Author kim
 * @Since 2021/4/21
 * Rsa算法非对称加解密
 */
public class RsaTest {

    @Test
    public void rsa() throws Exception {
        String password = "123456";
        Map<String, Object> keys = RsaUtil.genKeyPair();
        String publicKey = RsaUtil.getPublicKey(keys);
        System.out.println("公钥:" + publicKey);
        String privateKey = RsaUtil.getPrivateKey(keys);
        System.out.println("私钥:" + privateKey);
        String encrypt = RsaUtil.encryptedDataOnJava(password, publicKey);
        System.out.println("公钥加密后的密文:" + encrypt);
        String decrypt = RsaUtil.decryptDataOnJava(encrypt, privateKey);
        System.out.println("私钥解密后的明文:" + decrypt);
    }


    @Test
    public void rsaDecrypt() throws Exception {

        String encrypt = "UVKJk9cmwOUZUMXMjizyuPAUFcehf3asCFBaED6yGq+D/16/Mp357ftH0+Ys4Miq9nMoXuXtghtDTSpQxDRxOVttc4jsfeIN2q58Hik7wNH6zPQFyZt2vTQM7eUmXM/Md0Z59oavzdgyGxJ3cd7tYWOVqb03CsZz4TPaXZ2VB1E=";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKiGYLGKrZ+mJsPGTtmkyH0ydBjEUPDJnIpKk1OsCOITPYNBOKt7+4SVK23bh879ts6sVBYMZ7oEowqX8ygREEuRuczleaXLs8fvgD7ZJSXjQnVig408qTxhDJQ2tFP2mzQWxcUbCWDFerxz4m86ngRUVYuWq9iEJj8gXzKc6oARAgMBAAECgYEAp+Zpw7kUznBtQrP6ryBbl1CDFoHQ2nUjfyxZqA1INGVS7cg3O7Kkni04PZVwsytXzIUoqWbmmRAxmEZXPd3ySpcdN3re7KgxgBzO3f7t5ljbvMUlb2FRI68Tz2amkxVhXIGryLrMO10VLNx0Jlxc1M24jr/x35CgI/xH6fkqJAECQQDqR75hOLGWn3cd/+VbbORMVEjvPlFZr8fi4hG7YQtxZbd+EAGwBzRfmc7qnQHWjn3U3Oxc8E/K1NPUZggl5VfxAkEAuCYKD/Scgw/gUNe/TitpK5aeDccWHINT0xFGJdR87Mr2AnNuT/moL5UP4Bp//yLtrHPILfHEe08YfUXeuFbKIQJBANuCXym+sf476aLtKhwwefPeoPuwuw46eiHlydF0iwEz2eo+2yXwiizs8we2FovOtK2mVbx62XhlUxjWhNnk+jECQB+vw/l84NWYjMO8bBm6VI2AjE/YmxBq9KiUB1sJFjn0rqI7VJcEYZFWd/Xa+X1geD89aQum12VUdeVQeYOEw6ECQETN8WItfHmYXxn3zFTauN0l4HGdPLE/ZV1UFgkyv2DAb6uKHe0O3PDYFiSKt11w34fvvqIzqeTXhrJ6pw9BdB0=";
        String password = RsaUtil.decryptDataOnJava(encrypt, privateKey);
        System.out.println("私钥解密后的明文:" + password);
    }


}

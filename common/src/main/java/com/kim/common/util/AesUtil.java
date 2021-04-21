package com.kim.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author huangjie
 * @description
 * @date 2019/12/12
 */
public class AesUtil {

    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    public static final String PUBLIC_KEY="reader1234567890";

    public static String encrypt(String content, String key) {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec skey = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            byte [] byte_content = content.getBytes("utf-8");
            byte [] encode_content = cipher.doFinal(byte_content);
            return Base64.encodeBase64String(encode_content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptStr, String decryptKey) {
        try {
            byte[] raw = decryptKey.getBytes();
            SecretKeySpec skey = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.DECRYPT_MODE, skey);
            byte [] encode_content = Base64.decodeBase64(encryptStr);
            byte [] byte_content = cipher.doFinal(encode_content);
            return new String(byte_content,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}

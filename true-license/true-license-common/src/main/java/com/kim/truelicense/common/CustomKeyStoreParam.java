package com.kim.truelicense.common;

import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangjie
 * @description  密钥库参数对象
 * @date 2019/11/28
 */
public class CustomKeyStoreParam extends AbstractKeyStoreParam {


    /**密钥路径，可为磁盘路径，也可为项目资源文件里的路径,如果为磁盘路径需重写getStream()方法*/
    private String storePath;

    /**公钥或私钥的别名*/
    private String alias;

    /**访问公钥/私钥库的密码*/
    private String storePass;

    /**公钥/私钥的密码*/
    private String keyPass;



    public CustomKeyStoreParam(Class clazz, String resource,String alias,String storePass,String keyPass) {
        super(clazz, resource);
        this.storePath=resource;
        this.alias=alias;
        this.storePass=storePass;
        this.keyPass=keyPass;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePass;
    }

    @Override
    public String getKeyPwd() {
        return keyPass;
    }

    @Override
    public InputStream getStream() throws IOException {
        return new FileInputStream(storePath);
    }
}

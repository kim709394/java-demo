package com.kim.mini.tomcat.net;

import com.kim.mini.tomcat.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangjie
 * @description
 * @date 2022-12-01
 */
public class Request {


    private String url;
    private String method;
    private InputStream inputStream;


    public Request(){

    }

    public Request(InputStream inputStream){

        this.inputStream = inputStream;
        String requestStr=new String(IOUtils.toByteArray(inputStream));
        //读取第一行请求头信息
        String firstLineRequest=requestStr.split("\\n")[0];
        String[] strs=firstLineRequest.split(" ");
        this.method = strs[0];
        this.url = strs[1];
        System.out.println("method == "+this.method);
        System.out.println("url == "+this.url);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}

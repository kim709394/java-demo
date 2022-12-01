package com.kim.mini.tomcat.net;

import com.kim.mini.tomcat.util.IOUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author huangjie
 * @description
 * @date 2022-12-01
 */
public class Response {

    private OutputStream output;

    public Response(){

    }

    public Response(OutputStream output){
        this.output = output;
    }


    public void responseContent(String content) throws IOException {
        this.output.write(content.getBytes());
        this.output.flush();
    }

    public void responseStaticResources(String relativePath){
        IOUtils.writeStaticResources(relativePath,this.output);
    }



}

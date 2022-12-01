package com.kim.mini.tomcat.servlet;

import com.kim.mini.tomcat.net.Request;
import com.kim.mini.tomcat.net.Response;

import java.io.IOException;

/**
 * @author huangjie
 * @description
 * @date 2022-12-01
 */
public abstract class HttpServlet implements Servlet{


    public abstract void doGet(Request request, Response response) throws IOException;
    public abstract void doPost(Request request,Response response) throws IOException;

    @Override
    public void service(Request request, Response response) throws Exception {
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }
}

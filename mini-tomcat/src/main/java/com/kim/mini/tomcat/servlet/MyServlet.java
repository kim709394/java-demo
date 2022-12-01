package com.kim.mini.tomcat.servlet;

import com.kim.mini.tomcat.net.Request;
import com.kim.mini.tomcat.net.Response;
import com.kim.mini.tomcat.util.HttpPtotocolUtil;

import java.io.IOException;

/**
 * @author huangjie
 * @description
 * @date 2022-12-01
 */
public class MyServlet extends HttpServlet {

    @Override
    public void doGet(Request request, Response response) throws IOException {
        String content = "<h1>hello GET Request</h1>";
        response.responseContent(HttpPtotocolUtil.response200(content));

    }

    @Override
    public void doPost(Request request, Response response) throws IOException {
        String content = "<h1>hello POST Request</h1>";
        response.responseContent(HttpPtotocolUtil.response200(content));
    }

    @Override
    public void init() throws Exception {
        System.out.println("MyServlet ================ init");
    }

    @Override
    public void destory() throws Exception {
        System.out.println("MyServlet ================ destory");
    }
}

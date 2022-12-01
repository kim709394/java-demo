package com.kim.mini.tomcat.servlet;

import com.kim.mini.tomcat.net.Request;
import com.kim.mini.tomcat.net.Response;

/**
 * @author huangjie
 * @description
 * @date 2022-11-30
 */
public interface Servlet {


    void init() throws Exception;

    void destory() throws Exception;

    void service(Request request, Response response) throws Exception;
}

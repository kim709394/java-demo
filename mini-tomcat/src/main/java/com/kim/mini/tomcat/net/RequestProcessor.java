package com.kim.mini.tomcat.net;

import com.kim.mini.tomcat.config.ServletContext;
import com.kim.mini.tomcat.servlet.HttpServlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author huangjie
 * @description
 * @date 2022-12-01
 */
public class RequestProcessor implements Runnable {

    private Socket socket;

    public RequestProcessor(Socket socket){
        this.socket = socket;

    }
    @Override
    public void run() {

        try{
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Request request = new Request(inputStream);
            Response response=new Response(outputStream);
            ServletContext servletContext = ServletContext.getInstance();
            HttpServlet httpServlet = null;
            if((httpServlet = servletContext.get(request.getUrl())) == null){
                response.responseStaticResources(request.getUrl());
            }else{
                httpServlet.service(request,response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

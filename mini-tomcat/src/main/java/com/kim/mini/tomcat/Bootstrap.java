package com.kim.mini.tomcat;

import com.kim.mini.tomcat.config.ServletContext;
import com.kim.mini.tomcat.net.Request;
import com.kim.mini.tomcat.net.RequestProcessor;
import com.kim.mini.tomcat.net.Response;
import com.kim.mini.tomcat.servlet.HttpServlet;
import com.kim.mini.tomcat.util.HttpPtotocolUtil;
import com.kim.mini.tomcat.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author huangjie
 * @description
 * @date 2022-11-30
 */
public class Bootstrap {

    private static Boolean flag=false;

    public static void start() throws IOException {
        ServerSocket serverSocket=new ServerSocket(8080);
        ThreadPoolExecutor executor=new ThreadPoolExecutor(8,16,100L, TimeUnit.SECONDS,new ArrayBlockingQueue<>(50),new ThreadPoolExecutor.AbortPolicy());

            if(!flag){
                System.out.println("mini tomcat have started on 8080 .......");
                flag = true;
            }
            while (true){
                Socket socket = serverSocket.accept();
                executor.execute(new RequestProcessor(socket));
            }


    }

    public static void main(String[] args) throws IOException {

        start();

    }


}

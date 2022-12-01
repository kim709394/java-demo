package com.kim.mini.tomcat.util;

/**
 * @author huangjie
 * @description
 * @date 2022-11-30
 */
public class HttpPtotocolUtil {

    /**
     * 响应信息
     * @param statusCode
     * @param message
     * @param contentType
     * @param content
     * @return
     */
    public static String response(Integer statusCode,String message,String contentType,String content){

        return "HTTP/1.1 "+
                statusCode+" "+message
                +" \n"+
                "Content-Type: "+contentType +" \n" +
                "Content-Length: "+content.getBytes().length + " \n\r\n" +
                content;
    }

    /**
     *
     * @param statusCode
     * @param message
     * @param contentType
     * @param contentLength
     * @return
     */
    public static String responseHeader(Integer statusCode,String message,String contentType,Long contentLength){

        return "HTTP/1.1 "+
                statusCode+" "+message
                +" \n"+
                "Content-Type: "+contentType +" \n" +
                "Content-Length: "+contentLength + " \n\r\n";
    }

    /**
     * 响应200
     * @param content
     * @return
     */
    public static String response200(String content){
        return response(200,"OK","text/html",content);
    }

    /**
     *
     * @param contentLength
     * @return
     */
    public static String response200Header(Long contentLength){
        return responseHeader(200,"OK","text/html",contentLength);
    }

    /**
     * 响应404
     * @return
     */
    public static String response404(){
        String text404="<h1>404 not found</h1>";
        return response(404,text404,"text/html",text404);
    }




}

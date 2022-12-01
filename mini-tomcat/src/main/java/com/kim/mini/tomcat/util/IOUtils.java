package com.kim.mini.tomcat.util;

import java.io.*;

/**
 * @author huangjie
 * @description
 * @date 2022-12-01
 */
public class IOUtils {


    /**
     * 输入流转化为内存byte数组
     * @param input
     * @return
     */
    public static byte[] toByteArray(InputStream input){


        //读取请求流
        try{
            int count = 0;
            while (count == 0){
                count = input.available();
            }
            byte[] b= new byte[count];
            input.read(b);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getClassAbsolutePath(){
        return IOUtils.class.getResource("/").getPath();
    }

    /**
     *
     * @param relativePath
     * @param output
     */
    public static void writeStaticResources(String relativePath, OutputStream output){
        if(relativePath.equals("/")||relativePath.equals("")){
            relativePath = "/index.jsp";
        }
        String classAbsolutePath = getClassAbsolutePath();
        File file=new File(classAbsolutePath+relativePath);
        if(!file.exists()){
            try {
                output.write(HttpPtotocolUtil.response404().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        byte[] tmp=new byte[1024];
        int lenTmp=0;
        //读取请求流
        try(
            InputStream staticResource = new FileInputStream(file);
        ){
            int count = 0;
            while(count == 0){
                count = staticResource.available();
            }
            output.write(HttpPtotocolUtil.response200Header(Long.valueOf(count+"")).getBytes());
            output.flush();
            while((lenTmp = staticResource.read(tmp))>0){
                output.write(tmp,0,lenTmp);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

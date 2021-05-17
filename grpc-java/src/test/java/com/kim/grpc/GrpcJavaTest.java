package com.kim.grpc;

import com.kim.grpc.client.BidirectionalStreamClient;
import com.kim.grpc.client.CommonClient;
import com.kim.grpc.client.CustomStreamClient;
import com.kim.grpc.client.ServerStreamClient;
import com.kim.grpc.server.BidirectionalStreamServer;
import com.kim.grpc.server.CommonServer;
import com.kim.grpc.server.CustomStreamServer;
import com.kim.grpc.server.ServerStreamServer;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author huangjie
 * @description
 * @date 2021-5-5
 */
public class GrpcJavaTest {


    /**
     * 普通模式测试
     * */
    //服务端
    @Test
    public void commonServerTest() throws IOException {
        CommonServer commonServer=new CommonServer();
        commonServer.start(5050);
    }
    //客户端
    @Test
    public void commonClientTest() throws Exception {
        CommonClient commonClient=new CommonClient();
        commonClient.init("localhost",5050);

        String response = null;
        try (InputStream in=new FileInputStream("d:/testByte.jpg")){
            response = commonClient.greet("hello", true, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }
    /**
     * 客户端流式请求模式测试
     * */
    //客户端
    public void customStreamServerTest() throws IOException {
        CustomStreamServer customStreamServer=new CustomStreamServer();
        customStreamServer.start(5050);
    }
    //服务端
    public void customStreamClientTest(){
        CustomStreamClient client=new CustomStreamClient();
        client.transport(new String[]{"1","2","3"});
    }

    /**
     * 服务端流式请求模式测试
     * */
    //客户端
    public void serverStreamServerTest() throws IOException {
        ServerStreamServer server=new ServerStreamServer();
        server.start(5050);
    }
    //服务端
    public void serverStreamClientTest(){
        ServerStreamClient client=new ServerStreamClient();
        client.serverStream("123");
    }


    /**
     * 双向流式请求模式测试
     * */
    //客户端
    public void bidirectionalStreamServerTest() throws IOException {
        BidirectionalStreamServer server=new BidirectionalStreamServer();
        server.start(5050);
    }
    //服务端
    public void bidirectionalStreamClientTest(){
        BidirectionalStreamClient client=new BidirectionalStreamClient();
        client.bidirectionalStream(new String[]{"1","2","3"});
    }

}

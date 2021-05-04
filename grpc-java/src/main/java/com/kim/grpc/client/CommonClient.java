package com.kim.grpc.client;

import com.google.protobuf.ByteString;
import com.kim.grpc.service.GreeterGrpc;
import com.kim.grpc.service.HelloReply;
import com.kim.grpc.service.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author huangjie
 * @description  普通模式：客户端
 * @date 2021-5-5
 */
public class CommonClient {


    private ManagedChannel channel;

    private GreeterGrpc.GreeterBlockingStub blockingStub;

    //初始化连接，传入ip和端口号
    public void init(String host,int port){
        ManagedChannel channel= ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        this.channel=channel;
        this.blockingStub=GreeterGrpc.newBlockingStub(channel);
    }

    //停止
    public void shutdown() throws InterruptedException {
        if(channel!=null){
            System.out.println(channel.authority());
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }


    //连接服务端
    public String greet(String msg,boolean flag,InputStream in) throws IOException {
        System.out.println("客户端请求:"+msg);

        //设置请求参数，与protobuf文件定义的一致,InputStream流转化为bytes数据类型使用ByteString.readFrom(in)
        HelloRequest request= HelloRequest.newBuilder().setName(msg).setFlag(flag).setInput(ByteString.readFrom(in)).build();
        HelloReply response=null;
        try{
            //接收服务端响应的数据
            response=blockingStub.sayHello(request);
        }catch(Throwable e){
            e.printStackTrace();
        }
        String responseMsg=response.getMessage();
        System.out.println(responseMsg);
        return responseMsg;
    }

}

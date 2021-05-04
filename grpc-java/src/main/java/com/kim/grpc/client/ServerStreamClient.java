package com.kim.grpc.client;

import com.kim.grpc.service.custom.stream.CustomStreamGrpc;
import com.kim.grpc.service.server.stream.ServerStreamGrpc;
import com.kim.grpc.service.server.stream.ServerStreamReply;
import com.kim.grpc.service.server.stream.ServerStreamRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @author huangjie
 * @description 服务端流式调用，客户端
 * @date 2021-5-5
 */
public class ServerStreamClient {


    private ManagedChannel channel;

    private ServerStreamGrpc.ServerStreamBlockingStub blockingStub;

    private ServerStreamGrpc.ServerStreamStub asyncStub;

    //初始化连接
    public void init(String host,int port){
        ManagedChannel channel= ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        this.channel=channel;
        this.blockingStub=ServerStreamGrpc.newBlockingStub(channel);
        this.asyncStub= ServerStreamGrpc.newStub(channel);
    }

    //停止
    public void shutdown() throws InterruptedException {
        if(channel!=null){
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    //服务端流式调用客户端
    public void serverStream(String arg){
        ServerStreamRequest request=ServerStreamRequest.newBuilder().setName(arg).build();
        //发送消息
        Iterator<ServerStreamReply> replies = blockingStub.transport(request);
        //监听接收服务端响应的消息
        StringBuilder sb=new StringBuilder();
        while(replies.hasNext()){
            //接收单次返回的消息
            ServerStreamReply reply=replies.next();
            ServerStreamReply.Person person=reply.getMessage();
            String each=person.getName()+person.getAge();
            System.out.println("服务端单次返回:"+each);
            sb.append(each);
            sb.append(",");
        }
        System.out.println("服务端返回:"+sb.toString().replaceAll(",$",""));
    }
}

package com.kim.grpc.client;

import com.kim.grpc.service.custom.stream.CustomStreamGrpc;
import com.kim.grpc.service.custom.stream.HelloStreamReply;
import com.kim.grpc.service.custom.stream.HelloStreamRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * @author huangjie
 * @description 客户端流式调用客户端
 * @date 2021-5-5
 */
public class CustomStreamClient {

    private ManagedChannel channel;

    private CustomStreamGrpc.CustomStreamBlockingStub blockingStub;

    private CustomStreamGrpc.CustomStreamStub asyncStub;

    //初始化连接
    public void init(String host,int port){
        ManagedChannel channel= ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        this.channel=channel;
        this.blockingStub=CustomStreamGrpc.newBlockingStub(channel);
        this.asyncStub= CustomStreamGrpc.newStub(channel);
    }

    //停止
    public void shutdown() throws InterruptedException {
        if(channel!=null){
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    //客户端流式调用客户端
    public String transport(String[] args){
        final String[] result = {""};
        StreamObserver<HelloStreamReply> responseObserver=new StreamObserver<HelloStreamReply>() {
            //接收服务端返回数据
            @Override
            public void onNext(HelloStreamReply reply) {
                HelloStreamReply.Person person=reply.getMessage();
                result[0] ="服务端返回:"+person.getName()+person.getAge();
                System.out.println(result[0]);
            }
            //报错时调用
            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            //一次完整请求完成时调用
            @Override
            public void onCompleted() {
                System.out.println("本次客户端流调用已完成");
            }
        };

        StreamObserver<HelloStreamRequest> requestObserver=asyncStub.transport(responseObserver);

        //连续发送多次请求
        try {
            for(int i=0;i<args.length;i++){
                HelloStreamRequest request=HelloStreamRequest.newBuilder().setName(args[i]).build();
                requestObserver.onNext(request);
                Thread.sleep(2000L);

            }
        } catch (Exception e) {
            e.printStackTrace();
            requestObserver.onError(e);
        }

        requestObserver.onCompleted();
        return result[0];
    }




}

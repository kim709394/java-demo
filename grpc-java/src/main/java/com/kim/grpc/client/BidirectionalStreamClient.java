package com.kim.grpc.client;

import com.kim.grpc.service.bidirectional.stream.BidirectionalStreamGrpc;
import com.kim.grpc.service.bidirectional.stream.BidirectionalStreamReply;
import com.kim.grpc.service.bidirectional.stream.BidirectionalStreamRequest;
import com.kim.grpc.service.server.stream.ServerStreamGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

/**
 * @author huangjie
 * @description 双向流式模式，客户端
 * @date 2021-5-5
 */
public class BidirectionalStreamClient {

    private ManagedChannel channel;

    private BidirectionalStreamGrpc.BidirectionalStreamBlockingStub blockingStub;

    private BidirectionalStreamGrpc.BidirectionalStreamStub asyncStub;

    //初始化连接
    public void init(String host,int port){
        ManagedChannel channel= ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        this.channel=channel;
        this.blockingStub=BidirectionalStreamGrpc.newBlockingStub(channel);
        this.asyncStub= BidirectionalStreamGrpc.newStub(channel);
    }

    //停止
    public void shutdown() throws InterruptedException {
        if(channel!=null){
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    //双向流形式调用客户端
    public void bidirectionalStream(String[] args){

        StreamObserver<BidirectionalStreamRequest> requestStreamObserver=asyncStub.transport(new StreamObserver<BidirectionalStreamReply>() {
            @Override
            public void onNext(BidirectionalStreamReply helloStreamReply) {
                BidirectionalStreamReply.Person person = helloStreamReply.getMessage();
                System.out.println("收到服务端消息:"+person.getName()+person.getAge());

            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("服务端处理完毕");
            }
        });

        //向服务端发送消息
        try {
            for(int i=0;i<args.length;i++){
                BidirectionalStreamRequest request=BidirectionalStreamRequest.newBuilder().setName(args[i]).build();
                requestStreamObserver.onNext(request);
            }

        } catch (Exception e) {
            e.printStackTrace();
            requestStreamObserver.onError(e);
        } finally {
            requestStreamObserver.onCompleted();
        }


    }


}

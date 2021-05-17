package com.kim.grpc.server;

import com.kim.grpc.service.custom.stream.CustomStreamGrpc;
import com.kim.grpc.service.custom.stream.HelloStreamReply;
import com.kim.grpc.service.custom.stream.HelloStreamRequest;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * @author huangjie
 * @description 客户端流式调用服务端
 * @date 2021-5-5
 */
public class CustomStreamServer {

    private Server server;

    public void start(int port) throws IOException {

        server = ServerBuilder.forPort(port).addService((BindableService) new CustomStreamGrpcImpl()).build().start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown();
            }
        });

    }

    //停止
    public void shutdown() {
        if (server != null) {
            server.shutdown();
        }
    }

    //直到停止时断开
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class CustomStreamGrpcImpl extends CustomStreamGrpc.CustomStreamImplBase {

        //客户端流式调用服务端
        @Override
        public StreamObserver<HelloStreamRequest> transport(StreamObserver<HelloStreamReply> responseObserver) {
            return new StreamObserver<HelloStreamRequest>() {

                private Integer count = 0;

                //接收每一次请求
                @Override
                public void onNext(HelloStreamRequest helloStreamRequest) {

                    System.out.println("收到了请求:" + helloStreamRequest.getName() + count++);
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }

                //完整流式请求完调用返回数据
                @Override
                public void onCompleted() {
                    System.out.println(this.count);
                    HelloStreamReply.Person person = HelloStreamReply.getDefaultInstance().getMessage();
                    HelloStreamReply.Person person2 = person.newBuilderForType().setName("服务端").setAge(18).build();
                    HelloStreamReply reply = HelloStreamReply.newBuilder().setMessage(person2).build();
                    responseObserver.onNext(reply);
                    responseObserver.onCompleted();
                }
            };

        }
    }


}

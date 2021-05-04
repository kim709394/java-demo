package com.kim.grpc.server;

import com.kim.grpc.service.bidirectional.stream.BidirectionalStreamGrpc;
import com.kim.grpc.service.bidirectional.stream.BidirectionalStreamReply;
import com.kim.grpc.service.bidirectional.stream.BidirectionalStreamRequest;
import com.kim.grpc.service.server.stream.ServerStreamGrpc;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * @author huangjie
 * @description 双向流式模式，服务端
 * @date 2021-5-5
 */
public class BidirectionalStreamServer {


    private  Server server;

    public  void start(int port) throws IOException {

        server = ServerBuilder.forPort(port).addService((BindableService) new BidirectionalStreamGrpcImpl()).build().start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown();
            }
        });

    }

    //停止
    public  void shutdown() {
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

    static class BidirectionalStreamGrpcImpl extends BidirectionalStreamGrpc.BidirectionalStreamImplBase {

        //双向流式服务端
        @Override
        public StreamObserver<BidirectionalStreamRequest> transport(StreamObserver<BidirectionalStreamReply> responseObserver) {
            return new StreamObserver<BidirectionalStreamRequest>() {
                @Override
                public void onNext(BidirectionalStreamRequest helloStreamRequest) {
                    System.out.println("接收到客户端消息:" + helloStreamRequest.getName());
                    //发送消息给客户端
                    for (int i = 0; i < 3; i++) {
                        BidirectionalStreamReply.Person person = BidirectionalStreamReply.getDefaultInstance().getMessage().newBuilderForType().setName("服务端").setAge(i).build();
                        BidirectionalStreamReply reply = BidirectionalStreamReply.newBuilder().setMessage(person).build();
                        responseObserver.onNext(reply);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
    }


}

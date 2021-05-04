package com.kim.grpc.server;

import com.kim.grpc.service.server.stream.ServerStreamGrpc;
import com.kim.grpc.service.server.stream.ServerStreamReply;
import com.kim.grpc.service.server.stream.ServerStreamRequest;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * @author huangjie
 * @description 服务端流式调用，服务端
 * @date 2021-5-5
 */
public class ServerStreamServer {

    private  Server server;

    public  void start(int port) throws IOException {

        server = ServerBuilder.forPort(port).addService((BindableService) new ServerStreamGrpcImpl()).build().start();
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

    static class ServerStreamGrpcImpl extends ServerStreamGrpc.ServerStreamImplBase {
        //服务端流式调用,服务端
        @Override
        public void transport(ServerStreamRequest request, StreamObserver<ServerStreamReply> responseObserver) {
            //接受请求信息
            System.out.println("收到请求信息" + request.getName());
            //流式响应信息
            try {
                for (int i = 0; i < 3; i++) {
                    ServerStreamReply.Person person = ServerStreamReply.getDefaultInstance().getMessage().newBuilderForType().setName("服务端").setAge(i).build();
                    ServerStreamReply reply = ServerStreamReply.newBuilder().setMessage(person).build();
                    responseObserver.onNext(reply);
                }
            } catch (Exception e) {
                e.printStackTrace();
                responseObserver.onError(e);
            } finally {
                responseObserver.onCompleted();
            }


        }
    }
}

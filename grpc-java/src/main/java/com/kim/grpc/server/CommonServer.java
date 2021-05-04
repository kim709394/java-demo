package com.kim.grpc.server;

import com.kim.grpc.service.GreeterGrpc;
import com.kim.grpc.service.HelloReply;
import com.kim.grpc.service.HelloRequest;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author huangjie
 * @description 普通模式：服务端
 * @date 2021-5-5
 */
public class CommonServer {

    private Server server;

    public void start(int port) throws IOException {
        //监听端口号
        server= ServerBuilder.forPort(port)
                .addService((BindableService) new GreeterImpl())
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                shutdown();
            }
        });

    }

    //停止
    public void shutdown(){
        if(server!=null){
            server.shutdown();
        }
    }

    //直到停止时断开
    public void blockUntilShutdown() throws InterruptedException {
        if(server!=null){
            server.awaitTermination();
        }
    }




    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            //接收客户端请求的参数
            System.out.println(request.getFlag());
            InputStream input = request.getInput().newInput();
            OutputStream out= null;
            try {
                out = new FileOutputStream("d:/testByte.jpg");
                IOUtils.copy(input,out);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(request.getName());
            //给客户端响应
            HelloReply reply=HelloReply.newBuilder().setMessage("服务端回应:").build();
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(reply);
            //如果需要抛出业务异常，可以构建grpc异常
            //responseObserver.onError(new IllegalArgumentException("故意抛出异常"));
            StatusRuntimeException exception= Status.fromThrowable(new IllegalArgumentException("参数不对")).fromCode(Status.Code.FAILED_PRECONDITION).withDescription("报错了").asRuntimeException();

            //responseObserver.onError(exception);
            //throw new IllegalArgumentException("故意抛出异常");
            responseObserver.onCompleted();
        }
    }


}

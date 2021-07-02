package com.kim.common;

import org.junit.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Author kim
 * 网络编程，udp、tcp、socket、http等
 * @Since 2021/7/2
 */
public class NetTest {

    /**
     * UDP服务端
     * */
    @Test
    public void udpServer() throws Exception{
        //创建Socket，指定端口
        DatagramSocket socket=new DatagramSocket(8800);
        //创建数据报文，用来接收客户端传过来的数据
        byte[] data=new byte[1024*1024]; //创建字节数组，用来接收数据
        DatagramPacket packet=new DatagramPacket(data,data.length);
        //接收客户端的数据
        System.out.println("UDP服务已启动，正在监听客户端请求");
        while(true){
            socket.receive(packet);
            //读取客户端传过来的数据
            String s=new String(data,0,packet.getLength());
            System.out.println("客户端传过来的数据:"+s);
            /**
             * *向客户端返回数据
             * */
            //定义客户端的地址和端口号
            InetAddress address=packet.getAddress();
            int port=packet.getPort();
            byte[] back="我已收到，欢迎您".getBytes("UTF-8");
            //创建数据报文，封装返回数据
            DatagramPacket backPacket=new DatagramPacket(back,back.length,address,port);
            //响应客户端
            socket.send(backPacket);
        }

        //关闭socket
        //socket.close();
    }

    /**
     * UDP客户端
     * */
    @Test
    public void udpClient() throws Exception{

        //定义服务端的地址和端口号
        InetAddress address=InetAddress.getByName("localhost");
        int port=8800;
        byte[] data="你好，我是udp客户端".getBytes("UTF-8");
        //创建数据报文，封装发送数据
        DatagramPacket packet=new DatagramPacket(data,data.length,address,port);
        //创建Socket对象
        DatagramSocket socket=new DatagramSocket();
        //向服务器发送数据报文
        socket.send(packet);

        /**
         * 接收服务端发送的数据
         * */
        //创建数据报文
        byte[] receive=new byte[1024*1024];
        DatagramPacket receivePacket=new DatagramPacket(receive,receive.length);
        //接收服务器响应的数据
        socket.receive(receivePacket);
        //读取数据
        String s=new String(receive,0,receivePacket.getLength());
        System.out.println("服务端返回:"+s);
        //关闭资源
        socket.close();


    }



}

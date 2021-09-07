package com.kim.common;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author huangjie
 * @description 本次测试demo的rocketmq服务安装在windows10平台，版本为4.9.1
 * @date 2021-9-3
 */
public class RocketMQClientTest {

    /**
     * 消息发送
     * */
    @Test
    public void sendMsg() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //实例化消息生产者，可从构造方法传入默认消息组名
        DefaultMQProducer producer=new DefaultMQProducer("DEFAULT_GROUP");
        producer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        producer.setNamesrvAddr("localhost:9876");
        producer.setSendMsgTimeout(10000);
        producer.start();
        //连续发送100条消息
        for(int i=0;i<10;i++){
            //创建单条消息，指定topic，tag(相当于子topic)，和消息体,可以在控制台或者命令行创建topic
            Message message=new Message("TopicTest","TagA",("myMsg"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息，注意数据文件放在$home/user/store;磁盘空间要大于4G，否则会发送不成功报超时
            SendResult result = producer.send(message);
            System.out.printf("%s%n",result);
            Thread.sleep(1000);
        }
        //关闭生产者
        //producer.shutdown();


    }



}

package com.kim.common;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author huangjie
 * @description
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
        producer.setNamesrvAddr("localhost:9876");
        producer.setVipChannelEnabled(false);
        producer.start();
        //连续发送100条消息
        for(int i=0;i<100;i++){
            //创建单条消息，指定topic，tag(相当于子topic)，和消息体,默认只有TopicTest这个主题，可以在控制台或者命令行创建topic
            Message message=new Message("TopicTest","myTag",("myMsg"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息
            SendResult result = producer.send(message);
            System.out.printf("%s%n",result);
        }
        //关闭生产者
        producer.shutdown();


    }



}

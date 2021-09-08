package com.kim.common;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author huangjie
 * @description 本次测试demo的rocketmq服务安装在linux平台，版本为4.4.0
 * @date 2021-9-3
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RocketMQClientTest {

    private DefaultMQProducer producer;



    @BeforeEach
    public void producerBuilder() throws MQClientException {

        //实例化消息生产者，可从构造方法传入默认消息组名
        DefaultMQProducer producer = new DefaultMQProducer("DEFAULT_GROUP");
        producer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        producer.setNamesrvAddr("101.34.74.116:9876");
        producer.setSendMsgTimeout(10000);
        this.producer = producer;
        this.producer.start();


    }

    @AfterEach
    public void shutdownProducer() throws MQClientException {

        //关闭生产者
        producer.shutdown();


    }

    /**
     * 同步消息发送
     */
    @Test
    @DisplayName("发送同步消息")
    public void sendMsg() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {


        //连续发送100条消息
        for (int i = 0; i < 10; i++) {
            //创建单条消息，指定topic，tag(相当于子topic)，和消息体,可以在控制台或者命令行创建topic
            Message message = new Message("TopicTest", "TagE", ("myMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息，注意数据文件放在$home/user/store;磁盘空间要大于4G，否则会发送不成功报超时
            SendResult result = producer.send(message);
            System.out.printf("%s%n", result);
            Thread.sleep(1000);
        }

    }

    @Test
    @DisplayName("发送异步消息")
    public void sendAsyncMsg() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //设置异步发送重试次数为0
        producer.setRetryTimesWhenSendAsyncFailed(0);
        //定义发送多少条异步消息，并加上等待消息发送完毕阻塞锁
        int msgCount = 10;
        CountDownLatch2 countDownLatch2 = new CountDownLatch2(msgCount);
        for (int i = 0; i < msgCount; i++) {
            Message msg=new Message("AsyncTopic", "tagA", "key" + i, ("asyncMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            int finalI = i;
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    //发送成功
                    System.out.println("第"+finalI+"条消息发送成功");
                    System.out.printf("%s%n", sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    //发送失败
                    System.out.println("第"+ finalI +"条消息发送失败");
                    e.printStackTrace();
                }
            });
        }
        countDownLatch2.await(5, TimeUnit.SECONDS);
    }


    @Test
    @DisplayName("单向发送消息")
    public void sendOneway() throws UnsupportedEncodingException, RemotingException, MQClientException, InterruptedException {
        for(int i=0;i<10;i++){
            Message msg=new Message("SendOneway","tagA","key"+i,("oneway"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //单向发送，不管是否发送成功，没有任何返回结果
            producer.sendOneway(msg);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("消费者测试")
    public static class ConsumerTest{

        private DefaultMQPushConsumer consumer;

        @BeforeEach
        public void consumerBuilder() throws MQClientException {

            DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("DEFAULT_GROUP");
            consumer.setNamesrvAddr("101.34.74.116:9876");
            this.consumer=consumer;
        }

        @AfterEach
        public void consumerStart() throws MQClientException {
            this.consumer.start();
        }

        @Test
        @DisplayName("消费消息")
        public void consumeMsg() throws MQClientException {

            //订阅指定的topic和tag消息，第二个参数是tag的模糊匹配，*表示订阅全部，也支持tag1 || tag2 || tag3格式
            consumer.subscribe("TopicTest","TagD");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

        }


    }



}

package com.kim.common;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.*;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.*;
import org.python.antlr.op.Add;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huangjie
 * @description 本次测试demo的rocketmq服务安装在linux平台，版本为4.4.0
 * @date 2021-9-3
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RocketMQClientTest {

    private DefaultMQProducer producer;
    private static final String ACCESS_KEY="rocketmq1";
    private static final String SECRET_KEY="12345678";
    private static final String ADDRESS="101.34.74.116:9876";
    @BeforeEach
    public void producerBuilder() throws MQClientException {

        //实例化消息生产者，可从构造方法传入默认消息组名
        DefaultMQProducer producer = new DefaultMQProducer("DEFAULT_GROUP", new AclClientRPCHook(new SessionCredentials(ACCESS_KEY,SECRET_KEY)));
        producer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        producer.setNamesrvAddr(ADDRESS);
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
            Message msg = new Message("AsyncTopic", "tagA", "key" + i, ("asyncMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            int finalI = i;
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    //发送成功
                    System.out.println("第" + finalI + "条消息发送成功");
                    System.out.printf("%s%n", sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    //发送失败
                    System.out.println("第" + finalI + "条消息发送失败");
                    e.printStackTrace();
                }
            });
        }
        countDownLatch2.await(5, TimeUnit.SECONDS);
    }


    @Test
    @DisplayName("单向发送消息")
    public void sendOneway() throws UnsupportedEncodingException, RemotingException, MQClientException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("SendOneway", "tagA", "key" + i, ("oneway" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //单向发送，不管是否发送成功，没有任何返回结果
            producer.sendOneway(msg);
        }
    }

    /**
     * 顺序消息发送和消费，rocketmq默认是轮询对指定topic绑定的所有队列分别发送消息，
     * 消费消息的时候，是并发接收，因此是不会按严格顺序进行发送和消费的。
     * 顺序消息的机制就是让发送方和消费方都选择同一个队列进行发送和消费消息。
     * 全部消息都在同一个队列进行发送和消费就是全局顺序消费
     * 分别选择不同的队列进行发送和消费则是分区顺序消费
     */
    @Test
    @DisplayName("顺序消息发送")
    public void sendOrderly() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("OrderMsg", "tagC", "key" + i, ("hello orderMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            /**
             * 发送顺序消息，最后一个参数是传入给队列选择器方法的参数,即在select(List<MessageQueue> list, Message message, Object arg)
             * 方法里面的作为第三个参数arg传入
             * 在同一个队列的消息生产和消费将会顺序进行
             */
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    int queueId = (int) arg;
                    return list.get(queueId);
                }
            }, 0);
            System.out.println(String.format("SendResult status:%s, queueId:%d, msg:%s", sendResult.getSendStatus(), sendResult.getMessageQueue().getQueueId(), new String(msg.getBody())));
        }

    }

    @Test
    @DisplayName("发送延时消息")
    public void sendMsgDelay() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 0; i < 10; i++) {
            Message message = new Message("DelayMsg", "tagB", "key" + i, ("delayMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            /**
             * 发送消息延时等级，参考下面这个配置类messageDelayLevel属性
             * org.apache.rocketmq.store.config.MessageStoreConfig
             * 目前仅支持以下这么多延时等级，从1~18等级依次对应
             * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
             * 这里以等级3为例
             * 要先启动消息监听服务再发送延时消费消息，延时消息只有在被消费之后才能存储
             * */
            message.setDelayTimeLevel(3);
            //同步发送消息
            producer.send(message);
            System.out.println(("key" + i + "的发送时间:") + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        }
    }

    @Test
    @DisplayName("异步发送延时消息,消费者未能消费成功")
    public void sendDelayMsgAsync() throws UnsupportedEncodingException, RemotingException, MQClientException, InterruptedException {
        Message message=new Message("DelayMsg","tagB","key","hello delay".getBytes(RemotingHelper.DEFAULT_CHARSET));
        message.setDelayTimeLevel(3);
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }

            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
            }
        },3000L);
        for(;;){}
    }

    @Test
    @DisplayName("批量发送消息")
    public void sendBatchMsgs() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add(new Message("BatchMsg", "tagA", "key" + i, ("batchMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET)));
        }
        producer.send(messages);
    }

    /**
     * 批量消息列表分割
     * 批量发送消息能显著提高传递小消息的性能。限制是这些批量消息应该有相同的topic，相同的waitStoreMsgOK，
     * 而且不能是延时消息。此外，这一批消息的总大小不应超过4MB。
     * 复杂度只有当你发送大批量时才会增长，你可能不确定它是否超过了大小限制（4MB）
     * 因此，需要将批量消息集合里面的单个大于4MB的消息过滤掉，以及将集合里面分割成不大于4MB的消息子集合
     * 再逐一进行发送
     */
    public static class MsgSplitter implements Iterator<List<Message>> {

        private static final Integer maxCapacity = 1024 * 1024 * 4;
        private Integer offset;
        private List<Message> messages;

        public MsgSplitter(List<Message> messages) {
            this.messages = messages;
        }

        @Override
        public boolean hasNext() {

            return offset < messages.size();
        }

        @Override
        public List<Message> next() {
            int tmpTotalSize = 0;
            List<Message> splitList = new ArrayList<>();
            for (; offset < messages.size(); offset++) {
                int capacitySimple = calcMessageSize(messages.get(offset));
                if (capacitySimple > maxCapacity) {
                    continue;
                } else {
                    tmpTotalSize += capacitySimple;
                }
                if (tmpTotalSize > maxCapacity) {
                    break;
                } else {
                    splitList.add(messages.get(offset));
                }
            }
            return splitList;
        }

        /**
         * 计算单条消息的容量大小
         */
        private int calcMessageSize(Message message) {
            int tmpSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> properties = message.getProperties();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                tmpSize += entry.getKey().length() + entry.getValue().length();
            }
            tmpSize = tmpSize + 20; // 增加日志的开销20字节
            return tmpSize;
        }

    }

    @Test
    @DisplayName("消息分割后批量发送消息")
    public void sendBatchSplitMsgs() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add(new Message("BatchMsg", "tagA", "key" + i, ("batchMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET)));
        }

        Iterator<List<Message>> splitter = new MsgSplitter(messages);
        while (splitter.hasNext()) {
            List<Message> next = splitter.next();
            producer.send(next);
        }

    }

    @Test
    @DisplayName("过滤消息发送")
    public void sendFilterMsg() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        /**
         * 默认情况下，通过大路由topic和小路由tag就可以订阅到具体的消息
         * 但是在复杂的场景下只有这两个可能不够，于是消息路由的订阅rocketmq提供了sql语法
         * 标准的统配规则，例如下面的例子，给消息设置属性a=i,那么在订阅的时候可以通过消息
         * 选择器MessageSelector利用sql语法进行对消息的过滤订阅，只有满足规则的消息被订阅
         * 消息过滤订阅请看下面的过滤消息接收的example
         * */
        for (int i = 0; i < 10; i++) {
            Message message = new Message("FilterMsg", "tagA", "key" + i, ("filterMsg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            message.putUserProperty("a", String.valueOf(i));
            producer.send(message);
        }

    }

    //push模式消费
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("推送模式消费者测试")
    public static class PushConsumerTest {

        private DefaultMQPushConsumer consumer;

        @BeforeEach
        public void consumerBuilder() throws MQClientException {

            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("DEFAULT_GROUP", new AclClientRPCHook(new SessionCredentials(ACCESS_KEY,SECRET_KEY)), new AllocateMessageQueueAveragely());
            consumer.setNamesrvAddr(ADDRESS);
            this.consumer = consumer;
        }

        @AfterEach
        public void consumerStart() throws MQClientException {
            this.consumer.start();
            while (true) {
            }
        }

        @Test
        @DisplayName("同步消费消息")
        public void syncConsumeMsg() throws MQClientException {

            //订阅指定的topic和tag消息，第二个参数是tag的模糊匹配，*表示订阅全部，也支持tag1 || tag2 || tag3格式
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.subscribe("TopicTest", "TagE");
            //并发地监听消息，消息将会并发接收
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    msgs.stream().forEach(messageExt -> {
                        System.out.println(new String(messageExt.getBody()) + "，接收时间:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                    });
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

        }

        @Test
        @DisplayName("多实例广播消费消息")
        public void broadcastingConsumeMsg() throws MQClientException {

            //订阅指定的topic和tag消息，第二个参数是tag的模糊匹配，*表示订阅全部，也支持tag1 || tag2 || tag3格式
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //默认是集群模式，同一个消费组的多实例通过负载均衡分摊消息，广播模式是同一个消费组的每一个实例获得全量消息
            consumer.setMessageModel(MessageModel.BROADCASTING);
            consumer.subscribe("ConsumerBroadcast", "TagE");
            consumer.setInstanceName("broadcastingConsumeMsg1");

            //并发地监听消息，消息将会并发接收
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    msgs.stream().forEach(messageExt -> {
                        System.out.println(new String(messageExt.getBody()) + "，接收时间:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                    });
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

        }

        @Test
        @DisplayName("多实例广播消费消息")
        public void broadcastingConsumeMsg2() throws MQClientException {

            //订阅指定的topic和tag消息，第二个参数是tag的模糊匹配，*表示订阅全部，也支持tag1 || tag2 || tag3格式
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //默认是集群模式，同一个消费组的多实例通过负载均衡分摊消息，广播模式是同一个消费组的每一个实例获得全量消息
            consumer.setMessageModel(MessageModel.BROADCASTING);
            consumer.subscribe("ConsumerBroadcast", "TagE");

            consumer.setInstanceName("broadcastingConsumeMsg2");

            //并发地监听消息，消息将会并发接收
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    msgs.stream().forEach(messageExt -> {
                        System.out.println(new String(messageExt.getBody()) + "，接收时间:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                    });
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

        }

        @Test
        @DisplayName("延时消息消费")
        public void consumeDelayMsg() throws MQClientException {

            //订阅指定的topic和tag消息，第二个参数是tag的模糊匹配，*表示订阅全部，也支持tag1 || tag2 || tag3格式
            consumer.subscribe("DelayMsg", "tagB");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //并发地监听消息，消息将会并发接收
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    msgs.stream().forEach(messageExt -> {
                        System.out.println(new String(messageExt.getBody()) + "，接收时间:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                    });
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

        }

        @Test
        @DisplayName("顺序消费消息")
        public void consumeMsgOrderly() throws MQClientException {
            consumer.subscribe("OrderMsg", "tagC");
            //设置第一次启动的时候是从队列头还是队列尾开始消费，非第一次启动的话则是从上次消费的位置继续往下消费，这里设置从头到尾消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    //自动提交事务
                    context.setAutoCommit(true);
                    msgs.stream().forEach(msg -> {
                        System.out.println("consumeThread=" + Thread.currentThread().getName() + "  queueId=" + msg.getQueueId() + ", content:" + new String(msg.getBody()));
                    });
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });

        }

        @Test
        @DisplayName("过滤消息接收")
        public void filterMsgListence() throws MQClientException {

            /**RocketMQ只定义了一些基本语法来支持这个特性。你也可以很容易地扩展它。
             *数值比较，比如：>，>=，<，<=，BETWEEN，=；
             *字符比较，比如：=，<>，IN；
             *IS NULL 或者 IS NOT NULL；
             *逻辑符号 AND，OR，NOT；
             *常量支持类型为：
             *数值，比如：123，3.1415；
             *字符，比如：'abc'，必须用单引号包裹起来；
             *NULL，特殊的常量
             *布尔值，TRUE 或 FALSE
             * *只有使用push模式的消费者才能用使用SQL92标准的sql语句*/
            consumer.subscribe("FilterMsg", MessageSelector.bySql("a between 0 and 3"));
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    msgs.stream().forEach(messageExt -> {
                        System.out.println(new String(messageExt.getBody()) + "，接收时间:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                    });
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        }


    }



    @Nested
    @DisplayName("事务消息")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public static class TransactionMsg {

        private TransactionMQProducer producer;

        private TransactionMsgExecuteHandle executeHandle;

        private TransactionMsgCheckHandle checkHandle;

        //本地事务执行业务接口
        public interface TransactionMsgExecuteHandle {
            LocalTransactionState executeLocalTransaction(Message msg, Object arg);
        }

        //本地事务检查业务接口
        public interface TransactionMsgCheckHandle {
            LocalTransactionState checkLocalTransaction(MessageExt msg);
        }

        @BeforeAll
        public void initProducer() throws MQClientException {
            //实例化事务消息生产者
            TransactionMQProducer producer = new TransactionMQProducer("transactionMsg");
            //创建线程池
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            //定义事务消息监听器
            TransactionListener transactionListener = new TransactionListener() {
                /**
                 * 事务消息发送后，先执行这个方法，如果这个方法返回COMMIT_MESSAGE，则消息直接发送
                 * 如果返回ROLLBACK_MESSAGE，则发送出去的消息直接回滚，不会发送到消息队列
                 * 如果返回UNKNOW，表示不能确定本地事务是否提交成功，则会进入间隔回查方法checkLocalTransaction(MessageExt msg)
                 * rocketmq将会每隔一段时间调用回查方法，如果回查方法返回COMMIT_MESSAGE，则消息直接发送
                 * 如果回查方法返回ROLLBACK_MESSAGE，则发送出去的消息直接回滚，不会发送到消息队列
                 * 如果回查方法返回UNKNOW，则会不断的循环调用回查方法，循环次数和间隔时间可以通过配置和重写接口进行个性化设置
                 * executeLocalTransaction(Message msg, Object arg)方法用来当即检查本地业务事务是否提交成功，arg参数是发送事务
                 * 消息的方法：sendMessageInTransaction(final Message msg, final Object arg)的第二个参数，可用于消息业务参数的
                 * 传递处理。
                 * LocalTransactionState checkLocalTransaction(MessageExt msg)方法用于本地事务回查，两个方法可以通过transactionId
                 * 连线
                 */
                @Override
                public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                    return executeHandle.executeLocalTransaction(msg, arg);
                }

                @Override
                public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                    return checkHandle.checkLocalTransaction(msg);
                }
            };
            //装配
            producer.setExecutorService(executorService);
            producer.setTransactionListener(transactionListener);
            //设置broker服务器地址
            producer.setInstanceName(String.valueOf(System.currentTimeMillis()));
            producer.setNamesrvAddr("101.34.74.116:9876");
            producer.setSendMsgTimeout(10000);
            this.producer = producer;
            //启动
            this.producer.start();
        }

        @AfterAll
        public void shutdown() throws InterruptedException {

            producer.shutdown();
        }

        /**
         * 事务消息监听器接口方法将会返回以下三种状态
         * TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
         * TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
         * TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
         * 事务消息不支持延时消息和批量消息。
         * 为了避免单个消息被检查太多次而导致半队列消息累积，我们默认将单个消息的检查次数限制为 15 次，
         * 但是用户可以通过 Broker 配置文件的 transactionCheckMax参数来修改此限制。
         * 如果已经检查某条消息超过 N 次的话（ N = transactionCheckMax ） 则 Broker 将丢弃此消息，
         * 并在默认情况下同时打印错误日志。用户可以通过重写 AbstractTransactionalMessageCheckListener类来修改这个行为。
         * 事务消息将在 Broker 配置文件中的参数 transactionTimeout 这样的特定时间长度之后被检查。
         * 当发送事务消息时，用户还可以通过设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制，
         * 该参数优先于 transactionTimeout 参数。事务性消息可能不止一次被检查或消费。##--rocketmq4.4.0版本测试发现不生效--##
         * 提交给用户的目标主题消息可能会失败，目前这依日志的记录而定。它的高可用性通过 RocketMQ 本身的高可用性机制
         * 来保证，如果希望确保事务消息不丢失、并且事务完整性得到保证，建议使用同步的双重写入机制。
         * 事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享。与其他类型的消息不同，事务消息允许
         * 反向查询、MQ服务器能通过它们的生产者 ID 查询到消费者。
         */

        @Test
        @DisplayName("本地事务提交成功的例子")
        public void localSuccess() throws UnsupportedEncodingException, MQClientException {
            executeHandle = (msg, arg) -> {
                System.out.printf("执行本地事务是否提交: %s%n   %s", msg, arg);
                return LocalTransactionState.COMMIT_MESSAGE;
            };

            checkHandle = msg -> {
                System.out.printf("执行事务消息检查: %n%s", msg);
                return LocalTransactionState.COMMIT_MESSAGE;
            };
            Message message = new Message("TransactionMsg", "tagA", String.valueOf(System.currentTimeMillis()), ("my transactionMsg" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendMessageInTransaction(message, "本地事务执行提交");

        }

        @Test
        @DisplayName("本地事务执行回滚的例子")
        public void localRollback() throws UnsupportedEncodingException, MQClientException {
            executeHandle = (msg, arg) -> {
                System.out.printf("执行本地事务是否提交: %s%n   %s", arg);
                return LocalTransactionState.ROLLBACK_MESSAGE;
            };

            checkHandle = msg -> {
                System.out.printf("执行事务消息检查: %n%s", msg);
                return LocalTransactionState.COMMIT_MESSAGE;
            };
            Message message = new Message("TransactionMsg", "tagA", String.valueOf(System.currentTimeMillis()), ("my transactionMsg" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendMessageInTransaction(message, "本地事务执行回滚");

        }

        @Test
        @DisplayName("本地事务执行未知需要检查执行，且检查执行3次后提交事务消息的例子")
        public void localUnknowCheckCommit() throws UnsupportedEncodingException, MQClientException, InterruptedException {
            executeHandle = (msg, arg) -> {
                System.out.printf("执行本地事务是否提交: %s%n   %s", msg, arg);
                return LocalTransactionState.UNKNOW;
            };
            AtomicInteger handleCount = new AtomicInteger();
            checkHandle = msg -> {
                System.out.println();
                System.out.printf("执行事务消息检查: %n%s,执行次数: %s ,当前时间: %s", msg, handleCount.incrementAndGet(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                return handleCount.get() >= 3 ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.UNKNOW;

            };
            Message message = new Message("TransactionMsg", "tagA", String.valueOf(System.currentTimeMillis()), ("my transactionMsg" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //设置用户态的事务消息检查间隔时间
           /* message.putUserProperty(MessageConst.PROPERTY_CHECK_IMMUNITY_TIME_IN_SECONDS,"6");
            System.out.println(message.getProperty(MessageConst.PROPERTY_CHECK_IMMUNITY_TIME_IN_SECONDS));*/
            System.out.println("消息体:"+new String(message.getBody()));
            producer.sendMessageInTransaction(message, "本地事务执行未知，需要检查执行");
            for(int i=0;i<10000;i++){
                Thread.sleep(1*1000);
            }
        }


        @Test
        @DisplayName("本地事务执行未知需要检查执行，且检查执行3次后回滚事务消息的例子")
        public void localUnknowCheckRollback() throws UnsupportedEncodingException, MQClientException, InterruptedException {
            executeHandle = (msg, arg) -> {
                System.out.printf("执行本地事务是否提交: %s%n   %s", msg, arg);
                return LocalTransactionState.UNKNOW;
            };
            AtomicInteger handleCount = new AtomicInteger();
            checkHandle = msg -> {
                System.out.println();
                System.out.printf("执行事务消息检查: %n%s,执行次数: %s ,当前时间: %s", msg, handleCount.incrementAndGet(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                return handleCount.get() >= 3 ? LocalTransactionState.ROLLBACK_MESSAGE : LocalTransactionState.UNKNOW;

            };
            Message message = new Message("TransactionMsg", "tagA", String.valueOf(System.currentTimeMillis()), ("my transactionMsg" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //设置用户态的事务消息检查间隔时间
           /* message.putUserProperty(MessageConst.PROPERTY_CHECK_IMMUNITY_TIME_IN_SECONDS,"6");
            System.out.println(message.getProperty(MessageConst.PROPERTY_CHECK_IMMUNITY_TIME_IN_SECONDS));*/
            System.out.println("消息体:"+new String(message.getBody()));
            producer.sendMessageInTransaction(message, "本地事务执行未知，需要检查执行");
            for(int i=0;i<10000;i++){
                Thread.sleep(1*1000);
            }
        }

    }


}

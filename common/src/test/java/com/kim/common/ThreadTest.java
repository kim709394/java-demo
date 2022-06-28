package com.kim.common;

import com.google.common.util.concurrent.RateLimiter;
import com.kim.common.entity.C;
import org.apache.commons.validator.Var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author kim
 * @Since 2021/4/21
 * 线程、多线程测试
 */
public class ThreadTest {


    /****************************************synchronized关键字***********************************************/
    /**
     * 同步代码块写法
     */
    @Test
    @DisplayName("锁对象")
    public void testSyncObj() {

        int count = 0;
        for (int i = 0; i < 4; i++) {
            /**
             * 给当前对象加锁，两个以上线程同时访问同一对象实例的静态代码块时需要获取对象锁
             * 否则将会阻塞
             * */
            synchronized (this) {
                count++;
            }
        }
        System.out.println(count);
    }

    @Test
    @DisplayName("锁class")
    public void testSyncClass() {
        synchronized (ThreadTest.class) {
            //两个以上线程同时访问该静态代码块，无论是否同一对象实例，需要获取该类的class锁，否则将会阻塞
            System.out.println("锁class");
        }
    }

    @Test
    @DisplayName("锁非静态方法")
    public void testSyncMethod() {
        //两个以上线程访问同一对象实例的该方法，需要获取方法锁，否则将会阻塞
        syncMethod();
    }

    public synchronized void syncMethod() {
        System.out.println("锁方法");
    }

    @Test
    @DisplayName("锁静态方法")
    public void testSyncStaticMethod() {
        //两个以上线程访问该静态方法，无论是否同一对象实例，都需要获取静态方法锁，否则将会阻塞
        syncStaticMethod();
    }

    public static synchronized void syncStaticMethod() {
        System.out.println("锁静态方法");
    }

    /*********************************************volatile关键字************************************************/

    private boolean nonVolatile=false;
    private volatile boolean withVolatile=false;

    @Test
    @DisplayName("未加volatile关键字，多线程对全局变量进行修改")
    public void nonVolatile() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("thread1:全局变量初始值："+nonVolatile);
            int i = 0;
            while(!nonVolatile){
                i++;
                //线程二修改了全局变量nonVolatile，但是线程1不可见，因此会一直无限循环
                //笔者自己测试发现，在循环内调用System.out.println()或Thread.sleep()方法会导致即使不加volatile关键字的全局变量也会变成马上可见
                //具体原因未知
            }
            System.out.println(String.format("thread1循环%s次",i));
            countDownLatch.countDown();
        },"thread1").start();

        new Thread(() -> {
            System.out.println("thread2:全局变量初始值："+nonVolatile);

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nonVolatile = true;
            countDownLatch.countDown();
        },"thread2").start();

        countDownLatch.await();

    }

    @Test
    @DisplayName("加volatile关键字，多线程对全局变量进行修改")
    public void withVolatile() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("thread1:全局变量初始值："+withVolatile);
            int i = 0;
            while(!withVolatile){
                i++;
                //线程二修改了全局变量withVolatile，由于withVolatile是可见的，因此线程1的withVolatile变量会立即被修改，所以会跳出循环
            }
            System.out.println(String.format("thread1循环%s次",i));
            countDownLatch.countDown();
        },"thread1").start();

        new Thread(() -> {
            System.out.println("thread2:全局变量初始值："+withVolatile);

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            withVolatile = true;
            countDownLatch.countDown();
        },"thread2").start();

        countDownLatch.await();

    }


    /******************************************juc包**********************************************/

    /**
     * 线程池业务接口使用
     */
    @Test
    @DisplayName("不推荐此方式创建线程池，因为队列大小为Integer.Max_Value,太大会堆积太多请求")
    public void ExecutorService() throws ExecutionException, InterruptedException {
        //实例一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //使用Runnable提交匿名线程，不能接收到返回值
        executorService.execute(() -> {
            System.out.println("执行匿名线程");
        });
        //使用Callable接口提交匿名线程，可以接收返回值
        Future<Integer> submit = executorService.submit(() -> 10);
        System.out.println("返回值:" + submit.get());
    }


    @Test
    @DisplayName("线程池测试，推荐使用构造方法实例化线程池")
    public void testThreadPool() throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 30, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));   //创建一个线程池

        for (int i = 0; i < 31; i++) {
            MyTask myTask = new MyTask(i); //创建一个线程任务
            threadPoolExecutor.execute(myTask);    //将这个任务加入线程池
            System.out.println("线程池的数目大小：" + threadPoolExecutor.getPoolSize() + ",队列中正在等待的缓存任务数目" + threadPoolExecutor.getQueue().size() + ",已执行完的线程数量：" + threadPoolExecutor.getCompletedTaskCount());
        }

        Thread.sleep(4000);
        System.out.println("4秒后，线程池任务已全部执行完，线程池的数目大小：" + threadPoolExecutor.getPoolSize() + ",队列中正在等待的缓存任务数目" + threadPoolExecutor.getQueue().size() + ",已执行完的线程数量：" + threadPoolExecutor.getCompletedTaskCount());

        Thread.sleep(5000);
        System.out.println("9秒后，线程池的数目大小：" + threadPoolExecutor.getPoolSize() + ",队列中正在等待的缓存任务数目" + threadPoolExecutor.getQueue().size() + ",已执行完的线程数量：" + threadPoolExecutor.getCompletedTaskCount());

        threadPoolExecutor.shutdown();  //销毁线程池

        MyTask myTask = new MyTask(709394); //创建一个线程任务
        threadPoolExecutor.execute(myTask);

        System.out.println("销毁线程池后：线程池的数目大小：" + threadPoolExecutor.getPoolSize() + ",队列中正在等待的缓存任务数目" + threadPoolExecutor.getQueue().size() + ",已执行完的线程数量：" + threadPoolExecutor.getCompletedTaskCount());
    }

    public static class MyTask implements Runnable {

        private Integer i;

        public MyTask(Integer i) {

        }

        @Override
        public void run() {
            System.out.println(i);
        }
    }

    /**
     * 信号量测试
     */
    private Semaphore seamphore;

    @Test
    public void semaphoreTest() {
        seamphore = new Semaphore(5);  //实例化一个信号量,每次只有5个线程同时执行
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            Future<?> submit = executorService.submit(() -> {
                try {
                    seamphore.acquire();  //打开信号灯
                    System.out.println(finalI); //执行线程任务
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    seamphore.release();  //释放限流开关
                }
            });
            futures.add(submit);
        }
        ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;
        while (true) {
            if (executor.getCompletedTaskCount() == 50) {
                break;
            }
        }
    }

    /**
     * 倒计时器CountDownLatch
     * 不足：中途不能对倒计时数再次设置，只能使用一次便不能再次使用
     * */
    @Test
    @DisplayName("主线程等待所有子线程执行完毕才往下执行")
    public void masterAwait() throws InterruptedException {
        int count=10;
        //实例化一个CountDownLatch，倒计时10个线程
        CountDownLatch countDownLatch=new CountDownLatch(count);
        for(int i = 0;i<count;i++){
            int finalI = i;
            new Thread(() -> {
                System.out.println("thread"+ finalI);
                //调用countDown()方法，使倒计时-1
                countDownLatch.countDown();
            },"thread"+i).start();
        }
        //主线程调用await()方法等待倒计时器倒计时为0时才往下执行，这个过程是在阻塞中等待所欲线程执行完毕
        countDownLatch.await();

        System.out.println("finish");
    }

    @Test
    @DisplayName("阻塞所有子线程，主线程统一释放倒计时器，让所有子线程在同一起跑线同时运行")
    public void currentHandle() throws InterruptedException {
        int count=1;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms");
        //实例化一个CountDownLatch，倒计时1个线程
        CountDownLatch countDownLatch=new CountDownLatch(count);
        for(int i = 0;i<10;i++){
            int finalI = i;
            new Thread(() -> {
                //调用countDown()方法，使倒计时-1
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("thread%s,执行时间是:%s",finalI,format.format(new Date())));
            },"thread"+i).start();
        }
        Thread.sleep(1000L);
        countDownLatch.countDown();
        Thread.sleep(1000L);
    }


    /****************************************cas原理*********************************************/
    //带有版本号的cas原子类，初始值为1，版本号为1
    private AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);

    @Test
    @DisplayName("通过添加修改版本号解决ABA问题")
    public void resloveABA() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {

            int stamp = atomicStampedReference.getStamp();
            System.out.println("初始版本为：" + stamp);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean setResult = atomicStampedReference.compareAndSet(1, 2, stamp, stamp + 1);
            System.out.println("thread1:value=" + atomicStampedReference.getReference() + ",stamp=" + atomicStampedReference.getStamp() + ",setResult:" + setResult);
            setResult = atomicStampedReference.compareAndSet(2, 1, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("thread1:value=" + atomicStampedReference.getReference() + ",stamp=" + atomicStampedReference.getStamp() + ",setResult:" + setResult);
            countDownLatch.countDown();
        }, "thread1").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("初始版本为：" + stamp);
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //版本号与内存值不一致，修改失败
            boolean setResult = atomicStampedReference.compareAndSet(1, 4, stamp, stamp + 1);
            System.out.println("thread2:value=" + atomicStampedReference.getReference() + ",stamp=" + atomicStampedReference.getStamp() + ",setResult:" + setResult);

            countDownLatch.countDown();
        }, "thread2").start();
        countDownLatch.await();
    }

    private AtomicInteger atomicInteger=new AtomicInteger(10);

    @Test
    @DisplayName("原子类Integer cas修改")
    public void atomicIntegerCasUpdate() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(2);
        new Thread(() -> {
            int i = atomicInteger.get();
            System.out.println("thread1初始值："+i);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicInteger.compareAndSet(i, 20);
            System.out.println("thread1:"+atomicInteger.get()+",result:"+result);
            countDownLatch.countDown();

        },"thread1").start();

        new Thread(() -> {
            int i = atomicInteger.get();
            System.out.println("thread2初始值："+i);
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //由于线程1先把共享变量atomicInteger修改为20，因此线程2获取的初始值与内存值不同，修改失败
            boolean result = atomicInteger.compareAndSet(i, 30);
            System.out.println("thread2:"+atomicInteger.get()+",result:"+result);
            countDownLatch.countDown();

        },"thread2").start();
        countDownLatch.await();
    }

    @Test
    @DisplayName("原子类Integer cas新增")
    public void atomicIntegerCasAdd() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("thread1初始值："+atomicInteger.get());
            //根据JMM内存模型，线程1先拷贝一份共享变量atomicInteger的初始值为10，然后对比atomicInteger内存的值，相等则新增10并更新内存的值为20
            int i = atomicInteger.addAndGet(10);
            System.out.println("thread1新增10之后value="+i);
            countDownLatch.countDown();
        },"thread1").start();

        new Thread(() -> {
            System.out.println("thread2初始值："+atomicInteger.get());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
              线程2也是先拷贝一份共享变量atomicInteger的初始值为10，然后对比atomicInteger内存的值，发现被其他线程变更为20，则
              启用自旋锁循环获取更新后共享变量atomicInteger内存的值，然后再比对内存真实的值，在新增10并更新内存的值为30
              */
            int i = atomicInteger.addAndGet(10);
            System.out.println("thread2新增10之后value="+i);
            countDownLatch.countDown();
        },"thread2").start();
        countDownLatch.await();
    }



    /**
     * google.guava限流
     */
    @Test
    public void rateLimiter() {
        //开启一个每秒发放10个令牌的限流器，每秒指的是整秒，下一秒的话重新清零计算。
        RateLimiter rateLimiter = RateLimiter.create(10);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //得到令牌许可，参数传入permits代表每秒允许的请求数中每获得一个许可需要等待的permits*100毫秒数
                    rateLimiter.acquire();
                    System.out.println("Accessing:," + finalI + ":" + new SimpleDateFormat("yy-MM-dd HH:mm:ss:SSS").format(new Date()));
                }
            });
        }
        while (true) {
            if (executor.getCompletedTaskCount() == 50) {
                break;
            }
        }
    }


}

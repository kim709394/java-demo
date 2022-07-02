package com.kim.common;

import com.google.common.util.concurrent.RateLimiter;
import com.kim.common.entity.C;
import org.apache.poi.ss.formula.functions.Count;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author kim
 * @Since 2021/4/21
 * 线程、多线程测试
 */
public class MultiThreadTest {


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
        synchronized (MultiThreadTest.class) {
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
    //volatile的作用有两个,1、防止指令重排。2、让多线程内存共享变量变得可见
    private boolean nonVolatile = false;
    private volatile boolean withVolatile = false;

    @Test
    @DisplayName("未加volatile关键字，多线程对全局变量进行修改")
    public void nonVolatile() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("thread1:全局变量初始值：" + nonVolatile);
            int i = 0;
            while (!nonVolatile) {
                i++;
                //线程二修改了全局变量nonVolatile，但是线程1不可见，因此会一直无限循环
                //笔者自己测试发现，在循环内调用System.out.println()或Thread.sleep()方法会导致即使不加volatile关键字的全局变量也会变成马上可见
                //具体原因未知
            }
            System.out.println(String.format("thread1循环%s次", i));
            countDownLatch.countDown();
        }, "thread1").start();

        new Thread(() -> {
            System.out.println("thread2:全局变量初始值：" + nonVolatile);

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nonVolatile = true;
            countDownLatch.countDown();
        }, "thread2").start();

        countDownLatch.await();

    }

    @Test
    @DisplayName("加volatile关键字，多线程对全局变量进行修改")
    public void withVolatile() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("thread1:全局变量初始值：" + withVolatile);
            int i = 0;
            while (!withVolatile) {
                i++;
                //线程二修改了全局变量withVolatile，由于withVolatile是可见的，因此线程1的withVolatile变量会立即被修改，所以会跳出循环
            }
            System.out.println(String.format("thread1循环%s次", i));
            countDownLatch.countDown();
        }, "thread1").start();

        new Thread(() -> {
            System.out.println("thread2:全局变量初始值：" + withVolatile);

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            withVolatile = true;
            countDownLatch.countDown();
        }, "thread2").start();

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
        seamphore = new Semaphore(5);  //实例化一个信号量,每次只有5个线程同时执行,默认是非公平模式
        //seamphore = new Semaphore(5,true); //另一个构造方法传入true则为公平模式，传入false则为非公平模式
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
     */
    @Test
    @DisplayName("主线程等待所有子线程执行完毕才往下执行")
    public void masterAwait() throws InterruptedException {
        int count = 10;
        //实例化一个CountDownLatch，倒计时10个线程
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("thread" + finalI);
                //调用countDown()方法，使倒计时-1
                countDownLatch.countDown();
            }, "thread" + i).start();
        }
        //主线程调用await()方法等待倒计时器倒计时为0时才往下执行，这个过程是在阻塞中等待所欲线程执行完毕
        countDownLatch.await();

        System.out.println("finish");
    }

    @Test
    @DisplayName("阻塞所有子线程，主线程统一释放倒计时器，让所有子线程在同一起跑线同时运行")
    public void currentHandle() throws InterruptedException {
        int count = 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms");
        //实例化一个CountDownLatch，倒计时1个线程
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                //调用countDown()方法，使倒计时-1
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("thread%s,执行时间是:%s", finalI, format.format(new Date())));
            }, "thread" + i).start();
        }
        Thread.sleep(1000L);
        countDownLatch.countDown();
        Thread.sleep(1000L);
    }

    //循环栅栏CyclicBarrier，非公平模式
    @Test
    @DisplayName("拦截等待多个线程一起放行")
    public void cyclicBarrier() throws InterruptedException {
        //实例化循环栅栏，设置达到阻塞5个线程时开放屏障
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000L);
            int finalI = i;
            executorService.execute(() -> {
                try {
                    System.out.println(String.format("thread %s start", finalI));
                    //当达到5个线程执行await()方法时开放屏障执行后面的代码
                    cyclicBarrier.await();
                    System.out.println(String.format("thread %s end", finalI));
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            });
        }
        Thread.sleep(20000L);
        executorService.shutdown();
    }

    @Test
    @DisplayName("带优先执行线程")
    public void barrierAction() throws InterruptedException {

        //实例化循环栅栏，设置达到阻塞5个线程时开放屏障
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("----------线程优先执行---------");
        });
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000L);
            int finalI = i;
            executorService.execute(() -> {
                try {
                    System.out.println(String.format("thread %s start", finalI));
                    //当达到5个线程执行await()方法时开放屏障执行后面的代码，同时优先执行构造方法指定的线程
                    cyclicBarrier.await();
                    System.out.println(String.format("thread %s end", finalI));
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            });
        }
        Thread.sleep(20000L);
        executorService.shutdown();

    }


    //CompletableFuture
    @Test
    @DisplayName("常规实例化方法")
    public void newInstance() throws ExecutionException, InterruptedException {
        //通过无参构造方法
        CompletableFuture<String> completableFuture1 = new CompletableFuture<>();
        //给对象填充结果对象，此方法未执行异步或者同步线程
        completableFuture1.complete("hello");
        //获取结果对象,此时不会阻塞，因为没有执行异步或者同步线程，直接返回
        String hello = completableFuture1.get();
        System.out.println(hello);

        //静态方法,功能和上述相同
        CompletableFuture<String> completableFuture2 = CompletableFuture.completedFuture("completable");
        System.out.println(completableFuture2.get());

    }

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    @Test
    @DisplayName("没有返回值的异步执行")
    public void asyncNonRes() {
        //使用默认线程池或者新创建一个线程进行异步执行，不推荐
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> System.out.println("hello CompletableFuture sync"));
        //使用自定义线程池异步执行，推荐
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            System.out.println("hello CompletableFuture async");
        }, executor);

        future1.join(); //等待future1线程执行完毕
        future2.join(); //等待future2线程执行完毕
        //自定义线程池已完成的任务数量
        System.out.println(executor.getCompletedTaskCount());
    }

    @Test
    @DisplayName("有返回值的异步执行")
    public void asyncHasRes() throws ExecutionException, InterruptedException {
        //使用默认线程池或者新创建一个线程进行异步执行，不推荐
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "with res async");
        System.out.println(future1.get());
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "with res async use definition executor", executor);
        System.out.println(future2.get());
        future1.join(); //等待future1线程执行完毕
        future2.join(); //等待future2线程执行完毕
        //自定义线程池已完成的任务数量
        System.out.println(executor.getCompletedTaskCount());
    }

    @Test
    @DisplayName("thenApply()方法:上一个线程执行返回值传给下一个线程继续执行")
    public void thenApply() throws ExecutionException, InterruptedException {
        //将上一次执行的返回结果传给下一个执行的线程继续执行
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> "thenApply", executor) //这一步在自定义线程池执行
                .thenApply(s -> s + " sync");    //同步执行，这一步执行是在主线程main方法中执行
        System.out.println(future1.get());
        future1.join(); //等待future1线程执行完毕
        //自定义线程池已完成的任务数量
        System.out.println(executor.getCompletedTaskCount());//1
        //清空线程池
        executor.shutdown();
        executor =(ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        //thenApply异步执行

        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> "thenApply", executor)   //异步在executor线程池中执行
                //.thenApplyAsync(s -> s+"async");      //使用CompletableFuture默认异步线程方式进行异步执行，不推荐
                .thenApplyAsync(s -> s + " async", executor);   //使用自定义线程池异步执行，在executor线程池中执行，推荐
        future2.join();
        System.out.println(future2.get());
        System.out.println(executor.getCompletedTaskCount());//2

        //清空线程池
        executor.shutdown();
        executor =(ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        //流式调用
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "hello", executor)
                .thenApplyAsync(s -> s + " thenApply", executor)
                .thenApplyAsync(s -> s + " stream", executor);
        future3.join();
        System.out.println(future3.get());
        System.out.println(executor.getCompletedTaskCount());//3

    }

    @Test
    @DisplayName("thenAccept()方法：最终结果进行回调，可以获取返回值")
    public void thenAccept(){

        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> "hello", executor)   //executor线程池异步调用
                .thenApplyAsync(s -> s + " thenAccept", executor)  //executor线程池异步调用
                .thenApplyAsync(s -> s + " sync", executor)        //executor线程池异步调用
                //.thenAccept(s -> System.out.println(s)) 等同下一行
                .thenAccept(System.out::println);//同步回调，这一步在main主线程调用,获取前面的最终结果作为参数进行回调
        future1.join();
        System.out.println(executor.getCompletedTaskCount());//3

        //清空线程池
        executor.shutdown();
        executor =(ThreadPoolExecutor)Executors.newFixedThreadPool(10);

        CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> "hello", executor)   //executor线程池异步调用
                .thenApplyAsync(s -> s + " thenAccept", executor)  //executor线程池异步调用
                .thenApplyAsync(s -> s + " async", executor)        //executor线程池异步调用
                //.thenAcceptAsync(s -> System.out.println(s)) 等同下一行
                //.thenAcceptAsync(System.out::println);    //使用CompletableFuture默认异步线程进行异步回调，不推荐
                .thenAcceptAsync(System.out::println,executor);//异步回调，这一步在executor线程池调用,获取前面的最终结果作为参数进行回调，推荐
        future2.join();
        System.out.println(executor.getCompletedTaskCount());//4
    }

    @Test
    @DisplayName("thenRun()方法：最后的回调方法，不能获取前面的返回值")
    public void thenRun(){

        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> "hello", executor) //executor线程池异步调用
                .thenApplyAsync(s -> s + " thenRun", executor)   //executor线程池异步调用
                .thenApplyAsync(s -> s + " sync", executor)      //executor线程池异步调用
                .thenRun(() -> System.out.println("thenRun sync"));//同步最终调用，无法获取前面的返回值，这一步在main主线程执行
        future1.join();
        System.out.println(executor.getCompletedTaskCount());    //3
        //清空线程池
        executor.shutdown();
        executor =(ThreadPoolExecutor)Executors.newFixedThreadPool(10);

        CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> "hello", executor) //executor线程池异步调用
                .thenApplyAsync(s -> s + " thenRun", executor)   //executor线程池异步调用
                .thenApplyAsync(s -> s + " async", executor)      //executor线程池异步调用
                //.thenRunAsync(() -> System.out.println("thenRun async"));   //异步最终调用，使用CompletableFuture默认异步方式，不推荐
                .thenRunAsync(() -> System.out.println("thenRun async"),executor);//异步最终调用，无法获取前面的返回值，这一步在executor线程池执行，推荐
        future2.join();
        System.out.println(executor.getCompletedTaskCount()); //4

    }

    @Test
    @DisplayName("whenComplete()方法：完成时回调")
    public void whenComplete(){
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello", executor)
                .thenApplyAsync(s -> {
                    if(false){
                        throw new RuntimeException("运行时异常");
                    }
                    return s + " whenComplete async";
                }, executor)
                //.whenCompleteAsync((s, throwable) -> {});   //使用CompletableFuture默认方式异步调用，不推荐
                .whenCompleteAsync((s, throwable) -> {   //异步调用，使用自定义executor线程池，推荐
                    System.out.println("返回值："+s);
                    System.out.println("可能抛出的异常："+throwable);
                },executor);
        future1.join();
        System.out.println(executor.getCompletedTaskCount());    //3

        //清空线程池
        executor.shutdown();
        executor =(ThreadPoolExecutor)Executors.newFixedThreadPool(10);

        //该方法会把子线程异常抛出到主线程，造成主线程中断
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "hello", executor)
                .thenApplyAsync(s -> {
                    if(true){
                        throw new RuntimeException("运行时异常");
                    }
                    return s + " whenComplete sync";
                }, executor)
                .whenComplete((s, throwable) -> {   //同步调用
                    System.out.println("返回值："+s);
                    System.out.println("可能抛出的异常："+throwable);

                });
        future2.join();
        System.out.println(executor.getCompletedTaskCount());    //2
    }

    @Test
    @DisplayName("异常处理")
    public void completableFutureException() throws ExecutionException, InterruptedException {

        //以下方法不会把子线程异常抛出到主线程，主线程不会中断
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            if(true){
                throw new RuntimeException("运行时异常");
            }
            return "exception";
        }, executor).handleAsync((o, throwable) -> {    //异步调用，handle()同步调用；handleAsync(fn)默认方式异步，不推荐
            System.out.println("返回值："+o);
            System.out.println("异常值："+throwable);
            return o;
        },executor);
        System.out.println(future1.get());
        //exceptionally方法处理异常
        System.out.println("---------exceptionally方法处理异常-----------");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("运行时异常");
            }
            return "exception";
        }, executor).exceptionally(throwable -> {    //只接受异常
            System.out.println(throwable);
            return throwable.getMessage();
        });
        System.out.println(future2.get());

        System.out.println("------------给默认异常值进行实例化-------------");
        CompletableFuture future3=new CompletableFuture();
        boolean exceptionally = future3.completeExceptionally(new RuntimeException("运行时异常"));

        System.out.println(exceptionally);
        //获取异常结果对象将会把异常抛出到主线程，导致主线程中断
        System.out.println(future3.get());

    }

    @Test
    @DisplayName("组合CompletableFuture")
    public void groupCompletableFuture() throws ExecutionException, InterruptedException {
        //将前面CompletableFuture的返回值传给下一个CompletableFuture执行，有先后顺序
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello", executor)
                //.thenCompose(fn)  同步调用
                //.thenComposeAsync(fn)  默认方式异步调用，不推荐
                .thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> s + " thenCompose async", executor), executor);
        System.out.println(future1.get());

        //将前面和后面的CompletableFuture的返回值作为参数传给回调函数进行回调,前两个任务是并行处理，没有先后顺序
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "hello", executor)
                //.thenCombine(fn)  同步调用
                //.thenCombineAsync(fn)  默认方式异步调用，不推荐
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> " thenCombine async", executor), (s, s2) -> s + s2, executor);
        System.out.println(future2.get());

    }

    @Test
    @DisplayName("处理多个CompletableFuture任务")
    public void parallelCompletableFutures() throws ExecutionException, InterruptedException {
        //并行处理,无法获取各自的返回值
        CompletableFuture<Void> future1 = CompletableFuture.allOf(CompletableFuture.supplyAsync(() -> "hello"), CompletableFuture.supplyAsync(() -> " allOf"), CompletableFuture.supplyAsync(() -> " handle multi CompletableFutures"));
        future1.join();

        //并行处理，其中一个任务执行完即返回,可能是任意一个任务
        CompletableFuture<Object> future2 = CompletableFuture.anyOf(CompletableFuture.supplyAsync(() -> "hello"), CompletableFuture.supplyAsync(() -> " anyOf"), CompletableFuture.supplyAsync(() -> " handle multi CompletableFutures"));
        future2.join();
        System.out.println(future2.get());
    }

    //Reentrantlock
    @Test
    @DisplayName("非公平锁Reentrantlock")
    public void unFairReentrantlock() throws InterruptedException {
        //默认实例化非公平锁
        ReentrantLock reentrantLock=new ReentrantLock();
        int count=20;
        CountDownLatch countDownLatch=new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<count;i++){
            int finalI = i;
            Thread.sleep(1L);
            executorService.execute(() -> {
                try {
                    reentrantLock.lock();
                    Thread.sleep(100L);
                    System.out.println(String.format("thread%s", finalI));
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
            });
        }
        countDownLatch.await();

    }

    @Test
    @DisplayName("公平锁Reentrantlock")
    public void fairReentrantlock() throws InterruptedException {
        //实例化公平锁
        ReentrantLock reentrantLock=new ReentrantLock(true);
        int count=20;
        CountDownLatch countDownLatch=new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<count;i++){
            int finalI = i;
            Thread.sleep(1L);
            executorService.execute(() -> {
                try {
                    reentrantLock.lock();
                    Thread.sleep(10L);
                    System.out.println(String.format("thread%s", finalI));
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }
            });
        }
        countDownLatch.await();

    }

    @Test
    @DisplayName("可重入锁")
    public void repeatedReentrantLock() throws InterruptedException {
        //实例化锁
        ReentrantLock reentrantLock=new ReentrantLock();
        int count=20;
        CountDownLatch countDownLatch=new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            for(int i=0;i<count;i++){
                reentrantLock.lock();
                System.out.println(i);
            }
            for(int i=count;i>0;i--){
                try {
                    System.out.println(i);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
            countDownLatch.countDown();
        });
        countDownLatch.await();
    }


    //默认非公平锁
    ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    @Test
    @DisplayName("读写锁")
    public void ReentrantReadWriteLock() throws InterruptedException {

        //读读不互斥，读写、写写互斥
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int count=50;
        CountDownLatch countDownLatch=new CountDownLatch(count);
        for(int i=0;i<count;i++){
            int finalI = i;
            executorService.execute(() -> {
                if (finalI % 2 != 0) {
                    get();
                } else {
                    put();
                }
                countDownLatch.countDown();

            });
        }
        countDownLatch.await();

    }

    private void get(){
        try {
            readWriteLock.readLock().lock();
            System.out.println("read ready");
            Thread.sleep(1000L);
            System.out.println("read end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private void put(){
        try {
            readWriteLock.writeLock().lock();
            System.out.println("write ready");
           Thread.sleep(1000L);
            System.out.println("write end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
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

    private AtomicInteger atomicInteger = new AtomicInteger(10);

    @Test
    @DisplayName("原子类Integer cas修改")
    public void atomicIntegerCasUpdate() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            int i = atomicInteger.get();
            System.out.println("thread1初始值：" + i);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicInteger.compareAndSet(i, 20);
            System.out.println("thread1:" + atomicInteger.get() + ",result:" + result);
            countDownLatch.countDown();

        }, "thread1").start();

        new Thread(() -> {
            int i = atomicInteger.get();
            System.out.println("thread2初始值：" + i);
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //由于线程1先把共享变量atomicInteger修改为20，因此线程2获取的初始值与内存值不同，修改失败
            boolean result = atomicInteger.compareAndSet(i, 30);
            System.out.println("thread2:" + atomicInteger.get() + ",result:" + result);
            countDownLatch.countDown();

        }, "thread2").start();
        countDownLatch.await();
    }

    @Test
    @DisplayName("原子类Integer cas新增")
    public void atomicIntegerCasAdd() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("thread1初始值：" + atomicInteger.get());
            //根据JMM内存模型，线程1先拷贝一份共享变量atomicInteger的初始值为10，然后对比atomicInteger内存的值，相等则新增10并更新内存的值为20
            int i = atomicInteger.addAndGet(10);
            System.out.println("thread1新增10之后value=" + i);
            countDownLatch.countDown();
        }, "thread1").start();

        new Thread(() -> {
            System.out.println("thread2初始值：" + atomicInteger.get());
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
            System.out.println("thread2新增10之后value=" + i);
            countDownLatch.countDown();
        }, "thread2").start();
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

package com.kim.common;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author kim
 * @Since 2021/4/21
 * 线程、多线程测试
 */
public class ThreadTest {

    /**
     * 同步代码块写法
     * */
    @Test
    public void testSync(){

        int count=0;
        for (int i=0;i<4;i++){
            synchronized (""){
                count++;
            }
        }
        System.out.println(count);
    }

    /**
     * 线程池业务接口使用
     * */
    @Test
    @DisplayName("不推荐此方式创建线程池，因为队列大小为Integer.Max_Value,太大会堆积太多请求")
    public void ExecutorService() throws ExecutionException, InterruptedException {
        //实例一个固定大小的线程池
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        //使用Runnable提交匿名线程，不能接收到返回值
        executorService.execute(() -> {
            System.out.println("执行匿名线程");
        });
        //使用Callable接口提交匿名线程，可以接收返回值
        Future<Integer> submit = executorService.submit(() -> 10);
        System.out.println("返回值:"+submit.get());
    }

    /**
     * google.guava限流
     * */
    @Test
    public void rateLimiter(){
        //开启一个每秒发放10个令牌的限流器，每秒指的是整秒，下一秒的话重新清零计算。
        RateLimiter rateLimiter= RateLimiter.create(10);
        ExecutorService executorService= Executors.newFixedThreadPool(100);
        ThreadPoolExecutor executor=(ThreadPoolExecutor)executorService;
        for(int i=0;i<50;i++){
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //得到令牌许可，参数传入permits代表每秒允许的请求数中每获得一个许可需要等待的permits*100毫秒数
                    rateLimiter.acquire();
                    System.out.println("Accessing:,"+ finalI +":"
                            + new SimpleDateFormat("yy-MM-dd HH:mm:ss:SSS").format(new Date()));
                }
            });
        }
        while(true){
            if(executor.getCompletedTaskCount()==50){
                break;
            }
        }
    }

    /**
     * 信号量测试
     * */
    private Semaphore seamphore;

    @Test
    public void semaphoreTest(){
        seamphore=new Semaphore(5);  //实例化一个信号量,每次只有5个线程同时执行
        ExecutorService executorService= Executors.newFixedThreadPool(100);
        List<Future<?>> futures =new ArrayList<>();
        for(int i=0;i<50;i++){
            int finalI = i;
            Future<?> submit = executorService.submit(() -> {
                try {
                    seamphore.acquire();  //打开信号灯
                    System.out.println(finalI); //执行线程任务
                    Thread.sleep(3000);
                    seamphore.release();  //释放限流开关
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            futures.add(submit);
        }
        ThreadPoolExecutor executor=(ThreadPoolExecutor)executorService;
        while(true){
            if(executor.getCompletedTaskCount()==50){
                break;
            }
        }
    }

    @Test
    @DisplayName("线程池测试，推荐使用构造方法实例化线程池")
    public void testThreadPool() throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(10,30,5,
                TimeUnit.SECONDS,new LinkedBlockingDeque<>(10));   //创建一个线程池

        for (int i=0;i<31;i++){
            MyTask myTask=new MyTask(i); //创建一个线程任务
            threadPoolExecutor.execute(myTask);    //将这个任务加入线程池
            System.out.println("线程池的数目大小："+threadPoolExecutor.getPoolSize()+",队列中正在等待的缓存任务数目"+
                    threadPoolExecutor.getQueue().size()+",已执行完的线程数量："+threadPoolExecutor.getCompletedTaskCount());
        }

        Thread.sleep(4000);
        System.out.println("4秒后，线程池任务已全部执行完，线程池的数目大小："+threadPoolExecutor.getPoolSize()+",队列中正在等待的缓存任务数目"+
                threadPoolExecutor.getQueue().size()+",已执行完的线程数量："+threadPoolExecutor.getCompletedTaskCount());

        Thread.sleep(5000);
        System.out.println("9秒后，线程池的数目大小："+threadPoolExecutor.getPoolSize()+",队列中正在等待的缓存任务数目"+
                threadPoolExecutor.getQueue().size()+",已执行完的线程数量："+threadPoolExecutor.getCompletedTaskCount());

        threadPoolExecutor.shutdown();  //销毁线程池

        MyTask myTask=new MyTask(709394); //创建一个线程任务
        threadPoolExecutor.execute(myTask);

        System.out.println("销毁线程池后：线程池的数目大小："+threadPoolExecutor.getPoolSize()+",队列中正在等待的缓存任务数目"+
                threadPoolExecutor.getQueue().size()+",已执行完的线程数量："+threadPoolExecutor.getCompletedTaskCount());
    }

    public static class MyTask implements Runnable{

        private Integer i;

        public MyTask(Integer i){

        }
        @Override
        public void run() {
            System.out.println(i);
        }
    }

}

package com.kim.common;

import com.google.common.util.concurrent.RateLimiter;
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
    public void ExecutorService() throws ExecutionException, InterruptedException {
        //实例一个固定大小的线程池
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        //提交匿名线程，不能接收到返回值
        Future<?> future=executorService.submit(() -> {
            System.out.println("执行匿名线程");
        });
        //使用Callable接口提交匿名线程，可以接收返回值
        Future<Object> submit = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });
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

}

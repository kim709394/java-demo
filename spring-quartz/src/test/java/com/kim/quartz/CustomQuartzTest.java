package com.kim.quartz;

import com.kim.quartz.intervaljob.ExampleJobBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kim
 * @description  灵活自定义定时任务
 * @date 2021-5-3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuartzApplication.class)
public class CustomQuartzTest {

    //注入定时任务管理工厂，该工厂可以随时管理定时任务，并且已交由spring管理
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 服务启动自动配置定时任务的方式，适合简单的业务，这种方式不能在中途对定时
     * 任务进行取消和变更，有时项目中会有中途需要变更定时任务的业务场景，因此下面
     * 这种方式可以随时取消旧定时任务，开启新的定时任务，从而实现灵活变更定时任务
     * */
    @Test
    public void customTest() throws Exception {
        /**
         * 根据定时任务的名字删除指定定时任务，定时任务在没有特殊设置时是放在默认的组中
         * 本质是将正在执行的任务停下来，但任务并未删除
         */

        JobKey jobKey=new JobKey("myTask", Scheduler.DEFAULT_GROUP);
        schedulerFactoryBean.getScheduler().deleteJob(jobKey);

        CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule("0/1 * * * * ? ");//一秒执行一次
        /**
         * 定义新的定时任务执行bean,如果只是改变定时执行频率，那么可以不用重新定义任务执行bean
         * 但是重新绑定任务执行bean的名称要和本来一致。
         * */
        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("key","value");
        JobDetail myTask = JobBuilder.newJob(ExampleJobBean.class).withIdentity("myNewTask")  //指定定时任务名称
                .storeDurably().setJobData(jobDataMap) //设置一些状态参数，可以在执行任务方法里面通过获取到，也可以不设置
                .build();
        Trigger trigger= TriggerBuilder
                .newTrigger()
                .forJob("myNewTask")
                .withIdentity("myNewTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
        schedulerFactoryBean.setJobDetails(myTask);  //重新设置定时任务执行bean
        schedulerFactoryBean.setTriggers(trigger);
        schedulerFactoryBean.afterPropertiesSet();
        //开启新的定时任务
        schedulerFactoryBean.start();
    }



}

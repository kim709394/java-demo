package com.kim.quartz.config;

import com.kim.quartz.intervaljob.ExampleJobBean;
import org.quartz.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author huangjie
 * @description 定时任务配置
 * @date 2021-5-3
 */
/**
 * 执行流程:
 * JobDetail配置定时任务，绑定一个实现了org.springframework.scheduling.quartz.QuartzJobBean的类
 * 并且实现protected void executeInternal(JobExecutionContext context) throws JobExecutionException方法
 * 定时任务在该方法中进行执行
 * 还可以通过JobDataMap对象绑定一些自定义参数
 * Trigger配置触发器，绑定cron表达式构建器，实现定时执行
 * */
@SpringBootConfiguration
public class QuartzConfig {

    /**
     * 配置要执行的任务，传入任务类，给任务指定唯一标识
     */
    @Bean
    public JobDetail identifyScanJob(){
        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("key","value");
        return JobBuilder
                .newJob(ExampleJobBean.class)
                .withIdentity("myTask")  //指定定时任务名称
                .storeDurably()
                .setJobData(jobDataMap) //设置一些状态参数，可以在执行任务方法里面通过获取到，也可以不设置
                .build();
    }


    /**
     * 配置触发器
     */
    @Bean
    public Trigger myTaskTrigger(){
        //cron表达式构建器，指定定时频率
        CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule("0/3 * * * * ? ");//每三秒执行一次
        return TriggerBuilder
                .newTrigger()
                .forJob("myTask")  //指定定时任务名称，与上面的定时任务配置要一致
                .withIdentity("myTaskTrigger")  //指定触发器唯一标识
                .withSchedule(cronScheduleBuilder)  //封装定时频率构建器
                .build();
    }

}

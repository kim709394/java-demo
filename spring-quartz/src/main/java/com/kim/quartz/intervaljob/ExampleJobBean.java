package com.kim.quartz.intervaljob;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author huangjie
 * @description
 * @date 2021-5-3
 */
/**
 * 不并发运行：上一次任务未执行完时，当次任务延时执行，等待上一次任务执行完毕再执行。
 * 要并发执行，则不加此注解
 * */
@DisallowConcurrentExecution
public class ExampleJobBean extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        System.out.println("状态参数:"+jobDataMap.get("key"));
        System.out.println("执行定时任务");
    }
}

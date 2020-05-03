package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author You
 * @create 2020-05-04 1:34
 */
public class QuartzScheduleTest {

    @Test
    public void test() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(DemoJob.class)
                .withIdentity("task1", "group1")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);

        Thread.currentThread().join();
    }


}

package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-05-04 14:27
 */
public class ScheduleExecutorServiceTest {

    @Test
    public void testRunnable() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(3);
        ScheduledFuture<?> future = scheduledThreadPoolExecutor.schedule(() -> System.out.println("i am work"), 2, TimeUnit.SECONDS);
        boolean b = scheduledThreadPoolExecutor.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println(b);
    }

    @Test
    public void testCallable() throws InterruptedException, ExecutionException {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(3);
        ScheduledFuture<Integer> future = scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("i am work");
            return 10;
        }, 2, TimeUnit.SECONDS);
        System.out.println(future.get());
    }

    @Test
    public void testScheduleAtFixedRate() throws InterruptedException {
        // 和 TimerTask 一样，依旧要等上一个执行完才会执行下一次，尽管到达执行时间
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(3);
        // default = false  true 表示 shutdown 后继续执行周期任务
        scheduledThreadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + "-" + LocalTime.now());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 2, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.shutdown();
        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void testScheduleWithFixedDelay() throws InterruptedException {
        // 在上一个执行完之后 delay 延时 2s 才会执行下一个
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(3);
        // defaut = true  并未发现有什么改变  false shutdown 均无执行
        System.out.println(scheduledThreadPoolExecutor.getExecuteExistingDelayedTasksAfterShutdownPolicy());
        scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread().getName() + "-" + LocalTime.now());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 2, TimeUnit.SECONDS);
//        scheduledThreadPoolExecutor.shutdown();
        TimeUnit.SECONDS.sleep(30);
    }

}

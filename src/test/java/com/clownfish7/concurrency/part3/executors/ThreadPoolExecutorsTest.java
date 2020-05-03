package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-05-03 23:18
 */
public class ThreadPoolExecutorsTest {
    /**
     * test
     * 1. corePoolSize=1, maximumPoolSize=2, BlockingQueue size = 1  what happen when submit 3 task?
     * 2. corePoolSize=1, maximumPoolSize=2, BlockingQueue size = 5  what happen when submit 7 task?
     * 3. corePoolSize=1, maximumPoolSize=2, BlockingQueue size = 5  what happen when submit 8 task?
     * <p>
     * int corePoolSize,
     * int maximumPoolSize,
     * long keepAliveTime,
     * TimeUnit unit,
     * BlockingQueue<Runnable> workQueue,
     * ThreadFactory threadFactory,
     * RejectedExecutionHandler handler
     * <p>
     * shutdown();
     * 1. 将线程池状态置为SHUTDOWN,并不会立即停止：
     * 2. 停止接收外部submit的任务
     * 3. 内部正在跑的任务和队列里等待的任务，会执行完
     * 4. 等到第二步完成后，才真正停止
     * 5. 非阻塞
     * <p>
     * shutdownNow();
     * 将线程池状态置为STOP。企图立即停止，事实上不一定：
     * 跟shutdown()一样，先停止接收外部提交的任务
     * 忽略队列里等待的任务
     * 尝试将正在跑的任务interrupt中断
     * 返回未执行的任务列表
     * 它试图终止线程的方法是通过调用Thread.interrupt()方法来实现的，但是大家知道，这种方法的作用有限，如果线程中没有sleep 、wait、Condition、定时锁等应用, interrupt()方法是无法中断当前的线程的。所以，ShutdownNow()并不代表线程池就一定立即就能退出，它也可能必须要等待所有正在执行的任务都执行完成了才能退出。
     * 但是大多数时候是能立即退出的
     * <p>
     * awaitTermination();
     * 接收 timeout 和 TimeUnit 两个参数，用于设定超时时间及单位。
     * 当等待超过设定时间时，会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。
     * 一般情况下会和shutdown方法组合使用。 可以阻塞
     *
     */
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            2,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1),
            r -> {
                Thread thread = new Thread(r);
                return thread;
            },
            // 拒绝策略
            // java.util.concurrent.RejectedExecutionException:
            // Task java.util.concurrent.FutureTask@1b68ddbd rejected from
            // java.util.concurrent.ThreadPoolExecutor@646d64ab[Running, pool size = 2, active threads = 2, queued tasks = 1, completed tasks = 0]
            new ThreadPoolExecutor.AbortPolicy()
    );

    Runnable r = () -> {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    @Test
    public void testBuildThreadPoolExecutors() throws InterruptedException {
        Thread t1 = new Thread(this::output);
        t1.start();
        Thread.sleep(500);
        threadPoolExecutor.submit(r);
        threadPoolExecutor.submit(r);
        threadPoolExecutor.submit(r);

        t1.join();

        threadPoolExecutor.shutdown();
    }

    private void output() {
        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != threadPoolExecutor.getActiveCount() || queueSize != threadPoolExecutor.getQueue().size()) {
                activeCount = threadPoolExecutor.getActiveCount();
                queueSize = threadPoolExecutor.getQueue().size();
                System.out.println(" activeCount: " + threadPoolExecutor.getActiveCount());
                System.out.println("corePoolSize: " + threadPoolExecutor.getCorePoolSize());
                System.out.println(" maxPoolSize: " + threadPoolExecutor.getMaximumPoolSize());
                System.out.println("   QueueSize: " + threadPoolExecutor.getQueue().size());
                System.out.println("---------------------------");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

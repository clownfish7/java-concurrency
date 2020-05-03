package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-04 0:31
 */
public class ExecutorsTest {

    @Test
    public void testCachedThreadPool() throws InterruptedException {
        /**
         * These pools will typically improve the performance
         * of programs that execute many short-lived asynchronous tasks.
         *
         * new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
         *
         * queue放一个拿一个，运行完任务会自动销毁线程池 core=0
         * 因为maximumPoolSize是无界的，所以提交任务的速度 > 线程池中线程处理任务的速度就要不断创建新线程；
         * 每次提交任务，都会立即有线程去处理，因此CachedThreadPool适用于处理大量、耗时少的任务。
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        System.out.println(((ThreadPoolExecutor) cachedThreadPool).getActiveCount());
        IntStream.range(0, 100).forEach(i ->
                cachedThreadPool.execute(() -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " - finished!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
        System.out.println(((ThreadPoolExecutor) cachedThreadPool).getActiveCount());
        cachedThreadPool.awaitTermination(20, TimeUnit.SECONDS);
    }


    @Test
    public void testFixedThreadPool() throws InterruptedException {
        /**
         * new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
         * 不会回收线程 core = max
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        System.out.println(((ThreadPoolExecutor) fixedThreadPool).getActiveCount());
        IntStream.range(0, 100).forEach(i ->
                fixedThreadPool.execute(() -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " - finished!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
        System.out.println(((ThreadPoolExecutor) fixedThreadPool).getActiveCount());
        fixedThreadPool.awaitTermination(20, TimeUnit.SECONDS);
    }

    @Test
    public void testSingleThread() {
        /**
         * 和 new Thread 的区别
         * 不会被回收，可以服用 core = max = 1
         * 可以将 task 丢到 queue
         * new FinalizableDelegatedExecutorService
         *             (new ThreadPoolExecutor(1, 1,
         *                                     0L, TimeUnit.MILLISECONDS,
         *                                     new LinkedBlockingQueue<Runnable>()));
         */
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    }

    @Test
    public void test() throws InterruptedException {
        /**
         * win + r -> dxdiag -> directx
         */
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService workStealingPool = Executors.newWorkStealingPool();
        List<Callable<String>> callableList = IntStream.range(0, 100).boxed()
                .map(i -> (Callable<String>) () -> {
                    System.out.println(Thread.currentThread().getName() + " doing");
                    Thread.sleep(1000);
                    return "Task" + i;
                })
                .collect(Collectors.toList());
        List<Future<String>> futureList = workStealingPool.invokeAll(callableList);
        futureList.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

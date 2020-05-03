package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-04 1:50
 */
public class ExecutorServiceTest {

    @Test
    public void testIsTerminating() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        fixedThreadPool.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(fixedThreadPool.isShutdown());
        fixedThreadPool.shutdown();
        System.out.println(fixedThreadPool.isShutdown());
        System.out.println(fixedThreadPool.isTerminated());
        System.out.println(((ThreadPoolExecutor) fixedThreadPool).isTerminating());
    }

    @Test
    public void testPrestartAllCoreThreads() {
        // 预启动线程
        ThreadPoolExecutor fixed = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        System.out.println(fixed.getActiveCount());
        System.out.println(fixed.prestartAllCoreThreads());
        System.out.println(fixed.getActiveCount());
        System.out.println(fixed.getPoolSize());
    }

    @Test
    public void testAllowCoreThreadTimeOut() throws InterruptedException {
        ThreadPoolExecutor fixed = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        fixed.setKeepAliveTime(5, TimeUnit.SECONDS);
        fixed.allowCoreThreadTimeOut(true);
        System.out.println(fixed.allowsCoreThreadTimeOut());
        System.out.println(fixed.getActiveCount());
        System.out.println(fixed.prestartAllCoreThreads());
        System.out.println(fixed.getActiveCount());

        TimeUnit.SECONDS.sleep(10);
        System.out.println(fixed.getActiveCount());
        System.out.println(fixed.isTerminated());
    }

    @Test
    public void testRemove() {
        ThreadPoolExecutor fixed = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        Runnable runnable = () -> {
            System.out.println(22);
        };
        fixed.execute(runnable);
        boolean remove = fixed.remove(runnable);
        System.out.println(remove);
    }

    @Test
    public void testInvokeAny() throws ExecutionException, InterruptedException {
        /**
         * 该方法是block的，返回第一个完成的任务，其他任务不再继续执行
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        List<Callable<String>> callableList = IntStream.range(0, 5).boxed().map(i -> (Callable<String>) () -> {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
            System.out.println(Thread.currentThread().getName() + " doing ");
            return "Task-" + i;
        }).collect(Collectors.toList());
        String s = fixedThreadPool.invokeAny(callableList);
        System.out.println("--- finished! ---");
        System.out.println(s);
        TimeUnit.SECONDS.sleep(20);
    }

    @Test
    public void testInvokeAnyTimeOut() throws InterruptedException {
        /**
         * 超时抛出java.util.concurrent.TimeoutException
         * 其他 task 取消，不再执行
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        List<Callable<String>> callableList = IntStream.range(0, 5).boxed().map(i -> (Callable<String>) () -> {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
            System.out.println(Thread.currentThread().getName() + " doing ");
            return "Task-" + i;
        }).collect(Collectors.toList());
        String s = null;
        try {
            s = fixedThreadPool.invokeAny(callableList, 1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("--- finished! ---");
        System.out.println(s);
        TimeUnit.SECONDS.sleep(20);
    }

    @Test
    public void testInvokeAll() throws ExecutionException, InterruptedException {
        /**
         * 该方法是block的
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        List<Callable<String>> callableList = IntStream.range(0, 5).boxed().map(i -> (Callable<String>) () -> {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
            System.out.println(Thread.currentThread().getName() + " doing ");
            return "Task-" + i;
        }).collect(Collectors.toList());
        List<Future<String>> futures = fixedThreadPool.invokeAll(callableList);
        System.out.println(" not blocking ");
        futures.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println("--- finished! ---");
    }

    @Test
    public void testInvokeAllTimeOut() throws ExecutionException, InterruptedException, TimeoutException {
        /**
         * 超时抛出java.util.concurrent.CancellationException
         * 不再执行
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        List<Callable<String>> callableList = IntStream.range(0, 5).boxed().map(i -> (Callable<String>) () -> {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
            System.out.println(Thread.currentThread().getName() + " doing ");
            return "Task-" + i;
        }).collect(Collectors.toList());
        List<Future<String>> futures = fixedThreadPool.invokeAll(callableList, 5, TimeUnit.SECONDS);
        System.out.println(" not blocking ");
        futures.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println("--- finished! ---");
        TimeUnit.SECONDS.sleep(20);
    }

    @Test
    public void testSubmit() throws ExecutionException, InterruptedException {
        /**
         * not block
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        Future<?> submit = fixedThreadPool.submit(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " doing ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 使阻塞住
        submit.get();
        System.out.println("--- finished! ---");

        Future<String> result = fixedThreadPool.submit(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " doing ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "ok");
        System.out.println(result.get());
    }

    @Test
    public void testGetTimeOut() throws InterruptedException {
        /**
         * 超时后任务继续会执行
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        Future<String> future = fixedThreadPool.submit(() -> {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " doing ");
            return "ok";
        });
        String s = null;
        try {
            s = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        TimeUnit.SECONDS.sleep(5);
    }
}

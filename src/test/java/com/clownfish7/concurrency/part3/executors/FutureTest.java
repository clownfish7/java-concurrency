package com.clownfish7.concurrency.part3.executors;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author You
 * @create 2020-05-04 5:08
 */
public class FutureTest {

    @Test
    public void testIsDown() throws InterruptedException, ExecutionException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        Future<Integer> future = fixedThreadPool.submit(() -> {
            TimeUnit.SECONDS.sleep(2);
            return 10;
        });
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
        System.out.println(future.get());
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void testCancel() throws InterruptedException {
        /**
         * Future.cancel(true)适用于：
         * 1. 长时间处于运行的任务，并且能够处理interruption
         *
         * Future.cancel(false)适用于：
         * 1. 未能处理interruption的任务
         * 2. 不清楚任务是否支持取消
         * 3. 需要等待已经开始的任务执行完成
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        Future<Integer> future = fixedThreadPool.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("something");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            while (!Thread.currentThread().isInterrupted()) {
//            }
            while (atomicBoolean.get()) {
            }
            System.out.println("lalala");
            return 10;
        });
        TimeUnit.MILLISECONDS.sleep(800);
        System.out.println(future.cancel(true));
        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
        TimeUnit.SECONDS.sleep(10);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        Future<Integer> future = fixedThreadPool.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//                System.out.println("something");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            while (!Thread.currentThread().isInterrupted()) {
//            }
            while (atomicBoolean.get()) {
            }
            System.out.println("lalala");
            return 10;
        });
        TimeUnit.MILLISECONDS.sleep(800);
        System.out.println(future.cancel(true));
        System.out.println(future.cancel(true));
        System.out.println(future.isDone());
        System.out.println(future.isCancelled());
    }
}

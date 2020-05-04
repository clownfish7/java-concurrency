package com.clownfish7.concurrency.part3.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-05-04 15:42
 */
public class CompletableTest {

    @Test
    public void test() throws InterruptedException {
        CompletableFuture.runAsync(() -> System.out.println("ok"))
                .whenCompleteAsync((v, t) -> System.out.println("finish"));
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test2() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "hello";
        }).whenComplete((v, t) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("ok");
        });
        System.out.println(Thread.currentThread().getName());
        System.out.println("block");
        System.out.println("-------d");
        future.join();
        System.out.println("-------d");
        future.whenComplete((v, t) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("ok");
        });
    }

    @Test
    public void testException() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                throw new RuntimeException("err");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1";
        }).exceptionally(Throwable::getMessage).thenAccept(System.out::println);
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void testGetNow() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "123";
        }).whenComplete((v, t) -> System.out.println(v));
        String haha = future.getNow("haha");
        System.out.println(haha);
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void testComplate() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "123";
        });
//        Thread.sleep(2000);
        boolean hahaha = future.complete("hahaha");
        System.out.println(hahaha);
        System.out.println(future.get());
    }

    @Test
    public void testJoin() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "123";
        });
        System.out.println(future.join());
    }

    @Test
    public void testCompleteExceptionally() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("finish");
            return "123";
        });
        Thread.sleep(3000);
        boolean err = future.completeExceptionally(new RuntimeException("err"));
        System.out.println(err);
        System.out.println(future.join());
    }

    @Test
    public void testObtrudeException() throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("1");
                Thread.sleep(3000);
                System.out.println("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "wad";
        });
        Thread.sleep(1000);
        future.obtrudeException(new RuntimeException("ob"));
//        System.out.println(future.join());
        Thread.sleep(5000);
    }
}

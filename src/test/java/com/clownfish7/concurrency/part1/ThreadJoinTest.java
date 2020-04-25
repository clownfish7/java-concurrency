package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-04-25 16:35
 */
public class ThreadJoinTest {

    @Test
    public void testJoin() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().isDaemon());
        Thread thread = new Thread(() -> {
            int i = 0;
            while (i++ <= 5) {
                System.out.println("--" + Thread.currentThread().getName());
                System.out.println("--" + Thread.currentThread().isDaemon());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        // 等待该 thread 执行完 param1 毫秒 param2 纳秒 default 0 一直等待
        // Thread.currentThread().join(); 自己等自己死亡
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
        thread.join();
    }
}

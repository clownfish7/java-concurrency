package com.clownfish7.concurrency.part3.countdown;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-03 15:05
 */
public class CountDownLatchTest {

    @Test
    public void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + ":finished!");
                countDownLatch.countDown();
            }).start();
        });
        countDownLatch.await();
    }
}

package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author You
 * @create 2020-04-25 17:05
 */
public class ThreadInterruptTest {

    @Test
    public void testInterrupt() {
        Thread thread = new Thread(() -> {
            Optional.of(Thread.currentThread().getName()).ifPresent(System.out::println);
            Optional.of(Thread.currentThread().isInterrupted()).ifPresent(System.out::println);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.interrupt();
    }
}

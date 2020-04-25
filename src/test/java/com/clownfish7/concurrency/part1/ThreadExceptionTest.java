package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author You
 * @create 2020-04-25 22:36
 */
public class ThreadExceptionTest {

    @Test
    public void test() {
        Thread thread = new Thread(() -> {
            int a = 1 / 0;
        });
        thread.start();
        thread.setUncaughtExceptionHandler((t, e) -> {
            Optional.of(t).ifPresent(System.out::println);
            Optional.of(e).ifPresent(System.out::println);
        });
    }
}

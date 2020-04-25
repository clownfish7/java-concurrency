package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author You
 * @create 2020-04-25 22:39
 */
public class StackTraceTest {

    @Test
    public void test() {
        Arrays.stream(Thread.currentThread().getStackTrace())
                .filter(e -> !e.isNativeMethod())
                .forEach(System.out::println);
    }
}

package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-25 22:26
 */
public class HookTest {

    @Test
    public void test() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::notifyAndRelease));
    }

    private void notifyAndRelease() {
        int i = 5;
        while (i-- >= 0) {
            System.out.println("xixi");
        }
    }
}

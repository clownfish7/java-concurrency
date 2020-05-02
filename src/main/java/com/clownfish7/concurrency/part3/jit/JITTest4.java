package com.clownfish7.concurrency.part3.jit;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author You
 * @create 2020-05-02 19:28
 */
public class JITTest4 {

    private static AtomicBoolean init = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (init.get()) {

            }
        }).start();

        Thread.sleep(1000L);

        new Thread(() -> {
            init.set(false);
            System.out.println("change init = false");
        }).start();

        // 会停止
    }
}

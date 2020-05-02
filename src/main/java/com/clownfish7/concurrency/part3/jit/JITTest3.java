package com.clownfish7.concurrency.part3.jit;

/**
 * @author You
 * @create 2020-05-02 19:28
 */
public class JITTest3 {

    private static volatile boolean init = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (init) {

            }
        }).start();

        Thread.sleep(1000L);

        new Thread(() -> {
            init = false;
            System.out.println("change init = false");
        }).start();

        // 会停止
    }
}

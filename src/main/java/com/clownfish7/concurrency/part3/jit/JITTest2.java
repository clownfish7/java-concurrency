package com.clownfish7.concurrency.part3.jit;

/**
 * @author You
 * @create 2020-05-02 19:28
 */
public class JITTest2 {

    private static boolean init = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (init) {
                System.out.println();
            }
        }).start();

        Thread.sleep(1000L);

        new Thread(() -> {
            init = false;
            System.out.println("change init = false");
        }).start();

        // 会停止
        // JIT 作祟
        // while (init) 里没有一句话 会等价于 while（true）
        // while (init)    有一句话 会等价于 while（init）
    }
}

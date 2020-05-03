package com.clownfish7.concurrency.part3.exchanger;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;

/**
 * @author You
 * @create 2020-05-03 16:54
 */
public class ExchangerTest {

    /**
     * 拿到是同一个对象 会有安全问题
     * 必须成对使用,没拿到值会阻塞
     */

    @Test
    public void test() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread t1 = new Thread(() -> {
            try {
                String result = exchanger.exchange("haha");
                System.out.println(Thread.currentThread().getName() + ":" + result);
                result = exchanger.exchange("xixi");
                System.out.println(Thread.currentThread().getName() + ":" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                String result = exchanger.exchange("xixi");
                System.out.println(Thread.currentThread().getName() + ":" + result);
                result = exchanger.exchange("haha");
                System.out.println(Thread.currentThread().getName() + ":" + result);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

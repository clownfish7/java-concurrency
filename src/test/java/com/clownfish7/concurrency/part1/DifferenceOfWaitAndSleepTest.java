package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

/**
 * @author You
 * @create 2020-04-25 21:03
 */
public class DifferenceOfWaitAndSleepTest {

    /**
     * 1. Object.wait() - Thread.sleep()
     * 2. wait需要monitor sleep不需要
     * 3. wait释放锁 sleep不释放
     * 4. wait需要被唤醒 sleep不需要
     */

    private final static Object LOCK = new Object();

    @Test
    public void test() {
        Stream.of("T1", "T2").forEach(name ->
                new Thread(name) {
                    @Override
                    public void run() {
                        m2();
                    }
                }.start()
        );
    }

    public static void m1() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m2() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

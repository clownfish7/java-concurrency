package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-25 18:02
 */
public class SynchronizedThisTest {

    @Test
    public void test() throws InterruptedException {
        ThisLock thisLock = new ThisLock();
        Thread t1 = new Thread("T1") {
            @Override
            public void run() {
                thisLock.m1();
            }
        };
        t1.start();

        Thread t2 = new Thread("T2") {
            @Override
            public void run() {
                thisLock.m2();
            }
        };
        t2.start();

        t1.join();
        t2.join();
    }

    class ThisLock {

        private final Object LOCK = new Object();

        public void m1() {
            synchronized (LOCK) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void m2() {
            synchronized (LOCK) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

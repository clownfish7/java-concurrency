package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-25 18:04
 */
public class SychronizedStaticTest {

    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread("T1") {
            @Override
            public void run() {
                SychronizedStatic.m1();
            }
        };
        t1.start();

        Thread t2 = new Thread("T2") {
            @Override
            public void run() {
                SychronizedStatic.m2();
            }
        };
        t2.start();

        Thread t3 = new Thread("T3") {
            @Override
            public void run() {
                SychronizedStatic.m3();
            }
        };
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

}

class SychronizedStatic {

    static {
        synchronized (SychronizedStatic.class) {
            try {
                System.out.println("static " + Thread.currentThread().getName());
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static void m1() {
        System.out.println("m1 " + Thread.currentThread().getName());
        try {
            Thread.sleep(10_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void m2() {
        System.out.println("m2 " + Thread.currentThread().getName());
        try {
            Thread.sleep(10_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m3() {
        System.out.println("m3 " + Thread.currentThread().getName());
        try {
            Thread.sleep(10_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
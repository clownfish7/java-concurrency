package com.clownfish7.concurrency.part3.atomic;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author You
 * @create 2020-05-02 19:01
 */
public class AtomicTest {
    /**
     * 1. 可见性
     * 2. 顺序性
     * 3. no原子性
     */
    private static volatile int value = 0;

    public static void main(String[] args) throws InterruptedException {
//        test();
        testAtomic();
    }

    private static void test() throws InterruptedException {
        Set<Integer> set = new HashSet<>();
        Thread t1 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = value;
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                set.add(value);
                value += 1;
                x++;
                /**
                 * value = value + 1
                 * 1. get value from main memory to local memory
                 * 2. add 1 -> x
                 * 3. assign the value to x
                 * 4. flush to main memory
                 */
            }
        });
        Thread t2 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = value;
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                set.add(value);
                value += 1;
                x++;
            }
        });
        Thread t3 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = value;
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                set.add(value);
                value += 1;
                x++;
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(set.size());
    }

    private static void testAtomic() throws InterruptedException {
        final AtomicInteger atomicValue = new AtomicInteger();
        Set<Integer> set = new HashSet<>();
        Thread t1 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = atomicValue.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                set.add(tmp);
                x++;
                /**
                 * value = value + 1
                 * 1. get value from main memory to local memory
                 * 2. add 1 -> x
                 * 3. assign the value to x
                 * 4. flush to main memory
                 */
            }
        });
        Thread t2 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = atomicValue.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                set.add(tmp);
                x++;
            }
        });
        Thread t3 = new Thread(() -> {
            int x = 0;
            while (x < 500) {
                int tmp = atomicValue.getAndIncrement();
                System.out.println(Thread.currentThread().getName() + ":" + tmp);
                set.add(tmp);
                x++;
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(set.size());
    }
}

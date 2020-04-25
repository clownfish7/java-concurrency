package com.clownfish7.concurrency.part1;

import org.junit.jupiter.api.Test;

/**
 * @author You
 * @create 2020-04-25 20:49
 */
public class DeadLockTest {

    /**
     * jps 查看进程
     * jconsole 查看jvm
     * jstack pid 检查死锁
     */

    public static final Object LOCKA = new Object();
    public static final Object LOCKB = new Object();

    @Test
    public void test() throws InterruptedException {
        Thread threadA = new Thread(() -> {
            while (true) {
                synchronized (DeadLockTest.LOCKA){
                    System.out.println("Thread A get lockA ...");
                    synchronized (DeadLockTest.LOCKB) {
                        System.out.println("Thread A get lockB ...");
                    }
                }
            }
        });
        threadA.start();
        Thread threadB = new Thread(() -> {
            while (true) {
                synchronized (DeadLockTest.LOCKB){
                    System.out.println("Thread B get lockB ...");
                    synchronized (DeadLockTest.LOCKA) {
                        System.out.println("Thread B get lockA ...");
                    }
                }
            }
        });
        threadB.start();
        threadA.join();
        threadB.join();
    }
}

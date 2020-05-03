package com.clownfish7.concurrency.part3.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author You
 * @create 2020-05-03 17:57
 */
public class ReentrantLockTest {

    /**
     * reentrantLock.lock();                不可中断
     * reentrantLock.lockInterruptibly();   可中断
     */

    @Test
    public void testLock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                reentrantLock.lock();
                System.out.println(reentrantLock.getHoldCount());
                System.out.println(Thread.currentThread().getName() + ":" + reentrantLock.isHeldByCurrentThread());
                System.out.println("T1 GET LOCK!");
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
                System.out.println("T1 RELEASE LOCK!");
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                reentrantLock.lock();
                System.out.println(reentrantLock.getHoldCount());
                System.out.println(Thread.currentThread().getName() + ":" + reentrantLock.isHeldByCurrentThread());
                System.out.println("T2 GET LOCK!");
//                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
                System.out.println("T2 RELEASE LOCK!");
            }
        });
        t2.start();

        Thread.sleep(1000);
        System.out.println(reentrantLock.getQueueLength());
        System.out.println(reentrantLock.hasQueuedThreads());
        System.out.println(reentrantLock.hasQueuedThread(t2));
        System.out.println(reentrantLock.isLocked());
        System.out.println(Thread.currentThread().getName() + ":" + reentrantLock.isHeldByCurrentThread());
        t1.join();
        t2.join();
    }

    @Test
    public void testTryLock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                reentrantLock.lock();
                System.out.println("T1 GET LOCK!");
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
                System.out.println("T1 RELEASE LOCK!");
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            if (reentrantLock.tryLock()) {
                try {
                    System.out.println("T2 GET LOCK!");
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                    System.out.println("T2 RELEASE LOCK!");
                }
            } else {
                System.out.println("T2 CANNOT GET LOCK!");
            }
        });
        t2.start();
        t1.join();
        t2.join();
    }
}

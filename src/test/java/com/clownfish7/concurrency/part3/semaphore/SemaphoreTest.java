package com.clownfish7.concurrency.part3.semaphore;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

/**
 * @author You
 * @create 2020-05-03 17:04
 */
public class SemaphoreTest {

    /**
     * new Semaphore(1)
     * new Semaphore(1，true)
     *
     * semaphore.acquire();
     * semaphore.acquire(int permits);  获取许可证
     * semaphore.release();
     * semaphore.release(int permits);  归还许可证
     *
     * semaphore.drainPermits()         拿走全部
     *
     * semaphore.tryAcquire()
     * semaphore.tryAcquire(int permits)
     * semaphore.tryAcquire(int permits, timeout, timeunit)
     *
     * semaphore.acquireUninterruptibly();
     *
     * semaphore.isFair()               公平
     * semaphore.availablePermits()     剩余许可证
     * semaphore.getQueueLength()       阻塞拿许可证数
     * semaphore.hasQueuedThreads()     存在阻塞拿许可证
     */

    @Test
    public void test() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        Thread t1 = new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("t1 in!");
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
                System.out.println("t1 out!");
            }
        });
        t1.start();


        Thread t2 = new Thread(()->{
            try {
                semaphore.acquireUninterruptibly();
                System.out.println("t2 in!");
            }finally {
                semaphore.release();
                System.out.println("t2 out!");
            }
        });
        t2.start();

        Thread.sleep(1000);
        System.out.println(semaphore.isFair());
        System.out.println(semaphore.availablePermits());
        System.out.println(semaphore.getQueueLength());
        System.out.println(semaphore.hasQueuedThreads());

        t2.interrupt();

        t1.join();
        t2.join();
    }
}

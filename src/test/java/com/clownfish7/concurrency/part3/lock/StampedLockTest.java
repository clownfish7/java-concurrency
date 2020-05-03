package com.clownfish7.concurrency.part3.lock;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

/**
 * @author You
 * @create 2020-05-03 21:09
 */
public class StampedLockTest {

    /**
     * 适用于多个读、少个写的情况，避免写线程抢不到锁
     */
    StampedLock stampedLock = new StampedLock();
    LinkedList<String> list = new LinkedList<>();

    @Test
    public void testRead() throws InterruptedException {
        IntStream.range(0, 99).forEach(i -> {
            new Thread(read).start();
        });
        new Thread(write).start();

        Thread.currentThread().join();
    }

    @Test
    public void testReadOptimistic() throws InterruptedException {
        list.add("123");
        IntStream.range(0, 1).forEach(i -> {
            new Thread(readOptimistic).start();
        });
        new Thread(write).start();

        Thread.currentThread().join();
    }

    private void read() {
        long stamped = -1;
        try {
            stamped = stampedLock.readLock();
            Thread.sleep(500);
            if (list.size() > 0) {
                System.out.println(String.join("-", list) + Thread.currentThread().getName() + ": read");
            } else {
                System.out.println(Thread.currentThread().getName() + ": read");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlockRead(stamped);
        }
    }

    private void readOptimistic() {
        //尝试去拿一个乐观锁
        long stamped = stampedLock.tryOptimisticRead();
        if (list.size() > 0) {
            System.out.println(String.join("-", list) + Thread.currentThread().getName() + ": read - 1");
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //如果有线程修改，我们再去获取一个悲观读锁
        // true 没被改过  false 被改过
        if (!stampedLock.validate(stamped)) {
            try {
                stamped = stampedLock.readLock();
                if (list.size() > 0) {
                    System.out.println(String.join("-", list) + Thread.currentThread().getName() + ": read - 2");
                } else {
                    System.out.println(Thread.currentThread().getName() + ": read");
                }
            } finally {
                stampedLock.unlockRead(stamped);
            }
        }
    }

    private void write() {
        long stamped = -1;
        try {
            stamped = stampedLock.writeLock();
            Thread.sleep(500);
            System.out.println(System.currentTimeMillis() + Thread.currentThread().getName() + ":write");
            list.set(0, System.currentTimeMillis() + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlockWrite(stamped);
        }
    }

    Runnable read = () -> {
        while (true) {
            read();
        }
    };
    Runnable readOptimistic = () -> {
        while (true) {
            readOptimistic();
        }
    };
    Runnable write = () -> {
        while (true) {
            write();
        }
    };

}

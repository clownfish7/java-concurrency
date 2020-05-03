package com.clownfish7.concurrency.part3.lock;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author You
 * @create 2020-05-03 19:31
 */
public class ReentrantReadWriteLockTest {

    private List<String> data = new ArrayList<>();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    @Test
    public void test() throws InterruptedException {
        new Thread(this::write).start();
        new Thread(this::write).start();
        new Thread(this::write).start();
        new Thread(this::read).start();
        new Thread(this::read).start();
        new Thread(this::read).start();
        Thread.currentThread().join();
    }

    private void read() {
        try {
            reentrantReadWriteLock.readLock().lock();
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + ": read");
            data.forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    private void write() {
        try {
            reentrantReadWriteLock.writeLock().lock();
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + ": write");
            data.add(System.currentTimeMillis() + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }
}
